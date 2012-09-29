package service.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.worker.ProducerConsumerConsumerWorker;
import service.worker.ProducerConsumerProducerWorker;

public class ProducerConsumerApp implements IParameters{
	private static Logger log = LoggerFactory.getLogger(ProducerConsumerApp.class);
	/**
	 * 
	 */
	private static void application(){
		Thread basicProducerWorker = new Thread(new ProducerConsumerProducerWorker());
		log.info("start producer");
		basicProducerWorker.start();
		Thread basicConsumerWorker = new Thread(new ProducerConsumerConsumerWorker());
		log.info("start consumer");
		basicConsumerWorker.start();
		try {
			Thread.sleep(WAIT_TIME.intValue());
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		application();
		System.exit(0);  // exit all threads
	}

}
