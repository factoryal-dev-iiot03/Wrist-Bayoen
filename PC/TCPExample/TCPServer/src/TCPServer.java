import java.io.*;
import java.net.*;
import java.util.ArrayList;

class TCPServer{
	//String clientSentence;
	//String capitalizedSentence;
	static ServerSocket welcomeSocket;
	//static ArrayList<Client> clients;
	public static void main(String argv[]) throws Exception {
		
		//clients = new ArrayList<>();
		welcomeSocket = new ServerSocket(6789);
		
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("connect");
			
			Client client = new Client(connectionSocket);
			client.start();
			   /*BufferedReader inFromClient =
			    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			   
			   DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			   clientSentence = inFromClient.readLine();
			   System.out.println("Received: " + clientSentence);
			   
			   capitalizedSentence = clientSentence.toUpperCase() + '\n';
			   outToClient.writeBytes(capitalizedSentence);*/
		}
	}
}