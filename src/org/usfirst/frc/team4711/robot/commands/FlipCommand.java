package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.robot.subsystems.ControllerSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class FlipCommand extends Command {
	private ControllerSubsystem _controller;
	
	public FlipCommand() {
		super("FlipCommand");
		_controller = ControllerSubsystem.getInstance();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}