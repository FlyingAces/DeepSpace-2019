package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team4711.util.Conversions;

public class RotateTo extends DriveCommand {
	
	private double _rotatePosition;
	private double _initialRightPosition;
	private double _initialLeftPosition;
	
	public RotateTo(double angleInDegrees) {
		super((angleInDegrees > 0)? 
				DriveCommand.Rotate.RIGHT : 
				DriveCommand.Rotate.LEFT);
		
		_rotatePosition = Conversions.angleToEncoderPosition(Math.abs(angleInDegrees));
		
	}
	
	@Override
	protected void initialize() {
		_initialRightPosition = DriveTrainSubsystem.getInstance().getCurrentLeftPosition();
		_initialLeftPosition = DriveTrainSubsystem.getInstance().getCurrentRightPosition();
		super.initialize();
	}
	
	@Override
	protected boolean isFinished() {
		double leftDistancePosition = Math.abs(DriveTrainSubsystem.getInstance().getCurrentLeftPosition() - _initialRightPosition);
		double rightDistancePosition = Math.abs(DriveTrainSubsystem.getInstance().getCurrentRightPosition() - _initialLeftPosition);
		return (leftDistancePosition >= _rotatePosition) || (rightDistancePosition >= _rotatePosition);
	}
	
}
