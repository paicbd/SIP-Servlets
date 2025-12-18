package org.mobicents.as8.deployment;

import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

/**
 * A service starting a sip deployment. For sip, this is required so far only as a way to provide the context to management
 * methods
 *
 * @author Emanuel Muckenhuber
 * @author josemrecio@gmail.com
 *
 *         This class is based on the contents of org.mobicents.as7.deployment package from jboss-as7-mobicents project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
class SipDeploymentService implements Service<SIPWebContext> {

    private SIPWebContext sipContext;
    private final DeploymentUnit anchorDu;

    public SipDeploymentService(final DeploymentUnit du) {
        this.anchorDu = SIPWebContext.getSipContextAnchorDu(du);
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void start(StartContext startContext) throws StartException {
        if (sipContext == null) {
            this.sipContext = anchorDu.getAttachment(SIPWebContext.ATTACHMENT_KEY);
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
    public synchronized SIPWebContext getValue() throws IllegalStateException {
        final SIPWebContext sipContext = this.sipContext;
        if (sipContext == null) {
            throw new IllegalStateException();
        }
        return sipContext;
    }

}
