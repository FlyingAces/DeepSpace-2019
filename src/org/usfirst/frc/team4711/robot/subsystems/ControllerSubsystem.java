package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ControllerSubsystem extends Subsystem {
	
	private Joystick _joystick;
	

	private static ControllerSubsystem _instance;
	
	private ControllerSubsystem(){
		_joystick = new Joystick(RobotMap.Controller.JOYSTICK_PORT.getChannel());
		
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	public static ControllerSubsystem getInstance(){
		if(_instance == null)
			_instance = new ControllerSubsystem();
		
		return _instance;
	}
	
	public Joystick getController(){
		return _joystick;
	}

}