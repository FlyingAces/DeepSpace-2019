package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.config.MotorSpeeds;
import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.robot.subsystems.ControllerSubsystem;
import org.usfirst.frc.team4711.util.Feed;
import org.usfirst.frc.team4711.util.RobotArmCalculations;
import org.usfirst.frc.team4711.util.RobotArmCalculations.HandState;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeArmStateCommand extends Command {
	private ArmSubsystem _arm;
	private ControllerSubsystem _controller; 
	private RobotArmCalculations _calculations;
	private Feed _feed;
	
	private HandState _handState;
	private double _direction;
	
	public ChangeArmStateCommand(HandState newHandState) {
		super("ChangeArmStateCommand");
		
		//_controller = ControllerSubsystem.getInstance();
		
		_arm = ArmSubsystem.getInstance();
		requires(_arm);
		
		_feed = Feed.getInstance();
		_handState = newHandState;
		
	}
	
	@Override
	protected void initialize() {
		_calculations = new RobotArmCalculations(_arm.getShoulderAngle(),
				 								 _arm.getElbowAngle(), 
				 								 _handState);
		
		_direction = (_calculations.getWristAngle() >= _arm.getWristAngle()) ? 1.0 : -1.0;
		
		_feed.sendAngleInfo("endAngles", _calculations.getShoulderAngle(), _calculations.getElbowAngle(), _calculations.getWristAngle());
		_feed.sendAngleInfo("currentAngles", _arm.getShoulderAngle(), _arm.getElbowAngle(), _arm.getWristAngle());
	}
	
	@Override
	protected void execute() {
		_arm.setMotorSpeeds(0.0, 0.0, _direction * MotorSpeeds.WRIST_MOTOR_SPEED);
		_feed.sendAngleInfo("currentAngles", _arm.getShoulderAngle(), _arm.getElbowAngle(), _arm.getWristAngle());
	}

	@Override
	protected boolean isFinished() {
		
		return Math.abs(_calculations.getWristAngle() - _arm.getWristAngle()) < 1;
	}

}
