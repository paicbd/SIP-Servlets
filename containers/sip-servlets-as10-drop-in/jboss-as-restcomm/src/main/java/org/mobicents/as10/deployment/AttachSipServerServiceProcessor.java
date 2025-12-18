package org.mobicents.as10.deployment;

import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.mobicents.as10.SipServer;

/**
 * Attaches SipServer to the deploymentUnit so can be recovered afterwards
 *
 * @author josemrecio@gmail.com
 *
 *         This class is based on the contents of org.mobicents.as7.deployment package from jboss-as7-mobicents project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class AttachSipServerServiceProcessor implements DeploymentUnitProcessor {

    SipServer server;

    public AttachSipServerServiceProcessor(SipServer s) {
        server = s;
    }

    @Override
    public void deploy(final DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        deploymentUnit.putAttachment(SipServer.ATTACHMENT_KEY, server);
    }

    @Override
    public void undeploy(DeploymentUnit context) {
        //
    }
}
