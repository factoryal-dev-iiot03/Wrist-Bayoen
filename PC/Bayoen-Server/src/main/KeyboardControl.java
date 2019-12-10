package main;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyboardControl {
	ArrayList<KeyThread> list;
	
	public KeyboardControl() {
		list = new ArrayList<>();
		
		list.add(new KeyThread('W', KeyEvent.VK_UP));
		list.add(new KeyThread('A', KeyEvent.VK_LEFT));
		list.add(new KeyThread('S', KeyEvent.VK_DOWN));
		list.add(new KeyThread('D', KeyEvent.VK_RIGHT));
		list.add(new KeyThread('F', KeyEvent.VK_ENTER));
		list.add(new KeyThread('I', KeyEvent.VK_ESCAPE));
		list.add(new KeyThread('J', KeyEvent.VK_Z));
		list.add(new KeyThread('K', KeyEvent.VK_UP));
		list.add(new KeyThread('L', KeyEvent.VK_X));
		list.add(new KeyThread('H', KeyEvent.VK_SPACE));
		
		for(Thread t : list) {
			t.start();
		}
	}                    
	
	public void control(char signal) {
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
			case 'H':
				KeyState.setState(signal, true); break;
						
			case 'w':
			case 'a': 
			case 's':
			case 'd':
			case 'f':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'h':
				KeyState.setState(Character.toUpperCase(signal), false); break;
			default:return;
		}
	}
}
