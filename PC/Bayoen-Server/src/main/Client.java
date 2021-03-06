package main;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread{
	String clientSentence;
	String capitalizedSentence;
	Socket connectionSocket;
	
	KeyboardControl keyboardControl;
	
	public Client(Socket socket) {
		this.connectionSocket = socket;
		keyboardControl = new KeyboardControl();
	}
	
	public void run() {
		try {
			BufferedReader inFromClient =
			new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
					   
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			do {
				clientSentence = inFromClient.readLine();
				
				for (int i = 0; i < clientSentence.length(); i++) {
					keyboardControl.control(clientSentence.charAt(i));
				}
				
				capitalizedSentence = clientSentence.toUpperCase() + '\n';
				outToClient.writeBytes(capitalizedSentence);
			}while(!clientSentence.equals("-1"));
			
			connectionSocket.close();
		}catch(Exception e) {
			
		}finally { 
			try {
				this.finalize();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
