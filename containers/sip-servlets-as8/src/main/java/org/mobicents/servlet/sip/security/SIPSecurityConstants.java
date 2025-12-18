package org.mobicents.servlet.sip.security;

import org.jboss.security.SecurityConstants;

/**
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on org.mobicents.servlet.sip.security.SIPSecurityConstants class from sip-servlet-as7 project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public interface SIPSecurityConstants extends SecurityConstants {
    java.lang.String DEFAULT_SIP_APPLICATION_POLICY = "sip-servlets";
}
