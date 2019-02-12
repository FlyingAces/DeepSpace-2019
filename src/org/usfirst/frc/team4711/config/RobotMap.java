package org.usfirst.frc.team4711.config;

public class RobotMap {
	
	
	public static final double SPEED_MULTIPLIER = 1.2;
	
	public static enum Talon {
		WRIST_MOTOR(0),
		ELBOW_MOTOR(0),
		SHOULDER_MOTOR(0),
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
		BASE_SEGMENT_LENGTH(20.0),
		BASE_CONNECTION(8.0),
		SHOULDER_SEGMENT_LENGTH(25.6),
		ELBOW_SEGMENT_LENGTH(35.0),
		HAND_HEIGHT(20.0),
		HAND_WIDTH(12.0),
		MAX_EXTENSION(30.0);
		
		private double _inches;
		
		private Measurement(double inches) {
			_inches = inches;
		}
		
		public double getInches() {
			return _inches;
		}
	}
	
	public static enum Angle {
		SHOULDER_START_ANGLE(0.0),
		SHOULDER_MAX_ANGLE(150.0),
		SHOULDER_MIN_ANGLE(-150.0),
		ELBOW_START_ANGLE(140.0),
		ELBOW_MAX_ANGLE(175.0),
		ELBOW_MIN_ANGLE(-175.0),
		WRIST_START_ANGLE(110.0),
		WRIST_MAX_ANGLE(170.0),
		WRIST_MIN_ANGLE(-170.0);
		
		private double _angle;
		
		private Angle(double angle) {
			_angle = angle;
		}
		
		public double getAngle() {
			return _angle;
		}
	}
	
	public static enum Controller {
		JOYSTICK_PORT(0),
		AXIS_TRIGGER_LT(2),
		AXIS_TRIGGER_RT(3),
		TRIGGER_LB(5),
		TRIGGER_RB(6),
		AXIS_LEFT_X(0),
		AXIS_LEFT_Y(1),
		AXIS_RIGHT_X(4),
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
	
	public static final double ROBOT_FRONT_X = Measurement.ROBOT_LENGTH.getInches() - 
											   Measurement.BASE_CONNECTION.getInches();
	public static final double ROBOT_BACK_X = -Measurement.BASE_CONNECTION.getInches();
	
	public static final double GROUND_LEVEL_Y = -(Measurement.BASE_SEGMENT_LENGTH.getInches() + 
												  Measurement.ROBOT_HEIGHT.getInches()); 

	public static final double PICK_UP_START_X = RobotMap.ROBOT_FRONT_X + 10.0;
	public static final double PICK_UP_START_Y = GROUND_LEVEL_Y + Measurement.HAND_WIDTH.getInches() + 15.0;
	
	public static final double PICK_UP_MAX_EXTENSION_X = ROBOT_FRONT_X + 
														 Measurement.MAX_EXTENSION.getInches() - 
														 Measurement.HAND_HEIGHT.getInches();
	public static final double PICK_UP_GROUND_LEVEL_Y = GROUND_LEVEL_Y + Measurement.HAND_WIDTH.getInches() + 2.0;
	
	public static final double PLACE_MAX_EXTENSION_X = ROBOT_FRONT_X + 
			 										   Measurement.MAX_EXTENSION.getInches() - 
			 										   Measurement.HAND_WIDTH.getInches();
	
	public static final int CAMERA_FRONT = 0;
	//only can use 160x120, 320x240, 640x480
	public static final int CAMERA_IMG_WIDTH = 320;
	public static final int CAMERA_IMG_HEIGHT = 240;
}
