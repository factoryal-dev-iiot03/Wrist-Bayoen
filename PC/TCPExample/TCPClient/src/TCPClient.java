import java.io.*;
import java.net.*;

class TCPClient {
public static void main(String argv[]) throws Exception {
  String sentence;
  String modifiedSentence;
  
  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
  Socket clientSocket = new Socket("localhost", 6789);
  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  do {
	  sentence = inFromUser.readLine();
	  outToServer.writeBytes(sentence + '\n');
	  modifiedSentence = inFromServer.readLine();
	  System.out.println("FROM SERVER: " + modifiedSentence);
  } while(!sentence.equals("-1\n"));
  clientSocket.close();
 }
/*
 public static void main(String[] args) {
        String hostname = "time.nist.gov";
        int port = 13;
 
        try (Socket socket = new Socket(hostname, port)) {
 
            InputStream input = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
 
            int character;
            StringBuilder data = new StringBuilder();
 
            while ((character = reader.read()) != -1) {
                data.append((char) character);
            }
 
            System.out.println(data);
 
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
*/
}