package service.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractConfig {
	/**
	 * names are exposed to prevent spelling errors.
	 */
	public static String QUEUE_NAME = "queueName";
	public static String MQ_USER_NAME = "mqUsername";
	public static String MQ_PASSWORD = "mqPassword";
	public static String MQ_HOST = "mqHost";
	public static String MQ_VIRTUAL_HOST = "mqVirtualHost";
	public static String MQ_PORT = "mqPort";
	
	/**
	 * 
	 * @param propertyName - the name of the property file
	 * @return java.util Property Object
	 * @throws IOException
	 */
	public static Properties load(String propertyName) throws IOException {

		InputStream is = AbstractConfig.class.getClassLoader().getResourceAsStream(propertyName);
		if(is == null){
			throw new IOException("Could not load properties file:" + propertyName);
		}
		Properties props = new Properties();
		props.load(is);
		return props;
	}
	/**
	 * 
	 * @param propertyName - the name of the property file
	 * @return MQConnectionCredentials Object
	 * @throws IOException
	 */
	public static MQConnectionCredentials getMQConnectionCredentials(
			String propertyName) throws IOException {

		Properties props = load(propertyName);

		MQConnectionCredentials credentials = new MQConnectionCredentials.Builder()
				.username(props.getProperty(MQ_USER_NAME))
				.password(props.getProperty(MQ_PASSWORD))
				.host(props.getProperty(MQ_HOST))
				.virtualHost(props.getProperty(MQ_VIRTUAL_HOST))
				.port(Integer.parseInt(props.getProperty(MQ_PORT )))
				.queueName(props.getProperty(QUEUE_NAME)).build();

		return credentials;

	}
}
