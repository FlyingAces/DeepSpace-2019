package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithController extends Command {
	private DriveTrain _drive;
	public DriveWithController() {
		super("DriveWithController");
		
		_drive = DriveTrain.getInstance();
		requires(_drive);
	}
	
	@Override
	protected void initialize() {
		System.out.println("Cheese");
    }
	
	@Override
	protected void execute() {

    
    }	
	
	@Override
    protected void end() {
    }

	@Override
    protected void interrupted() {
        end();
    }

	@Override
	protected boolean isFinished() {
		return false;
	}
}
