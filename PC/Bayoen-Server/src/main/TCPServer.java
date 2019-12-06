package main;
import java.net.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class TCPServer{
	static ServerSocket welcomeSocket;
	ClientManager clientManager;
	Future<Client> future;
	ExecutorService executorService;
	_SystemTray tray;
	
	@SuppressWarnings("unchecked")
	public void start() throws Exception {

		welcomeSocket = new ServerSocket(6789);
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
			}catch(CancellationException e) {
				tray.displayMessage("Disconnect");
			}
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