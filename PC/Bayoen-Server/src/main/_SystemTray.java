package main;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;

public class _SystemTray{
	private static _SystemTray _systemTray = new _SystemTray();
	SystemTray systemTray;
	static TrayIcon trayIcon;
	TCPServer server;
	static Image image_play;
	static Image image_wait;

	public _SystemTray(){	
		
		systemTray = SystemTray.getSystemTray();
		try {
			image_play = ImageIO.read(getClass().getClassLoader().getResource("baseline_play_arrow_white_48dp.png"));
			image_wait = ImageIO.read(getClass().getClassLoader().getResource("baseline_pause_white_48dp.png"));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
				
		PopupMenu popup = new PopupMenu();
		MenuItem disconnect = new MenuItem("Disconnect");
		MenuItem exit = new MenuItem("Exit");

		popup.add(disconnect);
		popup.add(exit);

		disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.disconnect();
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.shutdown();
				System.exit(0);
			}
		});
		
		trayIcon = new TrayIcon(image_wait, "Bayoen-Server", popup);
		try {
			trayIcon.setImageAutoSize(true);
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	void setAlarm(TCPServer server) {
		this.server = server;
	}
	
	void setIcon(String state) {
		if(state.equals("Connect")) {
			trayIcon.setImage(image_play);
		} else if(state.equals("Disconnect")) {
			trayIcon.setImage(image_wait);
		}
	}
	
	void displayMessage(String str) {
		setIcon(str);
		trayIcon.displayMessage(null, str, TrayIcon.MessageType.INFO);
	}
	
	public static _SystemTray getTray() {
		return _systemTray;
	}
}
