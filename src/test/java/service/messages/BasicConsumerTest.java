package service.messages;

import org.junit.Before;
import org.junit.Test;

import service.config.ConfigException;
import service.config.CredentialSignature;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class BasicConsumerTest extends AbstractBasicTest {

	private Channel channel;
	private QueueingConsumer queueingConsumer;

	@Before
	public void setUp() {
		try {
			channel = super.setUpChannel(CredentialSignature.SIMPLE_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
		try {
			queueingConsumer = super.getMessageOperations().createConsumer(
					channel, super.getMQConnectionCredentials());
		} catch (MessageException me) {
			me.printStackTrace();
		}
	}

	@Test
	public void simpleConsumerTest() {
		String message = null;
		try {
			message = super.getMessageOperations().read(queueingConsumer);
		} catch (MessageException me) {
			me.printStackTrace();
		}
		System.out.println("consumed message "+message);
	}

}
