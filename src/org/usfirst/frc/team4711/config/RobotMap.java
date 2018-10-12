package org.usfirst.frc.team4711.config;

public class RobotMap {
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

}
