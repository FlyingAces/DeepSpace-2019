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

}
