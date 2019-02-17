package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FlipCommand extends CommandGroup {
	public static enum Direction {
		FORWARD, BACK;
	}
	
	public FlipCommand(Direction direction) {
		RobotArmCalculations calculations = new RobotArmCalculations(0.0, 0.0, RobotArmCalculations.HandState.LOCKED);

		switch(direction) {
		case FORWARD:
			break;
		case BACK:
			break;
		}
			
	}
}