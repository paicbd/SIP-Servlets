package org.mobicents.as8.deployment;

import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.logging.Logger;
import org.mobicents.metadata.sip.spec.SipAnnotationMetaData;
import org.mobicents.metadata.sip.spec.SipMetaData;

/**
 * The SIP contextFactory deployment process will attach a {@code WebContextFactory} to the {@code DeploymentUnit} in case it
 * finds a SIP deployment.
 *
 * @author Emanuel Muckenhuber
 * @author josemrecio@gmail.com
 *
 *         This class is based on the contents of org.mobicents.as7.deployment package from jboss-as7-mobicents project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class SipContextFactoryDeploymentProcessor implements DeploymentUnitProcessor {

    private static final Logger logger = Logger.getLogger(SipContextFactoryDeploymentProcessor.class);
    public static final DeploymentUnitProcessor INSTANCE = new SipContextFactoryDeploymentProcessor();

    @Override
    public void deploy(final DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {

        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        // Check if the deployment contains a sip metadata
        SipMetaData sipMetaData = deploymentUnit.getAttachment(SipMetaData.ATTACHMENT_KEY);
        if (sipMetaData == null) {
            if (logger.isDebugEnabled())
                logger.debug(deploymentUnit.getName() + " has null sipMetaData, checking annotations");
            SipAnnotationMetaData sipAnnotationMetaData = deploymentUnit.getAttachment(SipAnnotationMetaData.ATTACHMENT_KEY);
            if (sipAnnotationMetaData == null || !sipAnnotationMetaData.isSipApplicationAnnotationPresent()) {
                // http://code.google.com/p/sipservlets/issues/detail?id=168
                // When no sip.xml but annotations only, Application is not recognized as SIP App by AS7

                // In case there is no metadata attached, it means this is not a sip deployment, so we can safely ignore it!
                if (logger.isDebugEnabled())
                    logger.debug(deploymentUnit.getName()
                            + " has null sipMetaData and no SipApplication annotation, no Sip context factory installed");
                return;
            }
        }

        if (logger.isDebugEnabled())
            logger.debug(deploymentUnit.getName() + " sip context factory installed");
        // Just attach the context factory, the web subsystem will pick it up
        final SIPContextFactory contextFactory = new SIPContextFactory();
        deploymentUnit.putAttachment(SIPContextFactory.ATTACHMENT, contextFactory);
    }

    @Override
    public void undeploy(DeploymentUnit context) {
        //
    }
}
