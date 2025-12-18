package org.mobicents.servlet.sip.core.message;

/**
 * @author jean.deruelle@gmail.com
 *
 */
public class OutboundProxy {

	private String host = null;
	private int port = 5060;
	
	public OutboundProxy(String outboundProxy) {
		int separatorIndex = outboundProxy.indexOf(":");
		if(separatorIndex > 0) {
			host = outboundProxy.substring(0, separatorIndex);
			port = Integer.parseInt(outboundProxy.substring(separatorIndex + 1));
		} else {
			host = outboundProxy;
		}
	}
	
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String toString() {		
		return host + ":" + port;
	}
}
