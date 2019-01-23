package org.usfirst.frc.team4711.robot.ui;

import org.usfirst.frc.team4711.config.RobotMap;
import org.usfirst.frc.team4711.robot.subsystems.CameraSubsystem;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class UI {
	private static UI _instance;
	private Thread _UIThread;
	private CvSource _cvSource;
	
	
	
	private UI() {
		_cvSource = CameraServer.getInstance().putVideo("UI", 620, 480);
	}
		
	public static UI getInstance() {
		if(_instance == null)
			_instance = new UI();
		
		return _instance;
	}
	
	public void start() {
		
		_UIThread = new Thread(() -> {
			
			
			while(!Thread.interrupted()){
				Mat frontImage = CameraSubsystem.getInstance().getFrontCameraPicture();
				Mat source = new Mat(640, 480, frontImage.type());
				
				frontImage.copyTo(source);
				
				Imgproc.putText(
						source, 
						"Beginning UI", 
						new Point(10, 50), 
						Core.FONT_HERSHEY_COMPLEX, 
						1, 
						new Scalar(255,255,255),
						1);
				
				_cvSource.putFrame(source);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		_UIThread.start();
	}
	
	public void end() {
		if(_UIThread.getState() == Thread.State.RUNNABLE)
			_UIThread.interrupt();
	}
}

