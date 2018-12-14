package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ControllerSubsystem;
import org.usfirst.frc.team4711.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class CommandByController extends Command{
	private DriveTrainSubsystem _drive;
	private ControllerSubsystem _controller; 
	
	public CommandByController() {
		super("CommandByController");
		
		_drive = DriveTrainSubsystem.getInstance();
		requires(_drive);
		
		_controller = ControllerSubsystem.getInstance();
		requires(_controller);
	}

	@Override
	protected void initialize() {
		System.out.println("CommandByController initialized");
        _controller.setBButtonOnPressed(new LauncherShoot());
	}
	
	
	@Override
	protected void execute() {
		System.out.println("Gyro angle: " + _drive.gyroAngle());
    	double driveSpeed = RobotMap.SPEED_MULTIPLIER*(_controller.getController().getRawAxis(RobotMap.Controller.AXIS_TRIGGER_RT.getChannel()) -
    			_controller.getController().getRawAxis(RobotMap.Controller.AXIS_TRIGGER_LT.getChannel()));
		double driveAngle = RobotMap.SPEED_MULTIPLIER*(_controller.getController().getRawAxis(RobotMap.Controller.AXIS_LEFT_X.getChannel()));

    	_drive.arcadeDrive(driveSpeed, driveAngle);
    }
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
