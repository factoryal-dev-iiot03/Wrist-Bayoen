import java.io.*;
import java.net.*;
import java.util.ArrayList;

class TCPServer{
	static ServerSocket socket;
	static int devices;

	public static void main(String argv[]) throws Exception {
		devices = 0;
		socket = new ServerSocket(6789);
		
		while (true) {
			Socket connectionSocket = socket.accept();
			System.out.println(connectionSocket.getLocalAddress());
			
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