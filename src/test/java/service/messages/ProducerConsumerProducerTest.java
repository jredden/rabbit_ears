package service.messages;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.config.ConfigException;
import service.config.CredentialSignature;
import service.util.RandomName;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.Channel;

public class ProducerConsumerProducerTest extends AbstractBasicTest{
	private Channel channel;
	private static Logger log = LoggerFactory.getLogger(ProducerConsumerProducerTest.class);
	@Before
	public void setUp(){
		try {
			channel = super.setUpChannel(CredentialSignature.PRODUCER_CONSUMER_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
		
	}

	@Test
	public void producerConsumerProducerTest(){
		String name = "{name:"+RandomName.randomName()+"}";
		JsonParser jsonParser  = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(name).getAsJsonObject();
		
		log.info("sending "+jsonObject);
		try {
			super.getMessageOperations().postExchangeFanout(jsonObject, EXCHANGE_NAME, channel);
		} catch (MessageException me) {
			me.printStackTrace();
		}
	}
}
