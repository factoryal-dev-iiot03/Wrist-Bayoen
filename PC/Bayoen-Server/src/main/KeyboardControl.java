package main;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyboardControl {
	Robot robot;
	Map<Character, KeyThread> map;
	KeyState keyState;
	
	public KeyboardControl() {
		try {
			robot = new Robot();
			//robot.setAutoDelay(50);
		} catch(AWTException e) {}
		
		keyState = new KeyState();
		
		map = new HashMap<>();
		
		map.put('W', new KeyThread('W', KeyEvent.VK_UP, keyState));
		map.put('A', new KeyThread('A', KeyEvent.VK_LEFT, keyState));
		map.put('S', new KeyThread('S', KeyEvent.VK_DOWN, keyState));
		map.put('D', new KeyThread('D', KeyEvent.VK_RIGHT, keyState));
		map.put('F', new KeyThread('F', KeyEvent.VK_ENTER, keyState));
		map.put('I', new KeyThread('I', KeyEvent.VK_ESCAPE, keyState));
		map.put('J', new KeyThread('J', KeyEvent.VK_Z, keyState));
		map.put('K', new KeyThread('K', KeyEvent.VK_UP, keyState));
		map.put('L', new KeyThread('L', KeyEvent.VK_X, keyState));
		map.put('H', new KeyThread('H', KeyEvent.VK_SPACE, keyState));
		
		for(Thread t : map.values()) {
			t.start();
		}
	}
	
	public void control(char signal) {
		Thread keyHold = null;
		switch(signal) {
			case 'W':
			case 'A':
			case 'S':
			case 'D':
			case 'F':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'H': KeyState.setState(signal, true); break;
						
			case 'w' : 
			case 'a' : 
			case 's' : 
			case 'd' : 
			case 'f' : 
			case 'i' : 
			case 'j' : 
			case 'k' : 
			case 'l' : 
			case 'h' : 
				KeyState.setState(Character.toUpperCase(signal), false); break;
			default:return;
		}
	}
	
	public void keyHold(int keyEvent) {
		while(true) {
			robot.keyPress(keyEvent); 
		}
	}
}
