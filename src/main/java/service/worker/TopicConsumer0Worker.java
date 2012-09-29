package service.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.config.ConfigException;
import service.config.CredentialSignature;
import service.config.MQConnectionCredentials;
import service.messages.MessageException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class TopicConsumer0Worker extends AbstractWorker implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TopicConsumer0Worker.class);
	private Channel channel;
	private QueueingConsumer queueingConsumer;
	private String queueName;

	public void setUp() {
		try {
			channel = super.setUpChannel(CredentialSignature.ROUTE_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
		try {
			queueName = super.getMessageOperations().queueNameAndExchangeTopic(channel, EXCHANGE3_NAME, getPatterns().get(0));
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

	public void simpleConsumerTest() {
		String message = null;
		while (true) {
			try {
				message = super.getMessageOperations().read(queueingConsumer);
			} catch (MessageException me) {
				me.printStackTrace();
			}
			log.info("#0 topic consumed message " + message);
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		setUp();
		simpleConsumerTest();
	}

}
