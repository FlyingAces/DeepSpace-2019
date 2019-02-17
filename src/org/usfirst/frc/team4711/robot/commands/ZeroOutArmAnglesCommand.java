package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem.Angle;

public class ZeroOutArmAnglesCommand extends MoveArmAnglesCommand {
	public ZeroOutArmAnglesCommand() {
		super(0.0, 0.0, 0.0);
		
		setTimeout(15);
	}
	
	@Override
	protected boolean isFinished() {
		if(ArmSubsystem.getInstance().isAngleSwitchSet(ArmSubsystem.Angle.SHOULDER)) {
			ArmSubsystem.getInstance().zeroOutAnglePosition(ArmSubsystem.Angle.SHOULDER);
			_shoulderDir = 0.0;
		}
		
		if(ArmSubsystem.getInstance().isAngleSwitchSet(ArmSubsystem.Angle.ELBOW)) {
			ArmSubsystem.getInstance().zeroOutAnglePosition(ArmSubsystem.Angle.ELBOW);
			_elbowDir = 0.0;
		}
		
		if(ArmSubsystem.getInstance().isAngleSwitchSet(ArmSubsystem.Angle.WRIST)) {
			ArmSubsystem.getInstance().zeroOutAnglePosition(ArmSubsystem.Angle.WRIST);
			_wristDir = 0.0;
		}
		
		return ((_shoulderDir == 0.0) && (_elbowDir == 0.0) && (_wristDir == 0.0)) || isTimedOut();
	}

}
