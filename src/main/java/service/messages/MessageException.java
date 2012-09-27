package service.messages;

public class MessageException extends Exception {
	
	public MessageException() {
	}
	public MessageException(String exception) {
		super(exception);
	}
	public MessageException(Throwable throwable) {
		super(throwable);
	}
	public MessageException(String exception, Throwable throwable) {
		super(exception, throwable);
	}

}
