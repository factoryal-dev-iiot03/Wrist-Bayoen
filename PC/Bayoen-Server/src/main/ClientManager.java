package main;
import java.util.ArrayList;

public class ClientManager implements Runnable{
	ArrayList<Client> clients;
	int devices;
	
	public ClientManager() {
		clients = new ArrayList<>();
		devices = 0;
	}
	
	public void addClient(Client client) {
		if(devices <= 2) {
			client.start();
			clients.add(client);
			devices++;
		} else {
			//client.stopThread();
		}
	}
	
	public void run() {
		while(true) {
			for (Client client:clients) {
				/*if(client.getState() == Thread.State.TERMINATED) {
					client.stopThread();
					clients.remove(client);
					devices--;
				}*/
				System.out.println(client.getState());
			}
			
			try {
				System.out.println(devices);
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
