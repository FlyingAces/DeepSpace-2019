package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.MotorSpeeds;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.util.Feed;
import org.usfirst.frc.team4711.util.RobotArmCalculations;
import org.usfirst.frc.team4711.util.RobotArmCalculations.HandState;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArmWristToCommand extends Command {
	private ArmSubsystem _arm;
	private RobotArmCalculations _calculations;
	private Feed _feed;
	
	private double _shoulderDir;
	private double _elbowDir;
	private double _wristDir;
	
	private double _wristTargetX;
	private double _wristTargetY;
	
	private HandState _handState;
	
	public MoveArmWristToCommand(double wristTargetX, double wristTargetY) {
		this(wristTargetX, wristTargetY, null);
	}
	
	public MoveArmWristToCommand(HandState handState) {
		this(0.0, 0.0, handState);
	}
	
	public MoveArmWristToCommand(double wristTargetX, double wristTargetY, HandState handState) {
		super("MoveArmWristToCommand");
		
		_arm = ArmSubsystem.getInstance();
		requires(_arm);
		
		_feed = Feed.getInstance();
		
		_wristTargetX = wristTargetX;
		_wristTargetY = wristTargetY;
		
		_handState = handState;
	}
	
	@Override
	protected void initialize() {
		_calculations = new RobotArmCalculations(_arm.getShoulderAngle(),
												 _arm.getElbowAngle(), 
												 _arm.getHandState());
		_calculations.setWristAngle(_arm.getWristAngle());
		_calculations.setInverted(_arm.isInverted());

		if(_wristTargetX != 0.0) 
			_calculations.setWristTargetX(_wristTargetX);
		if(_wristTargetY != 0.0)
			_calculations.setWristTargetY(_wristTargetY);
		
		if(_handState != null)
			_calculations.setHandState(_handState);
		
		_shoulderDir = (_calculations.getShoulderAngle() == _arm.getShoulderAngle())? 0.0 : 
					   (_calculations.getShoulderAngle() > _arm.getShoulderAngle())? 1.0 : -1.0;
		_elbowDir = (_calculations.getElbowAngle() == _arm.getElbowAngle())? 0.0 :
					(_calculations.getElbowAngle() > _arm.getElbowAngle())? 1.0 : -1.0;
		_wristDir = (_calculations.getWristAngle() == _arm.getWristAngle()) ? 0.0 : 
					(_calculations.getWristAngle() > _arm.getWristAngle()) ? 1.0 : -1.0;
		
		_feed.sendAngleInfo("endAngles", _calculations.getShoulderAngle(), _calculations.getElbowAngle(), _calculations.getWristAngle());
	}
	
	@Override
	protected void execute() {
		double diffShoulderAngle = Math.abs(_calculations.getShoulderAngle() - _arm.getShoulderAngle());
		double diffElbowAngle = Math.abs(_calculations.getElbowAngle() - _arm.getElbowAngle());
		double diffWristAngle = Math.abs(_calculations.getWristAngle() - _arm.getWristAngle());
		
		double shoulderSpeed = 1.0;
		double elbowSpeed = 1.0;
		double wristSpeed = 1.0;
		
		if(diffElbowAngle < diffShoulderAngle && diffWristAngle < diffShoulderAngle) {
			elbowSpeed = diffElbowAngle / diffShoulderAngle;
			wristSpeed = diffWristAngle / diffShoulderAngle;
		} else if(diffShoulderAngle < diffElbowAngle && diffWristAngle < diffElbowAngle) {
			shoulderSpeed = diffShoulderAngle / diffElbowAngle;
			wristSpeed = diffWristAngle / diffElbowAngle;
		} else {
			shoulderSpeed = diffShoulderAngle / diffWristAngle;
			elbowSpeed = diffElbowAngle / diffWristAngle;
		}
		
		_arm.setMotorSpeeds(_shoulderDir * shoulderSpeed * MotorSpeeds.SHOULDER_MOTOR_SPEED, 
						    _elbowDir * elbowSpeed * MotorSpeeds.ELBOW_MOTOR_SPEED, 
				            _wristDir * wristSpeed * MotorSpeeds.WRIST_MOTOR_SPEED);
		    
		_feed.sendAngleInfo("currentAngles", _arm.getShoulderAngle(), _arm.getElbowAngle(), _arm.getWristAngle());
	}

	@Override
	protected boolean isFinished() {
		return 	((_shoulderDir < 0)? _arm.getShoulderAngle() <= _calculations.getShoulderAngle() : _arm.getShoulderAngle() >= _calculations.getShoulderAngle()) &&
				((_elbowDir < 0)? _arm.getElbowAngle() <= _calculations.getElbowAngle() : _arm.getElbowAngle() >= _calculations.getElbowAngle()) &&
				((_wristDir < 0)? _arm.getWristAngle() <= _calculations.getWristAngle() : _arm.getWristAngle() >= _calculations.getWristAngle());}
	
	@Override
	protected void end() {
		_arm.setMotorSpeeds(0.0, 0.0, 0.0);
		_arm.setHandState(_calculations.getHandState());
		
		_feed.sendAngleInfo("currentAngles", _arm.getShoulderAngle(), _arm.getElbowAngle(), _arm.getWristAngle());		
	}

}
