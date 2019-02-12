package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.commands.ButtonConditionalCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ControllerSubsystem extends Subsystem {
	
	private Joystick _joystick;
	private JoystickButton _buttonTriggerRB;
	private JoystickButton _buttonTriggerLB;
	private JoystickButton _buttonX;
	
	private static ControllerSubsystem _instance;
	
	private ControllerSubsystem(){
		_joystick = new Joystick(RobotMap.Controller.JOYSTICK_PORT.getChannel());
		
		_buttonTriggerRB = new JoystickButton(_joystick, RobotMap.Controller.TRIGGER_RB.getChannel());
		_buttonTriggerRB.whenPressed(new ButtonConditionalCommands(RobotMap.Controller.TRIGGER_RB));
		
		_buttonTriggerLB = new JoystickButton(_joystick, RobotMap.Controller.TRIGGER_LB.getChannel());
		_buttonTriggerLB.whenPressed(new ButtonConditionalCommands(RobotMap.Controller.TRIGGER_LB));
		
		_buttonX = new JoystickButton(_joystick, RobotMap.Controller.X_BUTTON.getChannel());
		_buttonX.whenPressed(new ButtonConditionalCommands(RobotMap.Controller.X_BUTTON));
		
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