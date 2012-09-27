package service.messages;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import service.config.ConfigException;
import service.config.CredentialSignature;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.Channel;

public class RouteProducerTest extends AbstractBasicTest{
	private Channel channel;

	@Before
	public void setUp(){
		try {
			channel = super.setUpChannel(CredentialSignature.ROUTE_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
	}

	@Test
	public void producerConsumerProducerTest() {
		for (String route : getRoutes()) {
			String name = "{name:" + RandomName.randomName() + "}";
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(name).getAsJsonObject();

			System.out.println("sending " + jsonObject);
			try {
				super.getMessageOperations().postExchangeDirect(jsonObject,
						EXCHANGE2_NAME, channel, route);
			} catch (MessageException me) {
				me.printStackTrace();
			}
		}
	}
}
