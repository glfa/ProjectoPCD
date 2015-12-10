package server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import msg.QuequeMessage;


public class ServerOut extends Thread {

	private Socket 				clientSocket;
	private QuequeServer 		server;
	private ObjectOutputStream 	out;

	public ServerOut(Socket clientSocket, QuequeServer server) {
		this.clientSocket = clientSocket;
		this.server = server;
	}

	private void connect(Socket socket) throws IOException {
		out = new ObjectOutputStream(socket.getOutputStream());		
	}

	@Override
	public void run() {
		super.run();
		try {
			connect(clientSocket);
			server.sendMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
