package service.config;

import java.util.HashMap;
import java.util.Map;

import service.configurations.Route;
import service.configurations.Simple;
import service.configurations.Topic;
import service.configurations.Work;
import service.configurations.ProducerConsumer;

public class Registry {
	/**
	 * Any new queue definitions go into this map,  they must implement the CredentialSignature interface.
	 */
	private static Map<String, CredentialSignature> credentialMap = new HashMap<String, CredentialSignature>();
	static{
		credentialMap.put(CredentialSignature.SIMPLE_PROPERTIES, new Simple());
		credentialMap.put(CredentialSignature.WORK_PROPERTIES, new Work());
		credentialMap.put(CredentialSignature.PRODUCER_CONSUMER_PROPERTIES, new ProducerConsumer());
		credentialMap.put(CredentialSignature.ROUTE_PROPERTIES, new Route());
		credentialMap.put(CredentialSignature.TOPIC_PROPERTIES, new Topic());
	}
	/**
	 * 
	 * @param property_name an exposed String Object in the CredentialSignature interface.
	 * @return MQConnectionCredentials Object
	 * @throws ConfigException
	 */
	public MQConnectionCredentials buildCredentials(String property_name) throws ConfigException{
		CredentialSignature credentialSignature = credentialMap.get(property_name);
		if(null == credentialSignature){
			throw new ConfigException("unregistered credential request:" +property_name);
		}
		return credentialSignature.credential();
	}
}
