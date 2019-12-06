package main;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JOptionPane;

class TCPServer{
	static ServerSocket welcomeSocket;
	Future<Client> future;
	ExecutorService executorService;
	_SystemTray tray;
	final int port = 62319;
	
	@SuppressWarnings("unchecked")
	public void start() {

		try {
			welcomeSocket = new ServerSocket(port);
			
			executorService = Executors.newFixedThreadPool(1);
			
			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				tray.displayMessage("Connect");
				
				Client client = new Client(connectionSocket);
				future = (Future<Client>) executorService.submit(client);
				try {
					if(future.get() == null) {
						tray.displayMessage("Disconnect");
					}
				}catch(CancellationException | InterruptedException | ExecutionException e) {
					tray.displayMessage("Disconnect");
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, String.format("%d번 포트가 이미 사용중입니다. 종료합니다.", port), "!!Error!!", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		
	}
	
	public void setTray(_SystemTray _systemTray) {
		this.tray = _systemTray;
	}
	
	public boolean disconnect() {
		if(future != null) {
			try {
				future.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				future.cancel(true);
			}
			return true;
		}
		return false;
	}
	
	public void shutdown() {
		executorService.shutdownNow();
	}
}