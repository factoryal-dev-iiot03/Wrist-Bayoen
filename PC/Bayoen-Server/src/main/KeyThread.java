package main;

import java.awt.AWTException;
import java.awt.Robot;

public class KeyThread extends Thread {
	
	char signal;
	int key;
	Robot robot;
	public boolean keyHold = false;
	KeyState keyState;
	
	public KeyThread(char signal) {
		this.signal = signal;
	}
	
	public KeyThread(char signal, int key, KeyState keyState) {
		this.signal = signal;
		this.key = key;
		this.keyState = keyState;
		System.out.println(keyState);
	}
	
	public void run() {
		try {
	        this.robot = new Robot();
	        
	       /* switch(signal) {
				case 'W': key = KeyEvent.VK_UP; break;
				case 'A': key = KeyEvent.VK_LEFT; break;
				case 'S': key = KeyEvent.VK_DOWN; break;
				case 'D': key = KeyEvent.VK_RIGHT; break;
				case 'F': key = KeyEvent.VK_ENTER; break;
				case 'I': key = KeyEvent.VK_ESCAPE; break;
				case 'J': key = KeyEvent.VK_Z; break;
				case 'K': key = KeyEvent.VK_K; break;
				case 'L': key = KeyEvent.VK_X; break;
				case 'H': key = KeyEvent.VK_SPACE; break;
				default:return;
	        }*/

	        
	        //int duration = 3000;
	        //long start = System.currentTimeMillis();
	        //while (System.currentTimeMillis() - start < duration) {
	        while(true) {
			    //if(keyState.getState(signal)) {
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
		
	       /* try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	    } catch (AWTException ex) {
	        // Do something with Exception
	    }
	}
	public void stateChange(String state) {
		if(!state.equals("Press"))
			keyHold = true;
		else
			keyHold = false;
	}
}
