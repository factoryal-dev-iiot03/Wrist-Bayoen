package main;

public class Main{
	static public void main(String [] args) throws Exception {
		_SystemTray systemTray = _SystemTray.getTray();
		TCPServer server = new TCPServer();
		systemTray.setAlarm(server);
		server.start();
	}
}
