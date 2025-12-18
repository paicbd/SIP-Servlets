package org.mobicents.as8.deployment;

import javax.servlet.ServletException;

import org.jboss.as.server.deployment.AttachmentKey;
import org.jboss.as.server.deployment.DeploymentUnit;
//import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.logging.Logger;
import org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService;

/**
 * The Sip context factory.
 *
 * @author Emanuel Muckenhuber
 * @author josemrecio@gmail.com
 *
 *         This class is based on the contents of org.mobicents.as7.deployment package from jboss-as7-mobicents project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
class SIPContextFactory {
    static AttachmentKey<SIPContextFactory> ATTACHMENT = AttachmentKey.create(SIPContextFactory.class);
    Logger logger = Logger.getLogger(SIPContextFactory.class);

    public SIPWebContext addDeplyomentUnitToContext(final DeploymentUnit deploymentUnit,
            UndertowDeploymentInfoService deploymentInfoservice, SIPWebContext context) throws ServletException {
        logger.debug("create context for " + deploymentUnit.getName());
        // Create the SIP specific context
        return context.addDeploymentUnit(deploymentUnit).createContextConfig(deploymentInfoservice).attachContext();
    }

    public void postProcessContext(DeploymentUnit deploymentUnit, SIPWebContext webContext) {
        logger.debug("postProcessContext() for " + deploymentUnit.getName());
        webContext.postProcessContext(deploymentUnit);
    }
}
