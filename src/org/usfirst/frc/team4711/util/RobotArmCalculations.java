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

	
	public RobotArmCalculations(double shoulderAngle, double elbowAngle, HandState handState) {
		_shoulderAngle = limitShoulderAngle(shoulderAngle);
		_elbowAngle = limitElbowAngle(elbowAngle);
		_wristAngle = 0.0;
		_isInverted = (_elbowAngle < 0);
		_handState = handState;
		
		NetworkTableInstance.getDefault().startClientTeam(4711);
		NetworkTableInstance.getDefault().startDSClient();
		NetworkTable armFeed = NetworkTableInstance.getDefault().getTable("robotArmFeed");

		_anglesEntry = armFeed.getEntry("angles");
		_constantMeasurementsEntry = armFeed.getEntry("measurements");
		
		calculateAllAngles();
	}
	
	public void sendInfo() {
		double[] anglesArray = {_shoulderAngle, _elbowAngle, _wristAngle};
		double[] measurementsArray = {RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches(), 
				RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches(),
				RobotMap.Measurement.HAND_HEIGHT.getInches()}; 
		
		_anglesEntry.setDoubleArray(anglesArray);
		_constantMeasurementsEntry.setDoubleArray(measurementsArray);
	}
	
	private void calculateAllAngles() {
		double distance = distance(_wristTargetX, _wristTargetY);
		double d1 = Conversions.radianToDegree(Math.atan2(_wristTargetY, _wristTargetX));
		double d2 = lawOfCosines(
				distance, 
				RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches(), 
				RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches());
		if(Double.isNaN(d2))
			d2 = 0.0;
		
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
						
		if(Double.isNaN(_elbowAngle))
			_elbowAngle = 0.0;
		
		if(_shoulderAngle != limitShoulderAngle(_shoulderAngle) || _elbowAngle != limitShoulderAngle(_elbowAngle)) {
			_shoulderAngle = limitShoulderAngle(_shoulderAngle);
			_elbowAngle = limitShoulderAngle(_elbowAngle);
			calculateWristTargetAndWristAngle();
		} else
			calculateWristAngle();

	}
	
	private void calculateWristTargetAndWristAngle() {
		
		double shoulderPolarAngle = Conversions.degreeToRadian(90.0-_shoulderAngle);
		double shoulderX = RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches()*Math.cos(shoulderPolarAngle);
		double shoulderY = RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches()*Math.sin(shoulderPolarAngle);

		double elbowPolarAngle = Conversions.degreeToRadian(90.0-_shoulderAngle-_elbowAngle);
		double elbowX = RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches()*Math.cos(elbowPolarAngle);
		double elbowY = RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches()*Math.sin(elbowPolarAngle);
		
		_wristTargetX = shoulderX + elbowX;
		_wristTargetY = shoulderY + elbowY;
		
		calculateWristAngle();

	}
	
	private void calculateWristAngle() {
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
		_wristAngle = limitWristAngle(_wristAngle);
	}
	
	private double lawOfCosines(double a, double b, double c) {
		return Conversions.radianToDegree(Math.acos((a * a + b * b - c * c) / (2 * a * b)));
	}
	
	private double distance(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}
	
	private double limitShoulderAngle(double angle) {
		return (angle < 0)? 
				Math.max(RobotMap.Measurement.SHOULDER_MIN_ANGLE.getInches(), angle): 
				Math.min(RobotMap.Measurement.SHOULDER_MAX_ANGLE.getInches(), angle);
	}
	
	private double limitElbowAngle(double angle) {
		return (angle < 0)?
				Math.max(RobotMap.Measurement.ELBOW_MIN_ANGLE.getInches(), angle):
				Math.min(RobotMap.Measurement.ELBOW_MAX_ANGLE.getInches(), angle);	
	}
	
	private double limitWristAngle(double angle) {
		return (angle < 0)?
				Math.max(RobotMap.Measurement.WRIST_MIN_ANGLE.getInches(), angle):
				Math.min(RobotMap.Measurement.WRIST_MAX_ANGLE.getInches(), angle);
	}

	public double getWristTargetX() {
		return _wristTargetX;
	}
	
	public double getWristTargetY() {
		return _wristTargetY;
	}
	
	public void setWristTargetX(double x) {
		if(((x*x)+(getWristTargetY()*getWristTargetY())) > 
		((RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches()+RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches())*
		 (RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches()+RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches()))) {
			double angle = Conversions.radianToDegree(Math.atan2(getWristTargetY(), x));
			_elbowAngle = 0.0;
			setShoulderAngle(90.0-angle);
		} else {
			_wristTargetX = x;
			calculateAllAngles();
		}
	}

	public void setWristTargetY(double y) {
		if(((y*y)+(getWristTargetX()*getWristTargetX())) > 
		((RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches()+RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches())*
		 (RobotMap.Measurement.SHOULDER_SEGMENT_LENGTH.getInches()+RobotMap.Measurement.ELBOW_SEGMENT_LENGTH.getInches()))) {
			double angle = Conversions.radianToDegree(Math.atan2(y, getWristTargetX()));
			_elbowAngle = 0.0;
			setShoulderAngle(90.0-angle);
		} else {
			_wristTargetY = y;
			calculateAllAngles();
		}
	}
	
	public double getShoulderAngle() {
		return _shoulderAngle; 
	}
	
	public void setShoulderAngle(double angle) {
		_shoulderAngle = limitShoulderAngle(angle);
		calculateWristTargetAndWristAngle();
	}
	
	public double getElbowAngle() {
		return _elbowAngle;
	}
	
	public void setElbowAngle(double angle) {
		_elbowAngle = limitElbowAngle(angle);
		_isInverted = (angle < 0);
		calculateWristTargetAndWristAngle();
	}
	
	public double getWristAngle() {
		return _wristAngle;
	}
	
	public void setWristAngle(double angle) {
		if(_handState != HandState.LOCKED)
			return;
		_wristAngle = limitWristAngle(angle);
		
	}
    
	public HandState getHandState() {
		return _handState;
		
	}
	
	public void setHandState(HandState handState) {
		_handState = handState;
		calculateWristAngle();
	}
	
	public boolean isInverted() {
		return _isInverted;
	}
	
	public void setInverted(boolean isInverted) {
		_isInverted = isInverted;
		calculateAllAngles();
	}

}