package org.usfirst.frc.team4711.robot.commands;

import org.usfirst.frc.team4711.util.RobotArmCalculations;

import edu.wpi.first.wpilibj.command.Command;

public class CommandRobotArmWithControler extends Command {
	
	private RobotArmCalculations _calculations;
	
	public CommandRobotArmWithControler() {
		
		_calculations = new RobotArmCalculations();
		
	}
	
	@Override
	protected void initialize() {
		
		
		
	}
	
	@Override
	protected void execute() {
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override 
	protected void end() {
		
	}

}
