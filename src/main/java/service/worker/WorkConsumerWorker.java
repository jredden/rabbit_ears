package service.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.config.ConfigException;
import service.config.CredentialSignature;
import service.messages.MessageException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class WorkConsumerWorker extends AbstractWorker implements Runnable {
	private static Logger log = LoggerFactory.getLogger(WorkConsumerWorker.class);
	private Channel channel;
	private QueueingConsumer queueingConsumer;

	
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


	@Override
	public void run() {
		setUp();
		simpleConsumerTest();
	}

}
