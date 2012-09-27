package service.config;

public class MQConnectionCredentials {
	
	private String username;
	private String password;
	private String host;
	private String virtualHost;
	private Integer port;
	private String queueName;
	/**
	 * 
	 * @return queue name as a String Object
	 */
	public String getQueueName() {
		return queueName;
	}
	/**
	 * 
	 * @return user name as a String Object.  Typically guest.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 
	 * @return queue users password as a String Object.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @return queue host, often "/"
	 */
	public String getHost() {
		return host;
	}
	/**
	 * 
	 * @return virtual host
	 */
	public String getVirtualHost() {
		return virtualHost;
	}
	/**
	 * 
	 * @return port number as an Integer Object.  Typically 5672.
	 */
	public Integer getPort() {
		return port;
	}
	/**
	 * 
	 * classic builder pattern implementation.  Allows the chaining of operations.
	 */
	public static class Builder {
		private String username;
		private String password;
		private String host;
		private String virtualHost;
		private String queueName;
		private Integer port;

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder queueName(String queueName) {
			this.queueName = queueName;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder host(String host) {
			this.host = host;
			return this;
		}

		public Builder virtualHost(String virtualHost) {
			this.virtualHost = virtualHost;
			return this;
		}

		public Builder port(Integer port) {
			this.port = port;
			return this;
		}

		public MQConnectionCredentials build() {
			return new MQConnectionCredentials(this);
		}
	}

	private MQConnectionCredentials(Builder builder) {
		this.username = builder.username;
		this.password = builder.password;
		this.host = builder.host;
		this.virtualHost = builder.virtualHost;
		this.port = builder.port;
		this.queueName = builder.queueName;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}


	@Override
	public String toString() {
		return "MQConnectionCredentials [username=" + username + ", password="
				+ password + ", host=" + host + ", virtualHost=" + virtualHost
				+ ", port=" + port + ", queueName=" + queueName + "]";
	}
}
