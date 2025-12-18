package org.mobicents.metadata.sip.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author jean.deruelle@gmail.com
 * @version $Revision$
 */
public class ProxyConfigMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;
    private int proxyTimeout;

    public int getProxyTimeout() {
        return proxyTimeout;
    }

    public void setProxyTimeout(int proxyTimeout) {
        this.proxyTimeout = proxyTimeout;
    }
}
