package service.configurations;

import java.io.IOException;

import service.config.AbstractConfig;
import service.config.ConfigException;
import service.config.CredentialSignature;
import service.config.MQConnectionCredentials;

public class Route extends AbstractConfig implements CredentialSignature{

	@Override
	public MQConnectionCredentials credential() throws ConfigException {
		
		try {
			return super.getMQConnectionCredentials(ROUTE_PROPERTIES);
		} catch (IOException ioe) {
			throw new ConfigException(ioe);
		}
	}

}
