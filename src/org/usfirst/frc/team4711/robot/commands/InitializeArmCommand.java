package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class InitializeArmCommand extends CommandGroup {
	public InitializeArmCommand() {
		addSequential(new MoveArmWristToCommand(0.0, 
				 RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches() + (RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches()*.75)));

		addSequential(new ZeroOutArmAnglesCommand());
		
		addSequential(new MoveArmAnglesCommand(MoveArmAnglesCommand.USE_CURRENT_ANGLE, -35.0, 170));
		
		addSequential(new MoveArmAlongAxisCommand(MoveArmAlongAxisCommand.Axis.X, RobotMap.ROBOT_BACK_X - 10.0));
		addSequential(new MoveArmAlongAxisCommand(MoveArmAlongAxisCommand.Axis.Y, RobotMap.GROUND_LEVEL_Y + 5.0));
	}

}
