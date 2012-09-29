package service.worker;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.config.ConfigException;
import service.config.CredentialSignature;
import service.messages.MessageException;
import service.util.RandomName;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.Channel;

public class WorkProducerWorker extends AbstractWorker implements Runnable{
	private Channel channel;
	private static Logger log = LoggerFactory.getLogger(WorkProducerWorker.class);
	
	public void setUp(){
		try {
			channel = super.setUpChannel(CredentialSignature.WORK_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
		
	}

	
	public void workProducerTest(){
		String name = "{name:"+RandomName.randomName()+"}";
		JsonParser jsonParser  = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(name).getAsJsonObject();
		log.info("sending "+jsonObject);
		try {
			super.getMessageOperations().postPersistent(jsonObject, super.getMQConnectionCredentials(), channel);
		} catch (MessageException me) {
			me.printStackTrace();
		}
	}


	@Override
	public void run() {
		setUp();
		workProducerTest();
	}
}
