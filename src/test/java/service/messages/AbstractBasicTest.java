package service.messages;


import java.util.ArrayList;
import java.util.List;

import service.config.ConfigException;
import service.config.MQConnectionCredentials;
import service.config.Registry;

import com.rabbitmq.client.Channel;

public abstract class AbstractBasicTest {
	
	public static String EXCHANGE_NAME = "exchange";
	public static String EXCHANGE2_NAME = "exchange2";
	public static String EXCHANGE3_NAME = "exchange3";
	
	private static List<String> routes = new ArrayList<String>();
	static {
		routes.add("route0");
		routes.add("route1");
		routes.add("route2");
		routes.add("route2");
	}
	private static List<String> routes2 = new ArrayList<String>();
	static {
		routes2.add("route0.zero");
		routes2.add("route1.one");
		routes2.add("route1.won");
		routes2.add("route2.two");
		routes2.add("route2.too");
	}
	private static List<String> patterns = new ArrayList<String>();
	static{
		patterns.add("route.zero");
		patterns.add("route1.*");
		patterns.add("route2.*");
		patterns.add("route2#");
	}
	
	private MessageOperations messageOperations;
	private MQConnectionCredentials mQConnectionCredentials;
	
	/**
	 * 
	 * @param properties
	 * @return
	 * @throws ConfigException
	 * @throws MessageException
	 */
	public Channel setUpChannel(String properties) throws ConfigException, MessageException{
		Registry registy = new Registry();
		mQConnectionCredentials = registy.buildCredentials(properties);
		messageOperations = new MessageOperations();
		Channel channel = messageOperations.createConnection(mQConnectionCredentials);
		return channel;
	}

	/**
	 * 
	 * @return message operation instance
	 */
	public MessageOperations getMessageOperations(){
		return messageOperations;
	}
	
	/**
	 * 
	 * @return Q connection instance
	 */
	public MQConnectionCredentials getMQConnectionCredentials(){
		return mQConnectionCredentials;
	}
	
	/**
	 * 
	 * @return test route list
	 */
	public static List<String> getRoutes(){return routes;}
	
	/**
	 * 
	 * @return test route detail list
	 */
	public static List<String> getDetailedRoutes(){return routes2;}
	
	/**
	 * 
	 * @return topic pattern test list
	 */
	public static List<String> getPatterns(){return patterns;}
}
