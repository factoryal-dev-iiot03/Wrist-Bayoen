package main;

import java.awt.AWTException;
import java.awt.Robot;

public class KeyThread extends Thread {
	
	char signal;
	int key;
	Robot robot;
	
	public KeyThread(char signal, int key) {
		this.signal = signal;
		this.key = key;
		new KeyState();
	}
	
	public void run() {
		try {
	        this.robot = new Robot();
	        
	        while(true) {
	        	if(KeyState.getState(signal)) {
			    	robot.keyPress(key);
			    	robot.delay(167);
			    	while(KeyState.getState(signal)) {
				        robot.keyPress(key);
				        robot.delay(33);
			    	}
				    robot.keyRelease(key);
			    }
			    robot.delay(1);
	        }
	    } catch (AWTException ex) {
	        // Do something with Exception
	    }
	}
}
