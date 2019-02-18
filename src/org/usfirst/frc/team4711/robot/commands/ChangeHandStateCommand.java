package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChangeHandStateCommand extends CommandGroup {
	public ChangeHandStateCommand(RobotArmCalculations.HandState handState) {
		switch(handState) {
		case PICK_UP:
			addSequential(new MoveArmAlongAxisCommand(MoveArmAlongAxisCommand.Axis.X, RobotMap.PICK_UP_START_X));
			addSequential(new MoveArmAlongAxisCommand(MoveArmAlongAxisCommand.Axis.Y, RobotMap.PICK_UP_START_Y));
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.PICK_UP));
			break;
		case PLACE:
			addSequential(new MoveArmAlongAxisCommand(MoveArmAlongAxisCommand.Axis.X, RobotMap.PLACE_START_X));
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.PLACE));
			break;
		case LOCKED:
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.LOCKED));
			break;
		}
	}

}
