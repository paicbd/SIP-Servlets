package org.mobicents.servlet.sip.undertow;

import io.undertow.servlet.api.SecurityConstraint;

/**
 * This class is based on org.mobicents.servlet.sip.catalina.SipSecurityConstraint class from sip-servlet-as7 project,
 * re-implemented for jboss as10 (wildfly) by:
 *
 * @author kakonyi.istvan@alerant.hu
 */
public class SipSecurityConstraint extends SecurityConstraint {
    private static final long serialVersionUID = 1L;
    public boolean proxyAuthentication;

    private String displayName;

    /**
     * @return the proxyAuthentication
     */
    public boolean isProxyAuthentication() {
        return proxyAuthentication;
    }

    /**
     * @param proxyAuthentication the proxyAuthentication to set
     */
    public void setProxyAuthentication(boolean proxyAuthentication) {
        this.proxyAuthentication = proxyAuthentication;
    }

    public void addCollection(SipSecurityCollection sipSecurityCollection) {
        super.addWebResourceCollection(sipSecurityCollection);
    }

    public void removeCollection(SipSecurityCollection sipSecurityCollection) {
        super.getWebResourceCollections().remove(sipSecurityCollection);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
