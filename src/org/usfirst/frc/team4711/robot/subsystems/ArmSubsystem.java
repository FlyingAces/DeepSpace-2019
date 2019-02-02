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
	
	private BaseMotorController _elbowEncoder;
	private BaseMotorController _shoulderEncoder;
	private BaseMotorController _wristEncoder;

	public ArmSubsystem() {
		super("ArmSubsystem");
		
		WPI_TalonSRX elbowMotor = new WPI_TalonSRX(RobotMap.Talon.ELBOW_MOTOR.getChannel());
		WPI_TalonSRX shoulderMotor = new WPI_TalonSRX(RobotMap.Talon.SHOULDER_MOTOR.getChannel());
		WPI_TalonSRX wristMotor = new WPI_TalonSRX(RobotMap.Talon.WRIST_MOTOR.getChannel());
		
		_elbowEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		_shoulderEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		_wristEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
	}
	
	public static ArmSubsystem getInstance(){
		if(_instance == null)
			_instance = new ArmSubsystem();
		
		return _instance;
	}
	
	public int getShoulderPosition() {
		return _shoulderEncoder.getSelectedSensorPosition(0);
	}
	
	public int getElbowPosition() {
		return _elbowEncoder.getSelectedSensorPosition(0);	
	}
	
	public int getWristPotistion() {
		return _wristEncoder.getSelectedSensorPosition(0);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
