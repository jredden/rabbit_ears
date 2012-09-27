package service.messages;

import org.junit.Before;
import org.junit.Test;

import service.config.ConfigException;
import service.config.CredentialSignature;
import service.config.MQConnectionCredentials;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class RouteConsumer1Test extends AbstractBasicTest {

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
			queueName = super.getMessageOperations().queueNameAndExchangeRoute(channel, EXCHANGE2_NAME, getRoutes().get(1));
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
			System.out.println("consumed message " + message);
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

}
