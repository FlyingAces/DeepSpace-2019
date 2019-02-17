package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team4711.util.RobotArmCalculations;
import org.usfirst.frc.team4711.util.RobotArmCalculations.HandState;

public class MoveArmWristToCommand extends MoveArmAnglesCommand {
	public static double USE_CURRENT_LOCATION = Double.NaN;
	
	protected double _wristTargetX;
	protected double _wristTargetY;
	
	private HandState _handState;
	
	public MoveArmWristToCommand(double wristTargetX, double wristTargetY) {
		this(wristTargetX, wristTargetY, null);
	}
	
	public MoveArmWristToCommand(HandState handState) {
		this(0.0, 0.0, handState);
	}
	
	public MoveArmWristToCommand(double wristTargetX, double wristTargetY, HandState handState) {
		super(0.0, 0.0, 0.0);
		
		_wristTargetX = wristTargetX;
		_wristTargetY = wristTargetY;
		
		_handState = handState;
	}
	
	@Override
	protected void initialize() {
		RobotArmCalculations calculations = new RobotArmCalculations(_arm.getAngle(ArmSubsystem.Angle.SHOULDER),
												 _arm.getAngle(ArmSubsystem.Angle.ELBOW), 
												 _arm.getHandState());
		calculations.setWristAngle(_arm.getAngle(ArmSubsystem.Angle.WRIST));
		calculations.setInverted(_arm.isInverted());

		if(!Double.isNaN(_wristTargetX)) 
			calculations.setWristTargetX(_wristTargetX);
		if(!Double.isNaN(_wristTargetY))
			calculations.setWristTargetY(_wristTargetY);
		
		if(_handState != null)
			calculations.setHandState(_handState);
		
		_shoulderValue = calculations.getShoulderAngle();
		_elbowValue = calculations.getElbowAngle();
		_wristValue = calculations.getWristAngle();
		
		super.initialize();
	}

	@Override
	protected void end() {
		super.end();
		if(_handState != null) {
			_arm.setHandState(_handState);
			_feed.sendString("currentHandState",  ArmSubsystem.getInstance().getHandState().toString());
		}
	}

}
