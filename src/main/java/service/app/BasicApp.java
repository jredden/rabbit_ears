package service.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.worker.BasicConsumerWorker;
import service.worker.BasicProducerWorker;

public class BasicApp {
	private static Logger log = LoggerFactory.getLogger(BasicApp.class);
	/**
	 * 
	 */
	private static void application(){
		Thread basicProducerWorker = new Thread(new BasicProducerWorker());
		log.info("start producer");
		basicProducerWorker.start();
		Thread basicConsumerWorker = new Thread(new BasicConsumerWorker());
		log.info("start consumer");
		basicConsumerWorker.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		application();

	}

}
