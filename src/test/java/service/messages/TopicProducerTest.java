package service.messages;


import org.junit.Before;
import org.junit.Test;

import service.config.ConfigException;
import service.config.CredentialSignature;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.Channel;

public class TopicProducerTest extends AbstractBasicTest{
	private Channel channel;

	@Before
	public void setUp(){
		try {
			channel = super.setUpChannel(CredentialSignature.TOPIC_PROPERTIES);
		} catch (ConfigException ce) {
			ce.printStackTrace();
		} catch (MessageException me) {
			me.printStackTrace();
		}
	}

	@Test
	public void producerConsumerProducerTest() {
		for (String route : getDetailedRoutes()) {
			String name = "{name:" + RandomName.randomName() + "}";
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(name).getAsJsonObject();

			System.out.println("sending " + jsonObject + " on route " + route);
			try {
				super.getMessageOperations().postExchangeRoute(jsonObject,
						EXCHANGE3_NAME, channel, route);
			} catch (MessageException me) {
				me.printStackTrace();
			}
		}
	}
}
