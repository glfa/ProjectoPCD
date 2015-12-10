package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import msg.QuequeMessage;


public class QuequeServer {

	public static final int PORT = 8080;
	
	/**
	 * Lista de contactos que estão ligados ao servidor.
	 */
	private HashMap<String, Integer> onlineContacts;
	
	/**
	 * Lista de mensagens que estão no servidor a espera de ser enviadas para os clientes.
	 * Cada cliente só pode ter uma mensagem associada.
	 */
	private HashMap<String, QuequeMessage> messages;
	

	public QuequeServer() {

		System.out.println("O servidor iniciou.");

		messages = new HashMap<>();
		onlineContacts = new HashMap<>();
		try {
			startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startServer() throws IOException {

		ServerSocket serverSocket = new ServerSocket(PORT);
		try{
			int i = 1;
			int port;
			while (true) {

				Socket clientSocket = serverSocket.accept();
				port = clientSocket.getPort();

				//servidor lança as threads para estabelecer que servem o cliente que se ligou.
				new ServerIn(clientSocket, this).start();
				new ServerOut(clientSocket, this).start();
				
				//adiciona o cliente que se ligou à lista de contactos online
				onlineContacts.put("nome do contacto", port);

				System.out.println("O servidor ligou-se a um cliente | " + i + " cliente(s) no total");
						
				i++;

			}
		}finally{
			serverSocket.close();
		}
	}

	/**
	 * Envia mensagem retirando a da lista de mensagens por enviar do servidor.
	 * 
	 */
	public synchronized void sendMessage(){

		while (messages.isEmpty()) { // se a lista de mensagens nao tiver msgs para enviar
			try {
				System.out.println("Estou a espera de enviar mensagens");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO: falta enviar as mensagens aos clientes online
		
		notifyAll();		
	}

	/**
	 * Recebe mensagem do cliente
	 */
	public synchronized void getMessage(QuequeMessage message){
		while (messages.containsKey(message)) {
			try {
				System.out.println("tou a espera de receber mensagens");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		messages.put(message.getReceiver(), message);
		System.out.println("Recebi esta mensagem: " + message + " | enviada pelo :" + message.getSender());
		notifyAll();

	}

	public HashMap<String, QuequeMessage> getMessages() {
		return messages;
	}

	private boolean clientIsOnline(String clientName){
		return onlineContacts.get(clientName) != null;
	}
		
	public static void main(String[] args) {
		QuequeServer server = new QuequeServer();
				
	}


}
