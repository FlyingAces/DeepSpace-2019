package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ButtonCommandsLocked extends CommandGroup {
	public ButtonCommandsLocked(RobotMap.Controller controller) {
		switch(controller) {
		case TRIGGER_LB:
			addSequential(new MoveArmWristToCommand(RobotMap.PLACE_START_X,
													RobotMap.PLACE_START_Y));
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.PLACE));
			break;
		case TRIGGER_RB:
			addSequential(new MoveArmWristToCommand(RobotMap.PICK_UP_START_X,
				    								RobotMap.PICK_UP_START_Y,
				    								RobotArmCalculations.HandState.LOCKED));
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.PICK_UP));
			break;
		
		}
	}
}
