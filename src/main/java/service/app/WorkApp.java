package service.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.worker.WorkConsumerWorker;
import service.worker.WorkProducerWorker;

public class WorkApp implements IParameters{
	private static Logger log = LoggerFactory.getLogger(WorkApp.class);
	/**
	 * 
	 */
	private static void application(){
		Thread workProducerWorker = new Thread(new WorkProducerWorker());
		log.info("start producer");
		workProducerWorker.start();
		Thread workConsumerWorker = new Thread(new WorkConsumerWorker());
		log.info("start consumer");
		workConsumerWorker.start();
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
