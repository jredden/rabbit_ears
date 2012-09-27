package service.config;

public interface CredentialSignature {
	
	public static String SIMPLE_PROPERTIES = "simple-application.properties";
	public static String WORK_PROPERTIES = "work-application.properties";
	public static String PRODUCER_CONSUMER_PROPERTIES = "producer-consumer-application.properties";
	public static String ROUTE_PROPERTIES = "route-application.properties";
	public static String TOPIC_PROPERTIES = "topic-application.properties";
	
	public MQConnectionCredentials credential() throws ConfigException;
}
