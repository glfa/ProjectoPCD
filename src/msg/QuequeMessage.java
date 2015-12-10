package msg;

public class QuequeMessage {

	private String msgText;
	private String receiver, sender;
	/**
	 * @param msgText
	 * @param receiver
	 */
	public QuequeMessage(String msgText, String receiver, String sender) {
		super();
		this.msgText = msgText;
		this.receiver = receiver;
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getSender() {
		return sender;
	}

}
