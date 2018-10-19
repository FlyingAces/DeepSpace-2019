//Package declaration
package org.usfirst.frc.team4711.robot;

import org.usfirst.frc.team4711.robot.commands.CommandByController;
import org.usfirst.frc.team4711.robot.commands.DriveStraight;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

//Class declaration
public class Robot extends IterativeRobot {
	
	//Command Objects
	private Command _autonomousCommand;
	private Command _teleopCommand;
	private Command _testCommand;
	
	public Robot() {
	}
	
	public void robotInit() {
		_teleopCommand = new CommandByController();
		_autonomousCommand = new DriveStraight(DriveStraight.Direction.FORWARD);

	}

	public void teleopInit() {
		if(_autonomousCommand != null) 
			_autonomousCommand.cancel();
		
		if(_teleopCommand != null)
			_teleopCommand.start();
	}
	
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public void autonomousInit() {
		if(_teleopCommand != null)
			_teleopCommand.cancel();
		
		if(_autonomousCommand != null) 
			_autonomousCommand.start();
		
	}
	
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public void testInit() {
		System.out.println("Start Test");
		_testCommand = new CommandByController();
		_testCommand.start();
		while(isTest() && isEnabled()) {
			LiveWindow.run();
			Timer.delay(0.1);
		}
	}
}