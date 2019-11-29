import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread{
	String clientSentence;
	String capitalizedSentence;
	Socket connectionSocket;
	
	public Client(Socket socket) {
		System.out.println(socket);
		this.connectionSocket = socket;
	}
	
	public void run() {
		
		try {
			BufferedReader inFromClient =
			new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
					   
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			do {
				
				clientSentence = inFromClient.readLine();
				System.out.println("Received: " + clientSentence);
					   
				capitalizedSentence = clientSentence.toUpperCase() + '\n';
				outToClient.writeBytes(capitalizedSentence);
			}while(!clientSentence.equals("-1\n"));
			connectionSocket.close();
		}catch(Exception e) {
			 try {
				this.finalize();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
