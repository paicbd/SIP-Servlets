package org.mobicents.as7.deployment;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

/**
 * A service starting a sip deployment.
 * For sip, this is required so far only as a way to provide the context to management methods 
 *
 * @author Emanuel Muckenhuber
 * @author josemrecio@gmail.com
 */
class SipDeploymentService implements Service<Context> {

    private StandardContext sipContext;
    private final DeploymentUnit anchorDu;

    public SipDeploymentService(final DeploymentUnit du) {
    	this.anchorDu =SIPWebContext.getSipContextAnchorDu(du);
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void start(StartContext startContext) throws StartException {
        if (sipContext == null) {
        	this.sipContext = anchorDu.getAttachment(SIPWebContext.ATTACHMENT);
        }
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void stop(StopContext stopContext) {
    }

    /**
     * {@inheritDoc}
     */
    public synchronized Context getValue() throws IllegalStateException {
        final Context sipContext = this.sipContext;
        if (sipContext == null) {
            throw new IllegalStateException();
        }
        return sipContext;
    }

}
