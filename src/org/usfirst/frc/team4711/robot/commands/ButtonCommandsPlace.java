package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ButtonCommandsPlace extends CommandGroup {
	public ButtonCommandsPlace(RobotMap.Controller controller) {
		switch(controller) {
		case TRIGGER_LB:
			break;
		case TRIGGER_RB:
			addSequential(new MoveArmWristToCommand(RobotArmCalculations.HandState.LOCKED));
			break;
		
		}
	}

}
