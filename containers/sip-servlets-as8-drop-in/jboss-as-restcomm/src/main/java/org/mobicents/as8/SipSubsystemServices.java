package org.mobicents.as8;

import org.jboss.msc.service.ServiceName;

/**
 * Service name constants.
 *
 * @author Brian Stansberry (c) 2011 Red Hat Inc.
 * @author Emanuel Muckenhuber
 *
 *         This class is based on the contents of org.mobicents.as7 package from jboss-as7-mobicents project, re-implemented for
 *         jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public final class SipSubsystemServices {

    /** The base name for jboss.sip services. */
    public static final ServiceName JBOSS_SIP = ServiceName.JBOSS.append("sip");
    /** The jboss.sip server name, there can only be one. */
    public static final ServiceName JBOSS_SIP_SERVER = JBOSS_SIP.append("server");
    /** The base name for jboss.sip connector services. */
    public static final ServiceName JBOSS_SIP_CONNECTOR = JBOSS_SIP.append("connector");
    /** The base name for jboss.sip deployments. */
    static final ServiceName JBOSS_SIP_DEPLOYMENT_BASE = JBOSS_SIP.append("deployment");

    public static ServiceName deploymentServiceName(final String appName) {
        return JBOSS_SIP_DEPLOYMENT_BASE.append("".equals(appName) ? "/" : appName);
    }

    private SipSubsystemServices() {
    }
}
