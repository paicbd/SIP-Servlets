package org.mobicents.as7.deployment;

import org.apache.catalina.core.StandardContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.web.ext.WebContextFactory;
import org.jboss.logging.Logger;

/**
 * The Sip context factory.
 *
 * @author Emanuel Muckenhuber
 * @author josemrecio@gmail.com
 */
class SIPContextFactory implements WebContextFactory {

    Logger logger = Logger.getLogger(SIPContextFactory.class);

    @Override
    public StandardContext createContext(final DeploymentUnit deploymentUnit) throws DeploymentUnitProcessingException {
        logger.debug("create context for " + deploymentUnit.getName());
        // Create the SIP specific context
        return new SIPWebContext(deploymentUnit);
    }

    @Override
    public void postProcessContext(DeploymentUnit deploymentUnit, StandardContext webContext) {
        logger.debug("postProcessContext() for " + deploymentUnit.getName());
        if (webContext instanceof SIPWebContext) {
            ((SIPWebContext)webContext).postProcessContext(deploymentUnit);
        }
    }

}
