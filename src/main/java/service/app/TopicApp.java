package service.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.worker.TopicConsumer0Worker;
import service.worker.TopicConsumer1Worker;
import service.worker.TopicConsumer2Worker;
import service.worker.TopicConsumer3Worker;
import service.worker.TopicProducerWorker;

public class TopicApp implements IParameters{
	private static Logger log = LoggerFactory.getLogger(TopicApp.class);
	/**
	 * 
	 */
	private static void application(){
		Thread topicProducerWorker= new Thread(new TopicProducerWorker());
		log.info("start producer");
		topicProducerWorker.start();
		Thread topicConsumer0Worker = new Thread(new TopicConsumer0Worker());
		log.info("start consumer 0");
		topicConsumer0Worker.start();
		Thread topicConsumer1Worker = new Thread(new TopicConsumer1Worker());
		log.info("start consumer 1");
		topicConsumer1Worker.start();
		Thread topicConsumer2Worker = new Thread(new TopicConsumer2Worker());
		log.info("start consumer 2");
		topicConsumer2Worker.start();
		Thread topicConsumer3Worker = new Thread(new TopicConsumer3Worker());
		log.info("start consumer 3");
		topicConsumer3Worker.start();
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
