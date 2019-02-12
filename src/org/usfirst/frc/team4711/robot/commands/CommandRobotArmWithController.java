package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.MotorSpeeds;
import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.robot.subsystems.ControllerSubsystem;
import org.usfirst.frc.team4711.util.Feed;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.Command;

public class CommandRobotArmWithController extends Command {
	private ArmSubsystem _arm;
	private ControllerSubsystem _controller; 
	private RobotArmCalculations _calculations;
	private Feed _feed;
	
	private double _shoulderDir;
	private double _elbowDir;
	private double _wristDir;
	
	public CommandRobotArmWithController() {
		super("CommandRobotArmWithController");
		_controller = ControllerSubsystem.getInstance();
		_arm = ArmSubsystem.getInstance();
		requires(_arm);
		_feed = Feed.getInstance();
	}
	
	@Override
	protected void initialize() {
		_calculations = new RobotArmCalculations(_arm.getShoulderAngle(), 
												 _arm.getElbowAngle(), 
												 _arm.getHandState());
		_calculations.setWristAngle(_arm.getWristAngle());
		
		_shoulderDir = 0.0;
		_elbowDir = 0.0;
		_wristDir = 0.0;
		
		_feed.sendAngleInfo("currentAngles", _arm.getShoulderAngle(), _arm.getElbowAngle(), _arm.getWristAngle());
	}
	
	@Override
	protected void execute() {
		
		double rightX = _controller.getController().getRawAxis(RobotMap.Controller.AXIS_RIGHT_X.getChannel());
		double rightY = _controller.getController().getRawAxis(RobotMap.Controller.AXIS_RIGHT_Y.getChannel());
		
		if(rightX > -.1 && rightX < .1)
			rightX = 0.0;
		
		if(rightY > -.1 && rightY < .1)
			rightY = 0.0;
		
		
		if(hasAllAnglesFinished()) { 
			_arm.setMotorSpeeds(0.0, 0.0, 0.0);

			switch (ArmSubsystem.getInstance().getHandState()) {
			case LOCKED:
				if (rightX > 0)
					_calculations.setWristTargetX(_calculations.getWristTargetX() + 1);
				else if (rightX < 0)
					_calculations.setWristTargetX(_calculations.getWristTargetX() - 1);

				if (rightY < 0)
					_calculations.setWristTargetY(_calculations.getWristTargetY() + 1);
				else if (rightY > 0)
					_calculations.setWristTargetY(_calculations.getWristTargetY() - 1);

				break;
			case PICK_UP:
				if (rightX > 0 && _calculations.getWristTargetX() < RobotMap.PICK_UP_MAX_EXTENSION_X)
					_calculations.setWristTargetX(_calculations.getWristTargetX() + 1);
				else if (rightX < 0 && _calculations.getWristTargetX() > RobotMap.PICK_UP_START_X)
					_calculations.setWristTargetX(_calculations.getWristTargetX() - 1);

				if (rightY < 0 && _calculations.getWristTargetX() >= RobotMap.PICK_UP_START_X)
					_calculations.setWristTargetY(_calculations.getWristTargetY() + 1);
				else if (rightY > 0 && 
						_calculations.getWristTargetY() > RobotMap.PICK_UP_GROUND_LEVEL_Y && 
						_calculations.getWristTargetX() >= RobotMap.PICK_UP_START_X)
					_calculations.setWristTargetY(_calculations.getWristTargetY() - 1);
				break;
			case PLACE:
				break;
			}
			
			if(rightX != 0 || rightY != 0) {
				_shoulderDir = (_calculations.getShoulderAngle() == _arm.getShoulderAngle()) ? 0.0
						: (_calculations.getShoulderAngle() > _arm.getShoulderAngle()) ? 1.0 : -1.0;
				_elbowDir = (_calculations.getElbowAngle() == _arm.getElbowAngle()) ? 0.0
						: (_calculations.getElbowAngle() > _arm.getElbowAngle()) ? 1.0 : -1.0;
				_wristDir = (_calculations.getWristAngle() == _arm.getWristAngle()) ? 0.0
						: (_calculations.getWristAngle() > _arm.getWristAngle()) ? 1.0 : -1.0;
	
				_feed.sendAngleInfo("endAngles", _calculations.getShoulderAngle(), _calculations.getElbowAngle(), _calculations.getWristAngle());
			}
		} else {
			double diffShoulderAngle = Math.abs(_calculations.getShoulderAngle() - _arm.getShoulderAngle());
			double diffElbowAngle = Math.abs(_calculations.getElbowAngle() - _arm.getElbowAngle());
			double diffWristAngle = Math.abs(_calculations.getWristAngle() - _arm.getWristAngle());
			
			double speed = Math.max(Math.max(Math.abs(0 - rightX), Math.abs(0 - rightY)), .1);

			double shoulderSpeed = speed;
			double elbowSpeed = speed;
			double wristSpeed = speed;
			
			if(diffShoulderAngle < diffElbowAngle && diffWristAngle < diffElbowAngle) {
				shoulderSpeed *= diffShoulderAngle / diffElbowAngle;
				wristSpeed *= diffWristAngle / diffElbowAngle;
			} else if(diffElbowAngle < diffShoulderAngle && diffWristAngle < diffShoulderAngle) {
				elbowSpeed *= diffElbowAngle / diffShoulderAngle;
				wristSpeed *= diffWristAngle / diffShoulderAngle;
			} else {
				shoulderSpeed *= diffShoulderAngle / diffWristAngle;
				elbowSpeed *= diffElbowAngle / diffWristAngle;
			}
			
			_arm.setMotorSpeeds(_shoulderDir * shoulderSpeed * MotorSpeeds.SHOULDER_MOTOR_SPEED, 
								_elbowDir * elbowSpeed * MotorSpeeds.ELBOW_MOTOR_SPEED, 
								_wristDir * wristSpeed * MotorSpeeds.WRIST_MOTOR_SPEED);
			
			_feed.sendAngleInfo("currentAngles", _arm.getShoulderAngle(), _arm.getElbowAngle(), _arm.getWristAngle());
		}
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean hasAllAnglesFinished() {
		return ((_shoulderDir < 0)? _arm.getShoulderAngle() <= _calculations.getShoulderAngle() : _arm.getShoulderAngle() >= _calculations.getShoulderAngle()) &&
			   ((_elbowDir < 0)? _arm.getElbowAngle() <= _calculations.getElbowAngle() : _arm.getElbowAngle() >= _calculations.getElbowAngle()) &&
			   ((_wristDir < 0)? _arm.getWristAngle() <= _calculations.getWristAngle() : _arm.getWristAngle() >= _calculations.getWristAngle());
	}

}