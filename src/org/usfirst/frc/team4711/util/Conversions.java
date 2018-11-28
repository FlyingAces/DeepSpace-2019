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
}
