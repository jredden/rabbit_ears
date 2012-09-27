package service.messages;


import org.junit.Before;
import org.junit.Test;

import service.config.ConfigException;
import service.config.CredentialSignature;
import service.config.Registry;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.Channel;

public class BasicProducerTest extends AbstractBasicTest{
	private Channel channel;

	@Before
	public void setUp(){
		try {
			channel = super.setUpChannel(CredentialSignature.SIMPLE_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
		
	}

	@Test
	public void simpleProducerTest(){
		String name = "{name:"+RandomName.randomName()+"}";
		JsonParser jsonParser  = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(name).getAsJsonObject();
		System.out.println("sending "+jsonObject);
		try {
			super.getMessageOperations().post(jsonObject, super.getMQConnectionCredentials(), channel);
		} catch (MessageException me) {
			me.printStackTrace();
		}
	}
}
