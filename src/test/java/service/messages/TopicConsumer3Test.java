package service.messages;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.config.ConfigException;
import service.config.CredentialSignature;
import service.config.MQConnectionCredentials;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class TopicConsumer3Test extends AbstractBasicTest {
	private static Logger log = LoggerFactory.getLogger(TopicConsumer3Test.class);
	private Channel channel;
	private QueueingConsumer queueingConsumer;
	private String queueName;

	@Before
	public void setUp() {
		try {
			channel = super.setUpChannel(CredentialSignature.ROUTE_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
		try {
			queueName = super.getMessageOperations().queueNameAndExchangeTopic(channel, EXCHANGE3_NAME, getPatterns().get(3));
		} catch (MessageException me) {
			me.printStackTrace();
		}
		MQConnectionCredentials mQConnectionCredentials = super.getMQConnectionCredentials();
		mQConnectionCredentials.setQueueName(queueName);
		
		try {
			queueingConsumer = super.getMessageOperations().createConsumer(channel, mQConnectionCredentials);
		} catch (MessageException me) {
			me.printStackTrace();
		}
	}

	@Test
	public void simpleConsumerTest() {
		String message = null;
		while (true) {
			try {
				message = super.getMessageOperations().read(queueingConsumer);
			} catch (MessageException me) {
				me.printStackTrace();
			}
			log.info("#3 topic consumed message " + message);
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

}
