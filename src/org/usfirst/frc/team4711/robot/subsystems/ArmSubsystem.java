package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmSubsystem extends Subsystem {

	public ArmSubsystem() {
		WPI_TalonSRX elbowMotor = new WPI_TalonSRX(RobotMap.Talon.ELBOW_MOTOR.getChannel());
		WPI_TalonSRX shoulderMotor = new WPI_TalonSRX(RobotMap.Talon.SHOULDER_MOTOR.getChannel());
		WPI_TalonSRX wristMotor = new WPI_TalonSRX(RobotMap.Talon.WRIST_MOTOR.getChannel());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
