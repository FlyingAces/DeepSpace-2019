package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.util.Feed;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArmAnglesCommand extends Command {
	public static double USE_CURRENT_ANGLE = Double.NaN;
	
	protected ArmSubsystem _arm;
	protected Feed _feed;
	
	protected double _shoulderValue;
	protected double _elbowValue;
	protected double _wristValue;
	
	protected double _shoulderDir;
	protected double _elbowDir;
	protected double _wristDir;
	
	public MoveArmAnglesCommand(double shoulderValue, double elbowValue, double wristValue) {
		super("MoveArmAnglesCommand");
		
		_arm = ArmSubsystem.getInstance();
		requires(_arm);
		
		_feed = Feed.getInstance();
		
		_shoulderValue = shoulderValue;
		_elbowValue = elbowValue;
		_wristValue = wristValue;
	}
	
	@Override
	protected void initialize() {
		_shoulderValue = (Double.isNaN(_shoulderValue))? _arm.getAngle(ArmSubsystem.Angle.SHOULDER) : _shoulderValue;
		_elbowValue = (Double.isNaN(_elbowValue))? _arm.getAngle(ArmSubsystem.Angle.ELBOW) : _elbowValue;
		_wristValue = (Double.isNaN(_wristValue))? _arm.getAngle(ArmSubsystem.Angle.WRIST) : _wristValue;
		
		_shoulderDir = (_shoulderValue == _arm.getAngle(ArmSubsystem.Angle.SHOULDER))? 0.0 : 
					   (_shoulderValue > _arm.getAngle(ArmSubsystem.Angle.SHOULDER))? 1.0 : -1.0;
		_elbowDir = (_elbowValue == _arm.getAngle(ArmSubsystem.Angle.ELBOW))? 0.0 :
					(_elbowValue > _arm.getAngle(ArmSubsystem.Angle.ELBOW))? 1.0 : -1.0;
		_wristDir = (_wristValue == _arm.getAngle(ArmSubsystem.Angle.WRIST)) ? 0.0 : 
					(_wristValue > _arm.getAngle(ArmSubsystem.Angle.WRIST)) ? 1.0 : -1.0;
		
		_feed.sendAngleInfo("endAngles", _shoulderValue, _elbowValue, _wristValue);
	}
	
	@Override
	protected void execute() {
		double diffShoulderAngle = Math.abs(_shoulderValue - _arm.getAngle(ArmSubsystem.Angle.SHOULDER));
		double diffElbowAngle = Math.abs(_elbowValue - _arm.getAngle(ArmSubsystem.Angle.ELBOW));
		double diffWristAngle = Math.abs(_wristValue - _arm.getAngle(ArmSubsystem.Angle.WRIST));
		
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
		
		_arm.setMotorSpeeds(_shoulderDir * shoulderSpeed, 
						    _elbowDir * elbowSpeed, 
				            _wristDir * wristSpeed);
		    
		_feed.sendAngleInfo("currentAngles", _arm.getAngle(ArmSubsystem.Angle.SHOULDER), _arm.getAngle(ArmSubsystem.Angle.ELBOW), _arm.getAngle(ArmSubsystem.Angle.WRIST));
	}

	@Override
	protected boolean isFinished() {
		if((_shoulderDir < 0)? _arm.getAngle(ArmSubsystem.Angle.SHOULDER) <= _shoulderValue : _arm.getAngle(ArmSubsystem.Angle.SHOULDER) >= _shoulderValue)
			_shoulderDir = 0.0;
		
		if((_elbowDir < 0)? _arm.getAngle(ArmSubsystem.Angle.ELBOW) <= _elbowValue : _arm.getAngle(ArmSubsystem.Angle.ELBOW) >= _elbowValue)
			_elbowDir = 0.0;
		
		if((_wristDir < 0)? _arm.getAngle(ArmSubsystem.Angle.WRIST) <= _wristValue : _arm.getAngle(ArmSubsystem.Angle.WRIST) >= _wristValue)
			_wristDir = 0.0;
			
		return 	(_shoulderDir == 0.0) && (_elbowDir == 0.0) && (_wristDir == 0.0);
	}
	
	@Override
	protected void end() {
		_arm.setMotorSpeeds(0.0, 0.0, 0.0);
		
		_feed.sendAngleInfo("currentAngles", _arm.getAngle(ArmSubsystem.Angle.SHOULDER), 
											 _arm.getAngle(ArmSubsystem.Angle.ELBOW), 
											 _arm.getAngle(ArmSubsystem.Angle.WRIST));
	}

}
