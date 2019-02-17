package org.usfirst.frc.team4711.robot.commands;

public class MoveArmAlongAxisCommand extends MoveArmWristToCommand {
	public static enum Axis {
		X, Y;
	}
	
	protected Axis _axis;
	protected double _endLoc;
	protected double _dir;
	
	public MoveArmAlongAxisCommand(Axis axis, double endLoc) {
		super(MoveArmWristToCommand.USE_CURRENT_LOCATION, MoveArmWristToCommand.USE_CURRENT_LOCATION);
		
		_axis = axis;
		_endLoc = endLoc;
	}
	
	@Override
	protected void initialize() {
		switch(_axis) {
		case X:
			_wristTargetX = _arm.getWristTargetX();
			
			_dir = (_endLoc == _wristTargetX)? 0.0 : 
				   (_endLoc > _wristTargetX)? 1.0 : -1.0;
			
			_wristTargetX = _wristTargetX + _dir;
			if((_dir < 0.0)? _wristTargetX < _endLoc : _wristTargetX > _endLoc)
				_wristTargetX = _endLoc;
			break;
		case Y:
			_wristTargetY = _arm.getWristTargetY();
			
			_dir = (_endLoc == _wristTargetY)? 0.0 : 
				   (_endLoc > _wristTargetY)? 1.0 : -1.0;
			
			_wristTargetY = _wristTargetY + _dir;
			if((_dir < 0.0)? _wristTargetY < _endLoc : _wristTargetY > _endLoc)
				_wristTargetY = _endLoc;
			break;
		}

		super.initialize();
	}
	
	@Override
	protected boolean isFinished() {
		switch(_axis) {
		case X:
			if(super.isFinished() && _wristTargetX == _endLoc)
				return true;
			else if(super.isFinished()) {
				_wristTargetX = _wristTargetX + _dir;
				if((_dir < 0.0)? _wristTargetX < _endLoc : _wristTargetX > _endLoc)
					_wristTargetX = _endLoc;
				
				super.initialize();
			}
		case Y:
			if(super.isFinished() && _wristTargetY == _endLoc)
				return true;
			else if(super.isFinished()) {
				_wristTargetY = _wristTargetY + _dir;
				if((_dir < 0.0)? _wristTargetY < _endLoc : _wristTargetY > _endLoc)
					_wristTargetY = _endLoc;
				
				super.initialize();
			}
			break;
		}
		
		return false; 
	}

}
