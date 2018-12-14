package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.robot.subsystems.LauncherSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class LauncherShoot extends Command {
	private LauncherSubsystem _launcher;

	public LauncherShoot() {
		super("ShootCommand");
		_launcher = LauncherSubsystem.getInstance();
		requires(_launcher);

		setTimeout(0.25);

	}

	@Override
	protected void initialize() {
		execute();
	}

	@Override
	protected void execute() {
		_launcher.setShooterSpeed(1);
	}

	@Override
	protected boolean isFinished() {
		if (isTimedOut()) end();

		return isTimedOut();
	}

	@Override
	protected void end() {
		_launcher.setShooterSpeed(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
