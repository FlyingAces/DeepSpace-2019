package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ButtonCommandsPickUp extends CommandGroup {
	public ButtonCommandsPickUp(RobotMap.Controller controller) {
		switch(controller) {
		case TRIGGER_LB:
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.LOCKED));
			break;
		case TRIGGER_RB:
			addSequential(new MoveArmWristToCommand(RobotMap.PICK_UP_START_X,
												    RobotMap.PICK_UP_START_Y));
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.PLACE));
			break;
		case X_BUTTON:

			addSequential(new MoveArmWristToCommand(0.0,
												    RobotMap.PICK_UP_GROUND_LEVEL_Y,
												    RobotArmCalculations.HandState.PICK_UP));
			addSequential(new WaitCommand(1));

			addSequential(new MoveArmWristToCommand(0.0,
												    RobotMap.PICK_UP_START_Y,
												    RobotArmCalculations.HandState.PICK_UP));
			break;
		}
	}
}
