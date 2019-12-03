import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TCPServer{
	static ServerSocket welcomeSocket;
	ClientManager clientManager;
	
	public void start() throws Exception {

		welcomeSocket = new ServerSocket(6789);
		//clientManager = new ClientManager();
		//clientManager.run();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("connect");

			Client client = new Client(connectionSocket);
			//clientManager.addClient(client);
			executorService.execute(client);
			//client.start();
			
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