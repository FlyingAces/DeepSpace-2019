package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.commands.CommandRobotArmWithController;
import org.usfirst.frc.team4711.util.RobotArmCalculations;
import org.usfirst.frc.team4711.util.RobotArmCalculations.HandState;
import org.usfirst.frc.team4711.util.Conversions;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmSubsystem extends Subsystem {
	private static ArmSubsystem _instance;
	
	private RobotArmCalculations _calculations;
	
	private BaseMotorController _elbowMotorAndEncoder;
	private BaseMotorController _shoulderMotorAndEncoder;
	private BaseMotorController _wristMotorAndEncoder;
	
	private double _wristAngle;
	
	private ArmSubsystem() {
		super("ArmSubsystem");
		
		//_elbowMotorAndEncoder = new WPI_TalonSRX(RobotMap.Talon.ELBOW_MOTOR.getChannel());
		//_shoulderMotorAndEncoder = new WPI_TalonSRX(RobotMap.Talon.SHOULDER_MOTOR.getChannel());
		//_wristMotorAndEncoder = new WPI_TalonSRX(RobotMap.Talon.WRIST_MOTOR.getChannel());
		
		//_elbowMotorAndEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		//_shoulderMotorAndEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		//_wristMotorAndEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		_calculations = new RobotArmCalculations(RobotMap.Angle.SHOULDER_START_ANGLE.getAngle(),
												 RobotMap.Angle.ELBOW_START_ANGLE.getAngle(),
												 RobotArmCalculations.HandState.LOCKED);
		_calculations.setWristAngle(RobotMap.Angle.WRIST_START_ANGLE.getAngle());
		
		_wristAngle = _calculations.getWristAngle();
	}
	
	public static ArmSubsystem getInstance(){
		if(_instance == null)
			_instance = new ArmSubsystem();
		
		return _instance;
	}
	
	public double getShoulderAngle() {
		return _calculations.getShoulderAngle();
	}
	
	public double getElbowAngle() {
		return _calculations.getElbowAngle();
	}
	
	public double getWristAngle() {
		return _wristAngle;
	}
	
	public boolean isInverted() {
		return _calculations.isInverted();
	}
	
	public HandState getHandState() {
		return _calculations.getHandState();
	}
	
	public double getWristTargetX() {
		return _calculations.getWristTargetX();
	}
	
	public double getWristTargetY() {
		return _calculations.getWristTargetY();
	}
	
	public void setHandState(HandState position) {
		_calculations.setHandState(position);
	}
	
	public void setMotorSpeeds(double shoulderMotor, 
							   double elbowMotor, 
							   double wristMotor) {
		
		_calculations.setShoulderAngle(shoulderMotor * .5 + getShoulderAngle());
		_calculations.setElbowAngle(elbowMotor * .5 + getElbowAngle());
		
		_wristAngle += wristMotor * .5;
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CommandRobotArmWithController());
	}

}
