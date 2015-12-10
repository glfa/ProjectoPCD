package client;

public class QuequeClient {

	private QuequeClientThread clientThread;
	private boolean online;
	private String clientName;
	

	public QuequeClient(String clientName) {

		this.clientName = clientName;
		clientThread = new QuequeClientThread(this);
		clientThread.start();
		
	}
	
	public boolean isOnline(){
		return online;
	}
	
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public static void main(String[] args) {
		new QuequeClient("Nome do cliente");
	}
}
