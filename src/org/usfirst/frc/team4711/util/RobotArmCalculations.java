package org.usfirst.frc.team4711.util;

import java.util.Arrays;

import org.usfirst.frc.team4711.config.RobotMap;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class RobotArmCalculations {
	
	public static enum HandState {
		PICK_UP,
		PLACE,
		LOCKED;
	}
	
	private double _shoulderAngle;
	private double _elbowAngle;
	private double _wristAngle;
	
	private double _wristTargetX;
	private double _wristTargetY;
	
	private HandState _handState;
	
	private boolean _isInverted;
	
	private NetworkTableEntry _anglesEntry;
	private NetworkTableEntry _constantMeasurementsEntry;

	
	public RobotArmCalculations() {
		_shoulderAngle = 0.0;
		_elbowAngle = 0.0;
		_wristAngle = 0.0;
		_isInverted = true;
		_wristTargetX = 35;
		_wristTargetY = 15;
		_handState = HandState.PLACE;
		
		NetworkTableInstance.getDefault().startClientTeam(4711);
		NetworkTableInstance.getDefault().startDSClient();
		NetworkTable armFeed = NetworkTableInstance.getDefault().getTable("robotArmFeed");

		_anglesEntry = armFeed.getEntry("angles");
		_constantMeasurementsEntry = armFeed.getEntry("measurements");
		
		calculate();
	}
	
	public void sendInfo() {
		double[] anglesArray = {_shoulderAngle, _elbowAngle, _wristAngle};
		double[] measurementsArray = {RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches(), 
				RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches(),
				RobotMap.Measurement.HAND_HEIGHT.getInches()}; 
		
		_anglesEntry.setDoubleArray(anglesArray);
		_constantMeasurementsEntry.setDoubleArray(measurementsArray);
	}
	
	private void calculate() {
		double distance = distance(_wristTargetX, _wristTargetY);
		double d1 = Conversions.radianToDegree(Math.atan2(_wristTargetY, _wristTargetX));
		double d2 = lawOfCosines(
				distance, 
				RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches(), 
				RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches());
		
		_shoulderAngle = (!_isInverted) ?
				90.0 - (d1 + d2) :
				90.0 - (d1 - d2);
		
		_elbowAngle = (!_isInverted) ?
				180.0 - lawOfCosines(
						RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches(), 
						RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches(), 
						distance) :
				-(180.0 - lawOfCosines(
						RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches(), 
						RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches(), 
						distance));
						
		switch (_handState) {
		case PICK_UP:
			_wristAngle = 90.0 - (_shoulderAngle + _elbowAngle);
			break;
		case PLACE:
			_wristAngle = -(_shoulderAngle + _elbowAngle);
			break;
		default:
			_wristAngle = _wristAngle;
			break;
		}	
		
		sendInfo();
	}
	
	private double lawOfCosines(double a, double b, double c) {
		return Conversions.radianToDegree(Math.acos((a * a + b * b - c * c) / (2 * a * b)));
	}
	
	private double distance(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}
	
	public double getWristTargetX() {
		
	}
	
	public double getWristTargetY() {
		
	}
}