package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ButtonCommandsPlace extends CommandGroup {
	public ButtonCommandsPlace(RobotMap.Controller controller) {
		switch(controller) {
		case TRIGGER_LB:
			addSequential(new MoveArmWristToCommand(RobotMap.PICK_UP_START_X,
					RobotMap.PICK_UP_START_Y,
					RobotArmCalculations.HandState.LOCKED));
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.PICK_UP));
			break;
		case TRIGGER_RB:
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.LOCKED));
			break;
		case X_BUTTON:
			addSequential(new MoveArmWristToCommand(RobotMap.PLACE_START_X + 5, 0.0));
			addSequential(new WaitCommand(0.5));
			addSequential(new MoveArmWristToCommand(RobotMap.PLACE_START_X, 0.0));
			break;
		case Y_BUTTON:
			addSequential(new MoveArmWristToCommand(0.0, RobotMap.DISK_HIGH_POSITION_Y));
			break;
		case B_BUTTON:
			addSequential(new MoveArmWristToCommand(0.0, RobotMap.DISK_MIDDLE_POSITION_Y));
			break;
		case A_BUTTON:
			addSequential(new MoveArmWristToCommand(0.0, RobotMap.DISK_LOW_POSITION_Y));
			break;
		
		}
	}

}
