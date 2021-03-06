import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyboardControl {
	Robot robot;
	public KeyboardControl() {
		try {
			robot = new Robot();
		} catch(AWTException e) {}
	}
	
	public void control(char signal) {
		switch(signal) {
			case 'W': robot.keyPress(KeyEvent.VK_UP); break;
			case 'A': robot.keyPress(KeyEvent.VK_LEFT); break;
			case 'S': robot.keyPress(KeyEvent.VK_DOWN); break;
			case 'D': robot.keyPress(KeyEvent.VK_RIGHT); break;
			case 'F': robot.keyPress(KeyEvent.VK_ENTER); break;
			case 'I': robot.keyPress(KeyEvent.VK_ESCAPE); break;
			case 'J': robot.keyPress(KeyEvent.VK_Z); break;
			case 'K': robot.keyPress(KeyEvent.VK_K); break;
			case 'L': robot.keyPress(KeyEvent.VK_X); break;
			case 'H': robot.keyPress(KeyEvent.VK_SPACE); break;
			default:return;
		}
	}
}
