

package org.mobicents.servlet.sip.testsuite.proxy;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import javax.sip.ListeningPoint;
import javax.sip.SipProvider;
import javax.sip.address.SipURI;
import javax.sip.header.Header;
import javax.sip.header.RecordRouteHeader;

import org.apache.log4j.Logger;
import org.mobicents.servlet.sip.NetworkPortAssigner;
import org.mobicents.servlet.sip.SipServletTestCase;
import org.mobicents.servlet.sip.testsuite.ProtocolObjects;
import org.mobicents.servlet.sip.testsuite.TestSipListener;

public class ProxyRecordRoutingTcpTest extends SipServletTestCase {

	private static transient Logger logger = Logger.getLogger(ProxyRecordRoutingTcpTest.class);

	private static final String SESSION_EXPIRED = "sessionExpired";
	private static final String SESSION_READY_TO_INVALIDATE = "sessionReadyToInvalidate";
	private static final String SIP_SESSION_READY_TO_INVALIDATE = "sipSessionReadyToInvalidate";
	private static final boolean AUTODIALOG = true;
	TestSipListener sender;
	TestSipListener neutral;
	TestSipListener receiver;
	ProtocolObjects senderProtocolObjects;
	ProtocolObjects	receiverProtocolObjects;
	ProtocolObjects neutralProto;


	private static final int TIMEOUT = 20000;

	public ProxyRecordRoutingTcpTest(String name) {
		super(name);
		autoDeployOnStartup = false;
		listeningPointTransport = ListeningPoint.TCP;
	}

	@Override
	public void setUp() throws Exception {
                containerPort = NetworkPortAssigner.retrieveNextPort(); 
		super.setUp();
        }
        public void setupPhones() throws Exception {
		senderProtocolObjects = new ProtocolObjects("proxy-sender",
				"gov.nist", ListeningPoint.TCP, AUTODIALOG, null, null, null);
		receiverProtocolObjects = new ProtocolObjects("proxy-receiver",
				"gov.nist", ListeningPoint.TCP, AUTODIALOG, null, null, null);
		neutralProto = new ProtocolObjects("neutral",
				"gov.nist", ListeningPoint.TCP, AUTODIALOG, null, null, null);
                
                int senderPort = NetworkPortAssigner.retrieveNextPort(); 
		sender = new TestSipListener(senderPort, containerPort, senderProtocolObjects, false);
		sender.setRecordRoutingProxyTesting(true);
		SipProvider senderProvider = sender.createProvider();

                int receiverPort = NetworkPortAssigner.retrieveNextPort(); 
		receiver = new TestSipListener(receiverPort, containerPort, receiverProtocolObjects, false);
		receiver.setRecordRoutingProxyTesting(true);
		SipProvider receiverProvider = receiver.createProvider();
		
                int neutralPort = NetworkPortAssigner.retrieveNextPort(); 
		neutral = new TestSipListener(neutralPort, containerPort, neutralProto, false);
		neutral.setRecordRoutingProxyTesting(true);
		SipProvider neutralProvider = neutral.createProvider();

		receiverProvider.addSipListener(receiver);
		senderProvider.addSipListener(sender);
		neutralProvider.addSipListener(neutral);

		senderProtocolObjects.start();
		receiverProtocolObjects.start();
		neutralProto.start();
                
                Map<String,String> params = new HashMap();
                params.put( "servletContainerPort", String.valueOf(containerPort)); 
                params.put( "testPort", String.valueOf(senderPort)); 
                params.put( "receiverPort", String.valueOf(receiverPort));
                params.put( "neutralPort", String.valueOf(neutralPort));                
                deployApplication(projectHome + 
                        "/sip-servlets-test-suite/applications/proxy-sip-servlet/src/main/sipapp", 
                        params, null);                  
	}

	/*
	 * https://code.google.com/p/sipservlets/issues/detail?id=20
	 */
	public void testTCPRecordRouteProxying() throws Exception {
		setupPhones();
		String fromName = "tcp-record-route-tcp-unique-location";
		String fromSipAddress = "sip-servlets.com";
		SipURI fromAddress = senderProtocolObjects.addressFactory.createSipURI(
				fromName, fromSipAddress);		
		
		String toSipAddress = "sip-servlets.com";
		String toUser = "proxy-receiver";
		SipURI toAddress = senderProtocolObjects.addressFactory.createSipURI(
				toUser, toSipAddress);
								
		sender.sendSipRequest("INVITE", fromAddress, toAddress, null, null, false);
		Thread.sleep(TIMEOUT);
		assertEquals(200,sender.getFinalResponseStatus());		
		assertTrue(sender.isAckSent());
		ListIterator<Header> it = receiver.getInviteRequest().getHeaders(RecordRouteHeader.NAME);
		int countRRHeader = 0;
		while(it.hasNext()) {
			countRRHeader++;
			it.next();
		}
		assertEquals(1, countRRHeader);
		sender.sendBye();
		Thread.sleep(TIMEOUT);
		assertTrue(receiver.getByeReceived());
		assertTrue(sender.getOkToByeReceived());		
	}
	
	@Override
	public void tearDown() throws Exception {
		senderProtocolObjects.destroy();
		receiverProtocolObjects.destroy();		
		neutralProto.destroy();
		logger.info("Test completed");
		super.tearDown();
	}

	@Override
	public void deployApplication() {
		assertTrue(tomcat
				.deployContext(
						projectHome
								+ "/sip-servlets-test-suite/applications/proxy-sip-servlet/src/main/sipapp",
						"sip-test-context", "/sip-test"));
	}

	@Override
	protected String getDarConfigurationFile() {
		return "file:///"
				+ projectHome
				+ "/sip-servlets-test-suite/testsuite/src/test/resources/"
				+ "org/mobicents/servlet/sip/testsuite/proxy/simple-sip-servlet-dar.properties";
	}
}
