package org.mobicents.as10;

import org.jboss.as.server.deployment.AttachmentKey;
import org.mobicents.servlet.sip.undertow.SipStandardService;

/**
 * The web server.
 *
 * @author Emanuel Muckenhuber
 * @author josemrecio@gmail.com
 *
 *         This class is based on the contents of org.mobicents.as7 package from jboss-as7-mobicents project, re-implemented for
 *         jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public interface SipServer {

    AttachmentKey<SipServer> ATTACHMENT_KEY = AttachmentKey.create(SipServer.class);

    /**
     * Add a connector.
     *
     * @param connector the connector
     */
    void addConnector(SipConnectorListener connector);

    /**
     * Remove connector.
     *
     * @param connector the connector
     */
    void removeConnector(SipConnectorListener connector);

    /**
     * Add a virtual host.
     *
     * @param host the virtual host
     */
    // FIXME: void addHost(Host host);

    /**
     * Remove a virtual host.
     *
     * @param host the virtual host
     */
    // FIXME: void removeHost(Host host);

    /**
     * return the server (StandardServer)
     */
    // FIXME: Server getServer();

    /**
     * return the service (StandardService)
     */
    SipStandardService getService();
    
    void connectorAdded(SipConnectorListener connector);    

}
