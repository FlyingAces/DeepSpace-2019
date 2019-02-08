package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.commands.ChangeArmStateCommand;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ControllerSubsystem extends Subsystem {
	
	private Joystick _joystick;
	private JoystickButton _armStateDropOffButton;
	private JoystickButton _armStatePickUpButton;

	private static ControllerSubsystem _instance;
	
	private ControllerSubsystem(){
		_joystick = new Joystick(RobotMap.Controller.JOYSTICK_PORT.getChannel());
		
		_armStateDropOffButton = new JoystickButton(_joystick, RobotMap.Controller.TRIGGER_RB.getChannel());
		_armStateDropOffButton.toggleWhenPressed(new ChangeArmStateCommand(RobotArmCalculations.HandState.PLACE));
		
		_armStatePickUpButton = new JoystickButton(_joystick, RobotMap.Controller.TRIGGER_LB.getChannel());
		_armStatePickUpButton.toggleWhenPressed(new ChangeArmStateCommand(RobotArmCalculations.HandState.PICK_UP));
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