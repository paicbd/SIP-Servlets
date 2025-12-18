package org.mobicents.servlet.sip;

import gov.nist.javax.sip.header.HeaderFactoryImpl;

import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;

import org.apache.log4j.Logger;

public class SipFactories {
	private final static Logger logger = Logger.getLogger(SipFactories.class.getCanonicalName());

	private static boolean initialized;

	public static AddressFactory addressFactory;

	public static HeaderFactory headerFactory;

	public static SipFactory sipFactory;

	public static MessageFactory messageFactory;

	public static void initialize(String pathName, boolean prettyEncoding) {
		if (!initialized) {
			try {
				System.setProperty("gov.nist.core.STRIP_ADDR_SCOPES", "true");
				sipFactory = SipFactory.getInstance();
				sipFactory.setPathName(pathName);
				addressFactory = sipFactory.createAddressFactory();				
				headerFactory = sipFactory.createHeaderFactory();
				if(prettyEncoding) {
					((HeaderFactoryImpl)headerFactory).setPrettyEncoding(prettyEncoding);
				}
				messageFactory = sipFactory.createMessageFactory();
				initialized = true;
			} catch (PeerUnavailableException ex) {
				logger.error("Could not instantiate factories -- exitting", ex);
				throw new IllegalArgumentException("Cannot instantiate factories ", ex);
			}
		}
	}
}
