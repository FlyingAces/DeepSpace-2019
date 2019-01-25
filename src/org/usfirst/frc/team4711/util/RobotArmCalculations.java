package org.usfirst.frc.team4711.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class RobotArmCalculations {
	
	private double _baseAngle;
	private double _elbowAngle;
	private double _handAngle;
	
	private NetworkTableEntry _baseAngleEntry;
	
	public RobotArmCalculations() {
		_baseAngle = 0.0;
		_elbowAngle = 0.0;
		_handAngle = 0.0;
		
		NetworkTable armFeed = NetworkTableInstance.getDefault().getTable("armFeed");
		_baseAngleEntry = armFeed.getEntry("baseAngle");
	}
	
	public void update() {
		_baseAngle++;
		_elbowAngle++;
		_handAngle++;
	}
	
	public void sendInfo() {
		_baseAngleEntry.setDouble(_baseAngle);
	}
}
