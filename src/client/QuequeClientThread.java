package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import server.QuequeServer;

public class QuequeClientThread extends Thread {

	private QuequeClient quequeClient;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public QuequeClientThread(QuequeClient quequeClient) {
		this.quequeClient = quequeClient;
	}

	private void connectToServer() throws IOException{
		InetAddress inetAddress = InetAddress.getByName(null);
		socket = new Socket(inetAddress, QuequeServer.PORT);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		System.out.println("liguei me com sucesso");
		quequeClient.setOnline(true);

	}

	private void serveClient() throws InterruptedException {
		while (quequeClient.isOnline()) {
			wait();
		}
	}

	@Override
	public void run() {
		try {
			connectToServer();
			serveClient();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
