package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class DriveTrain extends Subsystem {
	private static DriveTrain _instance; 
	
	public ADXRS450_Gyro _gyro;
	
	private DifferentialDrive _wheels;
	
	private DriveTrain() {
		super("DriveTrain");
		
		_gyro = new ADXRS450_Gyro();
		LiveWindow.addSensor("DriveTrain", "gyro", _gyro);
		
		WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.Talon.LEFT_BACK.getChannel());
		LiveWindow.addActuator("DriveTrain", "leftMaster", leftMaster);
		WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.Talon.RIGHT_BACK.getChannel());
		LiveWindow.addActuator("DriveTrain", "rightMaster", rightMaster);
		WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.Talon.LEFT_FRONT.getChannel());
		LiveWindow.addActuator("DriveTrain", "leftSlave", leftSlave);
		WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.Talon.RIGHT_FRONT.getChannel());
		LiveWindow.addActuator("DriveTrain", "rightSlave", rightSlave);

	}
	
	public static DriveTrain getInstance(){
		if(_instance == null)
			_instance = new DriveTrain();
		
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
