package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.robot.subsystems.ControllerSubsystem;
import org.usfirst.frc.team4711.util.Feed;
import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.Command;

public class CommandRobotArmWithController extends Command {
	private ArmSubsystem _arm;
	private ControllerSubsystem _controller; 
	private RobotArmCalculations _calculations;
	private Feed _feed;
	
	private boolean _yButton;
	private boolean _xButton;
	private boolean _aButton;
	private boolean _bButton;
	
	public CommandRobotArmWithController() {
		super("CommandRobotArmWithController");
		_controller = ControllerSubsystem.getInstance();
		_arm = ArmSubsystem.getInstance();
		requires(_arm);
		_feed = Feed.getInstance();
		

		_calculations = new RobotArmCalculations(_arm.getShoulderAngle(),
												 _arm.getElbowAngle(), 
												 _arm.getHandState());
	}
	
	@Override
	protected void initialize() {
		_yButton = false;
		_xButton = false;
		_aButton = false;
		_bButton = false;
		
		_calculations = new RobotArmCalculations(_arm.getShoulderAngle(),
				 _arm.getElbowAngle(), 
				 _arm.getHandState());
		
		//_feed.sendAngleInfo("endAngles", _calculations.getShoulderAngle(), _calculations.getElbowAngle(), _calculations.getWristAngle());
		//_feed.sendAngleInfo("currentAngles", _arm.getShoulderAngle(), _arm.getElbowAngle(), _arm.getWristAngle());
	}
	
	@Override
	protected void execute() {
		if (_controller.getController().getRawButtonPressed(RobotMap.Controller.Y_BUTTON.getChannel()))
			_yButton = true;
		else if (_controller.getController().getRawButtonReleased(RobotMap.Controller.Y_BUTTON.getChannel())) 
			_yButton = false;
		if (_controller.getController().getRawButtonPressed(RobotMap.Controller.X_BUTTON.getChannel()))
			_xButton = true;
		else if (_controller.getController().getRawButtonReleased(RobotMap.Controller.X_BUTTON.getChannel())) 
			_xButton = false;
		if (_controller.getController().getRawButtonPressed(RobotMap.Controller.A_BUTTON.getChannel()))
			_aButton = true;
		else if (_controller.getController().getRawButtonReleased(RobotMap.Controller.A_BUTTON.getChannel())) 
			_aButton = false;
		if (_controller.getController().getRawButtonPressed(RobotMap.Controller.B_BUTTON.getChannel()))
			_bButton = true;
		else if (_controller.getController().getRawButtonReleased(RobotMap.Controller.B_BUTTON.getChannel())) 
			_bButton = false;
		
		
		
		
		if(_yButton) {
			_calculations.setWristTargetY(_calculations.getWristTargetY() + .5);
		} else if(_aButton) {
			_calculations.setWristTargetY(_calculations.getWristTargetY() - .5);
		}else if(_bButton) {
			_calculations.setWristTargetX(_calculations.getWristTargetX() + .5);
		} else if(_xButton) {
			_calculations.setWristTargetX(_calculations.getWristTargetX() - .5);
		}
		//_feed.sendAngleInfo("currentAngles", _arm.getShoulderAngle(), _arm.getElbowAngle(), _arm.getWristAngle());
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