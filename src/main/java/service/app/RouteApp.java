package service.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.worker.RouteConsumer0Worker;
import service.worker.RouteConsumer1Worker;
import service.worker.RouteConsumer2Worker;
import service.worker.RouteProducerWorker;

public class RouteApp implements IParameters{
	private static Logger log = LoggerFactory.getLogger(RouteApp.class);
	/**
	 * 
	 */
	private static void application(){
		Thread routeProducerWorker= new Thread(new RouteProducerWorker());
		log.info("start producer");
		routeProducerWorker.start();
		Thread routeConsumer0Worker = new Thread(new RouteConsumer0Worker());
		log.info("start consumer 0");
		routeConsumer0Worker.start();
		Thread routeConsumer1Worker = new Thread(new RouteConsumer1Worker());
		log.info("start consumer 1");
		routeConsumer1Worker.start();
		Thread routeConsumer2Worker = new Thread(new RouteConsumer2Worker());
		log.info("start consumer 2");
		routeConsumer2Worker.start();
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
