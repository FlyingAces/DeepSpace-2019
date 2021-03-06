package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ButtonCommandsLocked extends CommandGroup {
	public ButtonCommandsLocked(RobotMap.Controller controller) {
		switch(controller) {
		case TRIGGER_LB:
			addSequential(
					new ConditionalCommand(
							new ChangeHandStateCommand(RobotArmCalculations.HandState.PLACE), 
							new WaitCommand(.01)) {

							@Override
							protected boolean condition() {
								return !ArmSubsystem.getInstance().isInverted();
							}
							
					});
			break;
		case TRIGGER_RB:
			addSequential(
					new ConditionalCommand(
							new ChangeHandStateCommand(RobotArmCalculations.HandState.PICK_UP), 
							new WaitCommand(.01)) {

							@Override
							protected boolean condition() {
								return !ArmSubsystem.getInstance().isInverted();
							}
							
					});
			break;
		case X_BUTTON:
			addSequential(
					new ConditionalCommand(
							new FlipCommand(FlipCommand.Direction.FORWARD), 
							new FlipCommand(FlipCommand.Direction.BACK)) {

							@Override
							protected boolean condition() {
								return ArmSubsystem.getInstance().isInverted();
							}
							
					});
			break;
		}
	}
}
