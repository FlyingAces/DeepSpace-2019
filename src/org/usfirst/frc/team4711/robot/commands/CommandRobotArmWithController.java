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
		_calculations = new RobotArmCalculations(_arm.getAngle(ArmSubsystem.Angle.SHOULDER), 
												 _arm.getAngle(ArmSubsystem.Angle.ELBOW), 
												 _arm.getHandState());
		_calculations.setWristAngle(_arm.getAngle(ArmSubsystem.Angle.WRIST));
		
		_shoulderDir = 0.0;
		_elbowDir = 0.0;
		_wristDir = 0.0;
		
		_feed.sendAngleInfo("currentAngles", _arm.getAngle(ArmSubsystem.Angle.SHOULDER), 
											 _arm.getAngle(ArmSubsystem.Angle.ELBOW), 
											 _arm.getAngle(ArmSubsystem.Angle.WRIST));
		_feed.sendAngleInfo("endAngles", _calculations.getShoulderAngle(), 
										 _calculations.getElbowAngle(), 
										 _calculations.getWristAngle());
	}
	
	@Override
	protected void execute() {
		
		double rightX = _controller.getController().getRawAxis(RobotMap.Controller.AXIS_RIGHT_X.getChannel());
		double rightY = _controller.getController().getRawAxis(RobotMap.Controller.AXIS_RIGHT_Y.getChannel());
		
		if(rightX > -.1 && rightX < .1)
			rightX = 0.0;
		
		if(rightY > -.1 && rightY < .1)
			rightY = 0.0;

		if(hasAllAnglesFinished() || 
		  (_shoulderDir == 0.0 &&_elbowDir == 0.0 && _wristDir == 0.0)) { 
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
				if (rightY < 0) {
					double tempX = _calculations.getWristTargetX();
					double tempY = _calculations.getWristTargetY();
					
					_calculations.setWristTargetY(tempY + 1);
					if(tempX != _calculations.getWristTargetX()) {
						_calculations.setWristTargetY(tempY);
						_calculations.setWristTargetX(tempX);
					}
				}else if (rightY > 0 && 
						_calculations.getWristTargetY() > RobotMap.PICK_UP_GROUND_LEVEL_Y)
					_calculations.setWristTargetY(_calculations.getWristTargetY() - 1);
				break;
			case PLACE:
				if (rightY < 0) {
					double tempX = _calculations.getWristTargetX();
					double tempY = _calculations.getWristTargetY();
					
					_calculations.setWristTargetY(tempY + 1);
					if(tempX != _calculations.getWristTargetX()) {
						_calculations.setWristTargetY(tempY);
						_calculations.setWristTargetX(tempX);
					}
					_calculations.setWristTargetY(_calculations.getWristTargetY() + 1);
				} else if (rightY > 0 && 
						_calculations.getWristTargetY() > RobotMap.PLACE_START_Y)
					_calculations.setWristTargetY(_calculations.getWristTargetY() - 1);
				break;
			}
			
			if(rightX != 0 || rightY != 0) {
				_shoulderDir = (_calculations.getShoulderAngle() == _arm.getAngle(ArmSubsystem.Angle.SHOULDER)) ? 0.0
						: (_calculations.getShoulderAngle() > _arm.getAngle(ArmSubsystem.Angle.SHOULDER)) ? 1.0 : -1.0;
				_elbowDir = (_calculations.getElbowAngle() == _arm.getAngle(ArmSubsystem.Angle.ELBOW)) ? 0.0
						: (_calculations.getElbowAngle() > _arm.getAngle(ArmSubsystem.Angle.ELBOW)) ? 1.0 : -1.0;
				_wristDir = (_calculations.getWristAngle() == _arm.getAngle(ArmSubsystem.Angle.WRIST)) ? 0.0
						: (_calculations.getWristAngle() > _arm.getAngle(ArmSubsystem.Angle.WRIST)) ? 1.0 : -1.0;
	
				_feed.sendAngleInfo("endAngles", _calculations.getShoulderAngle(), 
												 _calculations.getElbowAngle(), 
												 _calculations.getWristAngle());
			}
		} else {
			double diffShoulderAngle = Math.abs(_calculations.getShoulderAngle() - _arm.getAngle(ArmSubsystem.Angle.SHOULDER));
			double diffElbowAngle = Math.abs(_calculations.getElbowAngle() - _arm.getAngle(ArmSubsystem.Angle.ELBOW));
			double diffWristAngle = Math.abs(_calculations.getWristAngle() - _arm.getAngle(ArmSubsystem.Angle.WRIST));
			
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
			
			_arm.setMotorSpeeds(_shoulderDir * shoulderSpeed, 
								_elbowDir * elbowSpeed, 
								_wristDir * wristSpeed);
			
			_feed.sendAngleInfo("currentAngles", _arm.getAngle(ArmSubsystem.Angle.SHOULDER), 
												 _arm.getAngle(ArmSubsystem.Angle.ELBOW), 
												 _arm.getAngle(ArmSubsystem.Angle.WRIST));
		}
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean hasAllAnglesFinished() {
		return ((_shoulderDir < 0)? _arm.getAngle(ArmSubsystem.Angle.SHOULDER) <= _calculations.getShoulderAngle() : _arm.getAngle(ArmSubsystem.Angle.SHOULDER) >= _calculations.getShoulderAngle()) &&
			   ((_elbowDir < 0)? _arm.getAngle(ArmSubsystem.Angle.ELBOW) <= _calculations.getElbowAngle() : _arm.getAngle(ArmSubsystem.Angle.ELBOW) >= _calculations.getElbowAngle()) &&
			   ((_wristDir < 0)? _arm.getAngle(ArmSubsystem.Angle.WRIST) <= _calculations.getWristAngle() : _arm.getAngle(ArmSubsystem.Angle.WRIST)
			   >= _calculations.getWristAngle());
	}

}