package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.util.RobotArmCalculations;
import org.usfirst.frc.team4711.util.Conversions;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmSubsystem extends Subsystem {
	private static ArmSubsystem _instance;
	
	private RobotArmCalculations.HandState _handState;
	
	private BaseMotorController _elbowMotorAndEncoder;
	private BaseMotorController _shoulderMotorAndEncoder;
	private BaseMotorController _wristMotorAndEncoder;
	
	private double _elbowAngle;
	private double _shoulderAngle;
	private double _wristAngle;
	
	public ArmSubsystem() {
		super("ArmSubsystem");
		_handState = RobotArmCalculations.HandState.LOCKED;
		
		_elbowMotorAndEncoder = new WPI_TalonSRX(RobotMap.Talon.ELBOW_MOTOR.getChannel());
		_shoulderMotorAndEncoder = new WPI_TalonSRX(RobotMap.Talon.SHOULDER_MOTOR.getChannel());
		_wristMotorAndEncoder = new WPI_TalonSRX(RobotMap.Talon.WRIST_MOTOR.getChannel());
		
		_elbowMotorAndEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		_shoulderMotorAndEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		_wristMotorAndEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		_elbowAngle = -90.0;
		_shoulderAngle = 120.0;
		_wristAngle = 90.0;
				
	}
	
	public static ArmSubsystem getInstance(){
		if(_instance == null)
			_instance = new ArmSubsystem();
		
		return _instance;
	}
	
	public int getShoulderPosition() {
		return _shoulderMotorAndEncoder.getSelectedSensorPosition(0);
	}
	
	public int getElbowPosition() {
		return _elbowMotorAndEncoder.getSelectedSensorPosition(0);	
	}
	
	public int getWristPosition() {
		return _wristMotorAndEncoder.getSelectedSensorPosition(0);
	}
	
	public double getWristAngle() {
		return _wristAngle;
	}
	
	public double getShoulderAngle() {
		return _shoulderAngle;
	}
	
	public double getElbowAngle() {
		return _elbowAngle;
	}
	
	public RobotArmCalculations.HandState getHandState() {
		return _handState;
	}
	
	public void setHandState(RobotArmCalculations.HandState position) {
		_handState = position;
	}
	
	public void setMotorSpeeds(double shoulderMotor, 
							   double elbowMotor, 
							   double wristMotor) {
		_shoulderAngle = (shoulderMotor * 3) + _shoulderAngle;
		_elbowAngle = (elbowMotor * 3) + _elbowAngle;
		_wristAngle = (wristMotor * 3) + _wristAngle;
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
