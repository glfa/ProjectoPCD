package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import msg.QuequeMessage;


public class ServerIn extends Thread {

	private Socket 				clientSocket;
	private QuequeServer 		server;
	private ObjectInputStream 	in;

	public ServerIn(Socket clientSocket, QuequeServer server) {
		this.clientSocket = clientSocket;
		this.server = server;
	}

	private void connect() throws IOException {
		in = new ObjectInputStream(clientSocket.getInputStream());
	}

	@Override
	public void run() {		
		super.run();
		try {
			connect();
			server.getMessage((QuequeMessage)in.readObject());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				System.out.println("fechei a socket");
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
