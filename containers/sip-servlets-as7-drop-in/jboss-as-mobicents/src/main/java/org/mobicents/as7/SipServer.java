package org.mobicents.as7;

import org.apache.catalina.Host;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.core.StandardService;
import org.jboss.as.server.deployment.AttachmentKey;

/**
 * The web server.
 *
 * @author Emanuel Muckenhuber
 * @author josemrecio@gmail.com
 */
public interface SipServer {

    AttachmentKey<SipServer> ATTACHMENT_KEY = AttachmentKey.create(SipServer.class);
    /**
     * Add a connector.
     *
     * @param connector the connector
     */
    void addConnector(Connector connector);

    /**
     * Remove connector.
     *
     * @param connector the connector
     */
    void removeConnector(Connector connector);

    /**
     * Add a virtual host.
     *
     * @param host the virtual host
     */
    void addHost(Host host);

    /**
     * Remove a virtual host.
     *
     * @param host the virtual host
     */
    void removeHost(Host host);

    /**
     * return the server (StandardServer)
     */
    StandardServer getServer();

    /**
     * return the service (StandardService)
     */
    StandardService getService();
    
    void connectorAdded(Connector connector);

}
