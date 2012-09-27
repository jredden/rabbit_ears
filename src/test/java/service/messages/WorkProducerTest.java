package service.messages;


import org.junit.Before;
import org.junit.Test;

import service.config.ConfigException;
import service.config.CredentialSignature;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.Channel;

public class WorkProducerTest extends AbstractBasicTest{
	private Channel channel;

	@Before
	public void setUp(){
		try {
			channel = super.setUpChannel(CredentialSignature.WORK_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
		
	}

	@Test
	public void workProducerTest(){
		String name = "{name:"+RandomName.randomName()+"}";
		JsonParser jsonParser  = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(name).getAsJsonObject();
		System.out.println("sending "+jsonObject);
		try {
			super.getMessageOperations().postPersistent(jsonObject, super.getMQConnectionCredentials(), channel);
		} catch (MessageException me) {
			me.printStackTrace();
		}
	}
}
