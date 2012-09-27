package service.config;

public class ConfigException extends Exception {
	
	public ConfigException() {
	}
	public ConfigException(String exception) {
		super(exception);
	}
	public ConfigException(Throwable throwable) {
		super(throwable);
	}
	public ConfigException(String exception, Throwable throwable) {
		super(exception, throwable);
	}

}
