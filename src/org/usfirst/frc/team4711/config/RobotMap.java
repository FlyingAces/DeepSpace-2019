package org.usfirst.frc.team4711.config;

public class RobotMap {
	
	
	public static final double SPEED_MULTIPLIER = 1.2;
	
	public static enum Talon {
		LEFT_FRONT(4),
		LEFT_BACK(5),
		RIGHT_FRONT(7),
		RIGHT_BACK(8);
		
		private int _channel;
		
		private Talon(int channel) {
			_channel = channel;
		}
		
		public int getChannel() {
			return _channel;
		}
	}
	
	public static enum Measurement {
		//width of inside of track
		ROBOT_WIDTH(21.875),
		ROBOT_LENGTH(31.0),
		ROBOT_HEIGHT(15.5),
		WHEEL_DIAMETER(6.00),
		SHOULDER_SEGMENT_LENGTH(25.6),
		ELBOW_SEGMENT_LENGTH(29.0),
		HAND_HEIGHT(20.0),
		HAND_WIDTH(12.0);
		
		private double _inches;
		
		private Measurement(double inches) {
			_inches = inches;
		}
		
		public double getInches() {
			return _inches;
		}
	}
	
	public static enum Controller {
		JOYSTICK_PORT(0),
		AXIS_TRIGGER_LT(2),
		AXIS_TRIGGER_RT(3),
		TRIGGER_LB(5),
		TRIGGER_RB(6),
		AXIS_LEFT_X(0),
		AXIS_RIGHT_Y(5),
		A_BUTTON(1),
		B_BUTTON(2),
		X_BUTTON(3),
		Y_BUTTON(4);
		
		private int _channel;
		
		private Controller(int channel) {
			_channel = channel;
		}
		
		public int getChannel() {
			return _channel;
		}
		
	}
	
	public static final int CAMERA_FRONT = 0;
	//only can use 160x120, 320x240, 640x480
	public static final int CAMERA_IMG_WIDTH = 320;
	public static final int CAMERA_IMG_HEIGHT = 240;
}
