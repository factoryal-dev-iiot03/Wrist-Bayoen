package main;

import javax.swing.JFrame;

public class Main{
	static public void main(String [] args) throws Exception {
		
		_SystemTray systemTray = _SystemTray.getTray();
		TCPServer server = new TCPServer();
		systemTray.setAlarm(server);
		server.setTray(systemTray.getTray());
		server.start();
	}
}
