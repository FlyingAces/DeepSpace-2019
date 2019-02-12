package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class ButtonConditionalCommands extends ConditionalCommand {
	public ButtonConditionalCommands(RobotMap.Controller controller) {
		super(new ButtonCommandsLocked(controller), 
				new ConditionalCommand(new ButtonCommandsPickUp(controller), new ButtonCommandsPlace(controller)) {
						@Override
						protected boolean condition() {
							return ArmSubsystem.getInstance().getHandState() == RobotArmCalculations.HandState.PICK_UP;
						}
					});
	}

	@Override
	protected boolean condition() {
		return ArmSubsystem.getInstance().getHandState() == RobotArmCalculations.HandState.LOCKED;
	}
}
