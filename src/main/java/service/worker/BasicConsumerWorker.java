package service.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.config.ConfigException;
import service.config.CredentialSignature;
import service.messages.MessageException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class BasicConsumerWorker extends AbstractWorker implements Runnable {

	private Channel channel;
	private QueueingConsumer queueingConsumer;
	private static Logger log = LoggerFactory.getLogger(BasicConsumerWorker.class);


	private void setUp() {
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

	public void simpleConsumerTest() {
		String message = null;
		try {
			message = super.getMessageOperations().read(queueingConsumer);
		} catch (MessageException me) {
			me.printStackTrace();
		}
		log.info("consumed message "+message);
	}

	@Override
	public void run() {
		setUp();
		simpleConsumerTest();
		
	}

}
