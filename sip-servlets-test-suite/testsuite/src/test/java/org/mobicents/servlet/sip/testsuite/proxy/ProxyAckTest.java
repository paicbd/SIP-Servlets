package org.mobicents.servlet.sip.testsuite.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sip.ListeningPoint;
import javax.sip.SipProvider;
import javax.sip.address.SipURI;
import javax.sip.message.Response;

import org.apache.log4j.Logger;
import org.mobicents.servlet.sip.NetworkPortAssigner;
import org.mobicents.servlet.sip.SipServletTestCase;
import org.mobicents.servlet.sip.testsuite.ProtocolObjects;
import org.mobicents.servlet.sip.testsuite.TestSipListener;

public class ProxyAckTest extends SipServletTestCase {

    private static transient Logger logger = Logger.getLogger(ProxyAckTest.class);
    private static final boolean AUTODIALOG = true;
    TestSipListener sender;
    TestSipListener neutral;
    TestSipListener receiver;
    ProtocolObjects senderProtocolObjects;
    ProtocolObjects receiverProtocolObjects;
    ProtocolObjects neutralProto;

    private static final int TIMEOUT = 20000;

    public ProxyAckTest(String name) {
        super(name);
        autoDeployOnStartup = false;
    }

    @Override
    public void setUp() throws Exception {
        containerPort = NetworkPortAssigner.retrieveNextPort();
        super.setUp();
    }

    public void setupPhones() throws Exception {
        senderProtocolObjects = new ProtocolObjects("proxy-sender",
                "gov.nist", ListeningPoint.UDP, AUTODIALOG, null, null, null);
        receiverProtocolObjects = new ProtocolObjects("proxy-receiver",
                "gov.nist", ListeningPoint.UDP, AUTODIALOG, null, null, null);
//		SIP Stack by default doesn't pass the ACK to the Listener for non 2XX responses to INVITE. 
//		So, passing additional properties for SIP stack.
        Map<String, String> additionalProperties = new HashMap<String, String>();
        additionalProperties.put("gov.nist.javax.sip.PASS_INVITE_NON_2XX_ACK_TO_LISTENER", "true");
        neutralProto = new ProtocolObjects("neutral",
                "gov.nist", ListeningPoint.UDP, AUTODIALOG, null, null, null, additionalProperties);

        int senderPort = NetworkPortAssigner.retrieveNextPort();
        sender = new TestSipListener(senderPort, containerPort, senderProtocolObjects, false);
        sender.setRecordRoutingProxyTesting(true);
        SipProvider senderProvider = sender.createProvider();

        int receiverPort = NetworkPortAssigner.retrieveNextPort();
        receiver = new TestSipListener(receiverPort, containerPort, receiverProtocolObjects, false);
        receiver.setRecordRoutingProxyTesting(true);
        SipProvider receiverProvider = receiver.createProvider();

        //use cutme port 5056 to receive second invite in second branch
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

        Map<String, String> params = new HashMap();
        params.put("servletContainerPort", String.valueOf(containerPort));
        params.put("testPort", String.valueOf(senderPort));
        params.put("receiverPort", String.valueOf(receiverPort));
        params.put("cutme", String.valueOf(neutralPort));
        deployApplication(projectHome
                + "/sip-servlets-test-suite/applications/proxy-sip-servlet/src/main/sipapp",
                params, null);
    }

    /**
     * test for https://github.com/RestComm/sip-servlets/issues/78
     */
    public void testAckSentToForkedResponses() throws Exception {
        setupPhones();
        String fromName = "sequential";
        String fromSipAddress = "sip-servlets.com";
        SipURI fromAddress = senderProtocolObjects.addressFactory.createSipURI(
                fromName, fromSipAddress);

        String toSipAddress = "sip-servlets.com";
        String toUser = "proxy-receiver";
        SipURI toAddress = senderProtocolObjects.addressFactory.createSipURI(
                toUser, toSipAddress);

        List<Integer> respList = new ArrayList<Integer>();
        respList.add(180);
        receiver.setProvisionalResponsesToSend(respList);
        neutral.setProvisionalResponsesToSend(respList);

        receiver.setFinalResponseToSend(Response.OK);
        neutral.setFinalResponseToSend(Response.BUSY_HERE);
        sender.sendSipRequest("INVITE", fromAddress, toAddress, null, null, false);
        Thread.sleep(TIMEOUT);
        assertTrue(receiver.isAckReceived());
        assertTrue(neutral.isAckReceived());
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
                        "sip-test-context", "sip-test"));
    }

    @Override
    protected String getDarConfigurationFile() {
        return "file:///"
                + projectHome
                + "/sip-servlets-test-suite/testsuite/src/test/resources/"
                + "org/mobicents/servlet/sip/testsuite/proxy/simple-sip-servlet-dar.properties";
    }
}
