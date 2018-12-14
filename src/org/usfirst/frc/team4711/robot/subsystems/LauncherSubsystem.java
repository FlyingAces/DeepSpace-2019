package org.usfirst.frc.team4711.robot.subsystems;

import org.usfirst.frc.team4711.config.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class LauncherSubsystem extends Subsystem{
	private static LauncherSubsystem _instance;
	
	private WPI_TalonSRX _masterShooter;
	private WPI_TalonSRX _slaveShooter;
	private WPI_TalonSRX _intake;
	
	private LauncherSubsystem() {
		super("Launcher");
		
		_masterShooter = new WPI_TalonSRX(RobotMap.Talon.MASTER_SHOOTER.getChannel());
		_slaveShooter = new WPI_TalonSRX(RobotMap.Talon.SLAVE_SHOOTER.getChannel());
		_intake = new WPI_TalonSRX(RobotMap.Talon.INTAKE.getChannel());


		
	}
	 
    public void setIntakeSpeeds(double intakeSpeed) {
    	_intake.set(intakeSpeed);
    
    	
    }
	
    public void setShooterSpeed(double shooterSpeed) {
    	_masterShooter.set(shooterSpeed);
    	_slaveShooter.set(-shooterSpeed);
    }

	
	public static LauncherSubsystem getInstance(){
		if(_instance == null)
			_instance = new LauncherSubsystem();
		
		return _instance;
		
		
		
	}


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}


}

