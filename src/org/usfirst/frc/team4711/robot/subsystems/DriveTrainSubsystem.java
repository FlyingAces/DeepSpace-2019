package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.config.MotorSpeeds;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class DriveTrainSubsystem extends Subsystem {
	private static DriveTrainSubsystem _instance; 
	
	private ADXRS450_Gyro _gyro;
	
	private BaseMotorController _leftEncoder;
	private BaseMotorController _rightEncoder;
	
	private DifferentialDrive _wheels;
	
	private DriveTrainSubsystem() {
		super("DriveTrain");
		
		_gyro = new ADXRS450_Gyro();
		LiveWindow.addSensor("DriveTrain", "gyro", _gyro);
		
		WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.Talon.LEFT_FRONT.getChannel());
		LiveWindow.addActuator("DriveTrain", "leftSlave", leftMaster);
		WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.Talon.RIGHT_FRONT.getChannel());
		LiveWindow.addActuator("DriveTrain", "rightSlave", rightMaster);
		WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.Talon.LEFT_BACK.getChannel());
		LiveWindow.addActuator("DriveTrain", "leftMaster", leftSlave);
		WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.Talon.RIGHT_BACK.getChannel());
		LiveWindow.addActuator("DriveTrain", "rightMaster", rightSlave);

		 SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftMaster, leftSlave);
		 SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightMaster, rightSlave);

		_wheels = new DifferentialDrive(leftGroup, rightGroup);
		_wheels.setSafetyEnabled(false);
		
		_leftEncoder = leftSlave;
		_rightEncoder = rightSlave;
        
        _leftEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        _leftEncoder.setSelectedSensorPosition(_leftEncoder.getSensorCollection().getPulseWidthPosition() & 0xfff, 0, 0);
        _leftEncoder.setSensorPhase(true);
        
        _rightEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        _rightEncoder.setSelectedSensorPosition(_rightEncoder.getSensorCollection().getPulseWidthPosition() & 0xfff, 0, 0);

	}
	
	public void arcadeDrive(double moveValue, double rotateValue){
		if(DriverStation.getInstance().isOperatorControl()) 
			moveValue *= MotorSpeeds.TELEOP_MULTIPLIER;
		else
			moveValue *= MotorSpeeds.AUTONOMOUS_MULTIPLIER;
		
		_wheels.arcadeDrive(moveValue * MotorSpeeds.DRIVE_SPEED_ACCEL, rotateValue * MotorSpeeds.DRIVE_SPEED_TURN);
	}
	
	public void tankDrive(double leftMoveValue, double rightMoveValue) {
		if(DriverStation.getInstance().isOperatorControl()) {
			rightMoveValue *= MotorSpeeds.TELEOP_MULTIPLIER;
			leftMoveValue *= MotorSpeeds.TELEOP_MULTIPLIER; 
		} else {
			rightMoveValue *= MotorSpeeds.AUTONOMOUS_MULTIPLIER;
			leftMoveValue *= MotorSpeeds.AUTONOMOUS_MULTIPLIER;
		}
		
		_wheels.tankDrive(leftMoveValue, rightMoveValue);
	}
	
	public static DriveTrainSubsystem getInstance(){
		if(_instance == null)
			_instance = new DriveTrainSubsystem();
		
		return _instance;
	}
	
	public int getCurrentLeftPosition() {
		return _leftEncoder.getSelectedSensorPosition(0);
	}
	
	public int getCurrentLeftVelocity() {
		return _leftEncoder.getSelectedSensorVelocity(0);
	}
	
	public int getCurrentRightPosition() {
		return _rightEncoder.getSelectedSensorPosition(0);
	}
	
	public int getCurrentRightVelocity() {
		return _rightEncoder.getSelectedSensorVelocity(0);
	}
	
	public double gyroAngle() {
		return _gyro.getAngle();
	}
		
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
