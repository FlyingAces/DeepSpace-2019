package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.config.MotorSpeeds;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class DriveTrainSubsystem extends Subsystem {
	private static DriveTrainSubsystem _instance; 
	
	public ADXRS450_Gyro _gyro;
	
	private DifferentialDrive _wheels;
	
	private DriveTrainSubsystem() {
		super("DriveTrain");
		
		_gyro = new ADXRS450_Gyro();
		LiveWindow.addSensor("DriveTrain", "gyro", _gyro);
		
		WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.Talon.LEFT_BACK.getChannel());
		//leftMaster.setInverted(true);
		LiveWindow.addActuator("DriveTrain", "leftMaster", leftMaster);
		WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.Talon.RIGHT_BACK.getChannel());
		LiveWindow.addActuator("DriveTrain", "rightMaster", rightMaster);
		WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.Talon.LEFT_FRONT.getChannel());
		//leftSlave.setInverted(true);
		LiveWindow.addActuator("DriveTrain", "leftSlave", leftSlave);
		WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.Talon.RIGHT_FRONT.getChannel());
		LiveWindow.addActuator("DriveTrain", "rightSlave", rightSlave);
		
		 SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftMaster, leftSlave);
		 SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightMaster, rightSlave);

		_wheels = new DifferentialDrive(leftGroup, rightGroup);
		_wheels.setSafetyEnabled(false);

	}
	
	public void arcadeDrive(double moveValue, double rotateValue){
		if(DriverStation.getInstance().isOperatorControl()) 
			moveValue *= MotorSpeeds.TELEOP_MULTIPLIER;
		else
			moveValue *= MotorSpeeds.AUTONOMOUS_MULTIPLIER;
		
		_wheels.arcadeDrive(moveValue * MotorSpeeds.DRIVE_SPEED_ACCEL, rotateValue * MotorSpeeds.DRIVE_SPEED_TURN);
	}	
	
	public static DriveTrainSubsystem getInstance(){
		if(_instance == null)
			_instance = new DriveTrainSubsystem();
		
		return _instance;
	}
	
	public double gyroAngle() {
		return _gyro.getAngle();
	}
		
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
