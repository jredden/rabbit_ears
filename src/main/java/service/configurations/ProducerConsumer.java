package service.configurations;

import java.io.IOException;

import service.config.AbstractConfig;
import service.config.ConfigException;
import service.config.CredentialSignature;
import service.config.MQConnectionCredentials;

public class ProducerConsumer extends AbstractConfig implements CredentialSignature{

	@Override
	public MQConnectionCredentials credential() throws ConfigException {
		
		try {
			return super.getMQConnectionCredentials(PRODUCER_CONSUMER_PROPERTIES);
		} catch (IOException ioe) {
			throw new ConfigException(ioe);
		}
	}

}
