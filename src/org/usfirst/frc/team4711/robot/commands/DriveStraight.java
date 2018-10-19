package org.usfirst.frc.team4711.robot.commands;
import org.usfirst.frc.team4711.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {
	public static enum Direction {
		FORWARD(1),
		BACKWARD(-1);
		
		private int _moveValue;
		
		private Direction(int moveValue) {
			_moveValue = moveValue;
		}
		public int getMoveValue() {
			return _moveValue;
		}
	}
	
	private Direction _direction;
	private DriveTrainSubsystem _drive;
	
	public DriveStraight(Direction direction) {
		super("DriveStraight");
		_drive = DriveTrainSubsystem.getInstance();
		requires(_drive);
		
		_direction = direction;
	}
	
	@Override
	protected void initialize() {
		_drive.tankDrive(_direction.getMoveValue(), _direction.getMoveValue());
    }
	
	@Override
	protected void execute() {
		final double leftVelocity = (double)Math.abs(_drive.getCurrentLeftVelocity());
		final double rightVelocity = (double)Math.abs(_drive.getCurrentRightVelocity());
		double speedChangeLeft = 1.0;
		double speedChangeRight = 1.0;
		
		if(leftVelocity > rightVelocity) {
			speedChangeLeft = rightVelocity / leftVelocity;
		} else if(rightVelocity > leftVelocity) {
			speedChangeRight = leftVelocity / rightVelocity;
		}
		
		_drive.tankDrive(_direction.getMoveValue() * speedChangeLeft,
						 _direction.getMoveValue() * speedChangeRight);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
