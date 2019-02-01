package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ControllerSubsystem;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.Command;

public class CommandRobotArmWithController extends Command {
	private ControllerSubsystem _controller; 
	private RobotArmCalculations _calculations;
	
	public CommandRobotArmWithController() {
		super("CommandRobotArmWithController");
		_controller = ControllerSubsystem.getInstance();
		

		_calculations = new RobotArmCalculations(0.0,90.0,RobotArmCalculations.HandState.PLACE);
		
	}
	
	@Override
	protected void initialize() {
		_calculations.sendInfo();
	
	}
	
	@Override
	protected void execute() {
		if(_controller.getController().getRawButtonPressed(RobotMap.Controller.Y_BUTTON.getChannel())) {
			_calculations.setWristTargetY(_calculations.getWristTargetY() + .5);
		} else if(_controller.getController().getRawButtonPressed(RobotMap.Controller.A_BUTTON.getChannel())) {
			_calculations.setWristTargetY(_calculations.getWristTargetY() - .5);
		}
		
		if(_controller.getController().getRawButtonPressed(RobotMap.Controller.B_BUTTON.getChannel())) {
			_calculations.setWristTargetX(_calculations.getWristTargetX() + .5);
		} else if(_controller.getController().getRawButtonPressed(RobotMap.Controller.X_BUTTON.getChannel())) {
			_calculations.setWristTargetX(_calculations.getWristTargetX() - .5);
		}
		_calculations.sendInfo();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override 
	protected void end() {
		
	}

}
