package service.messages;

public interface MQMessageReceiver {
	/**
	 * 
	 * @param message
	 * @throws MessageException
	 */
	void onMessageReceived(String message) throws MessageException;
}
