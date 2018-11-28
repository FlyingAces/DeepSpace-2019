package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Patrol extends CommandGroup {
	public Patrol() {
		addSequential(new DriveTo(180.0 - RobotMap.Measurement.ROBOT_LENGTH.getInches()));
		
		addSequential(new WaitCommand(.5));
		addSequential(new RotateTo(180.0));

		addSequential(new WaitCommand(.5));
		addSequential(new DriveTo(180.0 - RobotMap.Measurement.ROBOT_LENGTH.getInches()));
		
		addSequential(new WaitCommand(.5));
		addSequential(new RotateTo(-180.0));
	}

}
