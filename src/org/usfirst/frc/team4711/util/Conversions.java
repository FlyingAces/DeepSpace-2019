package org.usfirst.frc.team4711.util;

import org.usfirst.frc.team4711.config.RobotMap;

public class Conversions {
	public static double inchToEncoderPosition(double inches) {
		double circumference = RobotMap.Measurement.WHEEL_DIAMETER.getInches() * Math.PI;
		double encoderPosition = ((inches / circumference) * 4.0) * 1024.0;
		
		return encoderPosition;
	}
	
	public static double encoderPositionToInches(double encoderPosition) {
		double circumference = RobotMap.Measurement.WHEEL_DIAMETER.getInches() * Math.PI;
		double toInches = (encoderPosition / (4.0 * 1024.0)) * circumference;
		
		return toInches;
	}
	
	public static double angleToEncoderPosition(double angle) {
		double lengthOfArc = (angle / 360.0) * RobotMap.Measurement.ROBOT_WIDTH.getInches() * Math.PI;
		return inchToEncoderPosition(lengthOfArc);
	}
	
	public static int encoderPositionToAngle(int encoderPosition) {
		return (encoderPosition / 1024) / 4;
	}
	
	public static int angleToEncoderPosition(int angle) {
		return angle * 4 * 1024;
	}
	
	public static double radianToDegree(double radian) {
		return (radian * 180) / Math.PI;
	}
	
	public static double degreeToRadian(double degree) {
		return (degree * Math.PI) / 180;
	}
}
