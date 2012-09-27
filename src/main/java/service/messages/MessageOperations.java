package service.messages;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import service.config.MQConnectionCredentials;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class MessageOperations {
	private Connection connection;
	
	/**
	 * Adds a listener for messages 
	 * @param listener
	 */
	public List<MQMessageReceiver> addMessageListener(
			MQMessageReceiver listener, List<MQMessageReceiver> messageListeners) {
		messageListeners.add(listener);
		return messageListeners;
	}
	
	/**
	 * 
	 * @param mQConnectionCredentials
	 * @return rabbitMq Channel Object
	 * @throws MessageException
	 */
	public Channel createConnection(
			MQConnectionCredentials mQConnectionCredentials)
			throws MessageException {
		connection = null;
		ConnectionFactory factory = new ConnectionFactory();

		factory.setUsername(mQConnectionCredentials.getUsername());
		factory.setPassword(mQConnectionCredentials.getPassword());
		factory.setHost(mQConnectionCredentials.getHost());
		factory.setVirtualHost(mQConnectionCredentials.getVirtualHost());
		factory.setPort(mQConnectionCredentials.getPort());

		Channel channel;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			// declare queue
			channel.queueDeclare(mQConnectionCredentials.getQueueName(), true,
					false, false, null);

		} catch (IOException ioe) {
			throw new MessageException("Error setting up channel", ioe);
		}
		return channel;
	}
	
	/**
	 * 
	 * @param channel
	 * @param exchangeName
	 * @param exchangeType
	 * @return
	 * @throws MessageException
	 */
	private String queueNameAndExchange(Channel channel, String exchangeName, String exchangeType, String routeKey) throws MessageException{
		String queueName = null;
		try {
			channel.exchangeDeclare(exchangeName, exchangeType);
		} catch (IOException ioe) {
			throw new MessageException("Error setting up exchange", ioe);
		}
		try {
			queueName = channel.queueDeclare().getQueue();
		} catch (IOException ioe) {
			throw new MessageException("Error setting up exchange queue name", ioe);
		}
		try {
			channel.queueBind(queueName, exchangeName, routeKey);
		} catch (IOException ioe) {
			throw new MessageException("Error binding queue", ioe);
		}
		return queueName;
	}
	
	/**
	 * 
	 * @param channel
	 * @param exchangeName
	 * @return queuename
	 * @throws MessageException
	 */
	public String queueNameAndExchangeFanOut(Channel channel, String exchangeName) throws MessageException{
		return queueNameAndExchange(channel, exchangeName, "fanout", "");
	}

	/**
	 * 
	 * @param channel
	 * @param exchangeName
	 * @param routeKey
	 * @return queuename
	 * @throws MessageException
	 */
	public String queueNameAndExchangeRoute(Channel channel, String exchangeName, String routeKey) throws MessageException{
		return queueNameAndExchange(channel, exchangeName, "direct", routeKey);
	}

	/**
	 * 	
	 * @param channel
	 * @param exchangeName
	 * @param routeKey
	 * @return
	 * @throws MessageException
	 */
	public String queueNameAndExchangeTopic(Channel channel, String exchangeName, String routeKey) throws MessageException{
		return queueNameAndExchange(channel, exchangeName, "topic", routeKey);
	}
	
	/**
	 * 
	 * @param channel
	 * @param mQConnectionCredentials
	 * @param noAck
	 * @return
	 * @throws MessageException
	 */
	private QueueingConsumer createConsumer(Channel channel,
	MQConnectionCredentials mQConnectionCredentials, boolean noAck)
	throws MessageException {
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		try {
			channel.basicConsume(mQConnectionCredentials.getQueueName(), noAck,
					queueingConsumer);
		} catch (IOException ioe) {
			throw new MessageException(ioe);
		}
		return queueingConsumer;		
	}
	/**
	 * 
	 * @param channel
	 * @param mQConnectionCredentials
	 * @return  rabbitMq QueueingConsumer Object
	 * @throws MessageException
	 */
	public QueueingConsumer createConsumer(Channel channel,
			MQConnectionCredentials mQConnectionCredentials)
			throws MessageException {
		return createConsumer(channel, mQConnectionCredentials, true);
	}
	/**
	 * 
	 * @param channel
	 * @param mQConnectionCredentials
	 * @return  rabbitMq QueueingConsumer Object
	 * @throws MessageException
	 */
	public QueueingConsumer createConsumerAck(Channel channel,
			MQConnectionCredentials mQConnectionCredentials)
			throws MessageException {
		return createConsumer(channel, mQConnectionCredentials, false);
	}
	/**
	 * 
	 * Read operation on the queue
	 * 
	 * @param queueingConsumer
	 * @return String Object
	 * @throws MessageException
	 */
	public String read(QueueingConsumer queueingConsumer)
			throws MessageException {
		QueueingConsumer.Delivery delivery = null;
		try {
			delivery = queueingConsumer
					.nextDelivery();
		} catch (ShutdownSignalException sse) {
			throw new MessageException(sse);
		} catch (ConsumerCancelledException cce) {
			throw new MessageException(cce);
		} catch (InterruptedException ie) {
			throw new MessageException(ie);
		}
		byte[] b_message =  delivery.getBody();
		String message = new String(b_message);
		return message;
	}
	/**
	 * 
	 * @param queueingConsumer
	 * @param channel
	 * @return
	 * @throws MessageException
	 */
	public String readAndAcknowledge(QueueingConsumer queueingConsumer, Channel channel)
			throws MessageException {
		QueueingConsumer.Delivery delivery = null;
		try {
			delivery = queueingConsumer
					.nextDelivery();
		} catch (ShutdownSignalException sse) {
			throw new MessageException(sse);
		} catch (ConsumerCancelledException cce) {
			throw new MessageException(cce);
		} catch (InterruptedException ie) {
			throw new MessageException(ie);
		}
		byte[] b_message =  delivery.getBody();
		try {
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		} catch (IOException ioe) {
			throw new MessageException(ioe);
		}
		String message = new String(b_message);
		return message;
	}	/**
	 * 
	 * Writes a JSON Object to the queue using MQConnectionCredentials (see message_connection package)
	 * and a rabbitMq Channel Object.
	 * 
	 * @param jsonObject
	 * @param mQConnectionCredentials
	 * @param channel
	 * @throws MessageException
	 */
	public void post(JsonObject jsonObject, MQConnectionCredentials mQConnectionCredentials, Channel channel) throws MessageException{
		try {
			channel.basicPublish("", mQConnectionCredentials.getQueueName(), null, jsonObject.toString().getBytes());
		} catch (IOException ioe) {
			throw new MessageException(ioe);
		}
	}
	
	/**
	 * 
	 * @param jsonObject
	 * @param mQConnectionCredentials
	 * @param channel
	 * @throws MessageException
	 */
	public void postPersistent(JsonObject jsonObject,
			MQConnectionCredentials mQConnectionCredentials, Channel channel)
			throws MessageException {
		try {
			channel.basicPublish("", mQConnectionCredentials.getQueueName(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, jsonObject
							.toString().getBytes());
		} catch (IOException ioe) {
			throw new MessageException(ioe);
		}
	}
	
	/**
	 * 
	 * @param jsonObject
	 * @param exchangeName
	 * @param channel
	 * @param exchangeType
	 * @param routeKey
	 * @throws MessageException
	 */
	private void postExchange(JsonObject jsonObject, String exchangeName,
	Channel channel, String exchangeType, String  routeKey) throws MessageException {
		try {
			channel.exchangeDeclare(exchangeName, exchangeType);
		} catch (IOException ioe) {
			throw new MessageException(ioe);
		}
		try {
			channel.basicPublish(exchangeName, routeKey, null, jsonObject.toString()
					.getBytes());
		} catch (IOException ioe) {
			throw new MessageException(ioe);
		}
		
	}
	
	/**
	 * 
	 * @param jsonObject
	 * @param exchangeName
	 * @param channel
	 * @throws MessageException
	 */
	public void postExchangeFanout(JsonObject jsonObject, String exchangeName,
			Channel channel) throws MessageException {
		postExchange(jsonObject, exchangeName, channel, "fanout", "");
	}
	
	/**
	 * 
	 * @param jsonObject
	 * @param exchangeName
	 * @param channel
	 * @param routeKey
	 * @throws MessageException
	 */
	public void postExchangeDirect(JsonObject jsonObject, String exchangeName,
			Channel channel, String routeKey) throws MessageException{
		postExchange(jsonObject, exchangeName, channel, "direct", routeKey);
	}
	
	/**
	 * 
	 * @param jsonObject
	 * @param exchangeName
	 * @param channel
	 * @param routeKey
	 * @throws MessageException
	 */
	public void postExchangeRoute(JsonObject jsonObject, String exchangeName,
			Channel channel, String routeKey) throws MessageException{
		postExchange(jsonObject, exchangeName, channel, "topic", routeKey);
	}
	
	/**
	 * 
	 * @param channel
	 * @param delivery
	 * @throws MessageException
	 */
	public void acknowlegeMessage(Channel channel, QueueingConsumer.Delivery delivery) throws MessageException{
		try {
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		} catch (IOException ioe) {
			throw new MessageException(ioe);
		}
	}
	
	/**
	 * 
	 * @param delivery
	 * @return
	 */
	private String getRoutingKey(Delivery delivery){
		return delivery.getEnvelope().getRoutingKey();
	}
	
	/**
	 * 
	 * Effectively a close operation.
	 * 
	 * @param channel
	 * @throws MessageException
	 */
	public void disconnect(Channel channel) throws MessageException{
		try {
			channel.close();
		} catch (IOException ioe) {
			throw new MessageException(ioe);
		}
		try {
			connection.close();
		} catch (IOException ioe) {
			throw new MessageException(ioe);
		}
	}
}
