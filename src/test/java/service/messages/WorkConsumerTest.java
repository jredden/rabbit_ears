package service.messages;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.config.ConfigException;
import service.config.CredentialSignature;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class WorkConsumerTest extends AbstractBasicTest {
	private static Logger log = LoggerFactory.getLogger(WorkConsumerTest.class);
	private Channel channel;
	private QueueingConsumer queueingConsumer;

	@Before
	public void setUp() {
		try {
			channel = super.setUpChannel(CredentialSignature.WORK_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
		try {
			queueingConsumer = super.getMessageOperations().createConsumerAck(
					channel, super.getMQConnectionCredentials());
		} catch (MessageException me) {
			me.printStackTrace();
		}
	}

	@Test
	public void simpleConsumerTest() {
		String message = null;
		while (true) {
			try {
				message = super.getMessageOperations().readAndAcknowledge(queueingConsumer, channel);
			} catch (MessageException me) {
				me.printStackTrace();
			}
			log.info("work consumed message " + message);
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

}
