package org.mobicents.as10.deployment;

import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.web.common.WarMetaData;
import org.jboss.as.web.common.WebComponentDescription;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.mobicents.as10.ConvergedServletContainerService;
import org.mobicents.metadata.sip.spec.SipAnnotationMetaData;
import org.mobicents.metadata.sip.spec.SipMetaData;
import org.wildfly.extension.undertow.Server;
import org.wildfly.extension.undertow.UndertowService;
import org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService;

/**
 * @author kakonyi.istvan@alerant.hu
 */

// sip-build-run Check this class, could be a clue here
public class UndertowSipDeploymentInfoProcessor implements DeploymentUnitProcessor {

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {

        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        SipMetaData sipMetaData = deploymentUnit.getAttachment(SipMetaData.ATTACHMENT_KEY);
        if (sipMetaData == null) {
            SipAnnotationMetaData sipAnnotationMetaData = deploymentUnit.getAttachment(SipAnnotationMetaData.ATTACHMENT_KEY);

            if (sipAnnotationMetaData == null || !sipAnnotationMetaData.isSipApplicationAnnotationPresent()) {
                return;
            }
        }

        final WarMetaData warMetaData = deploymentUnit.getAttachment(WarMetaData.ATTACHMENT_KEY);
        final JBossWebMetaData metaData = warMetaData.getMergedJBossWebMetaData();

        final ServiceName defaultServerServiceName = UndertowService.DEFAULT_SERVER;
        final ServiceController<?> defaultServerServiceController = phaseContext.getServiceRegistry().getService(
                defaultServerServiceName);
        final Server defaultServerService = (Server) defaultServerServiceController.getValue();

        final String defaultHost = defaultServerService.getDefaultHost();
        final String defaultServer = defaultServerService.getName();

        final String hostName = UndertowSipDeploymentProcessor.hostNameOfDeployment(warMetaData, defaultHost);
        final String pathName = UndertowSipDeploymentProcessor.pathNameOfDeployment(deploymentUnit, metaData);

        final String serverInstanceName = metaData.getServerInstanceName() == null ? defaultServer : metaData
                .getServerInstanceName();

        //lets find server service:

        final ServiceName serverName = ServiceName.of("org", "wildfly", "undertow", "server", "default-server");
        ServiceController<?> serverServiceController = phaseContext.getServiceRegistry().getService(serverName);

        Server serverService = (Server) serverServiceController.getValue();

        final ServiceName deploymentServiceName = UndertowService.deploymentServiceName(serverInstanceName, hostName, pathName);
        final ServiceName deploymentInfoServiceName = deploymentServiceName.append(UndertowDeploymentInfoService.SERVICE_NAME);

        // instantiate injector service
        final UndertowSipDeploymentInfoService sipDeploymentInfoService = new UndertowSipDeploymentInfoService(deploymentUnit, serverService);

        final ServiceName sipDeploymentInfoServiceName = deploymentServiceName
                .append(UndertowSipDeploymentInfoService.SERVICE_NAME);

        // lets earn that deploymentService will depend on this service:
        phaseContext.getDeploymentUnit().getAttachment(WebComponentDescription.WEB_COMPONENTS)
                .add(sipDeploymentInfoServiceName);

        // make injector service to be dependent from deploymentInfoService:
        final ServiceBuilder<UndertowSipDeploymentInfoService> infoInjectorBuilder = phaseContext.getServiceTarget()
                .addService(sipDeploymentInfoServiceName, sipDeploymentInfoService);
        infoInjectorBuilder.addDependency(deploymentInfoServiceName);

        infoInjectorBuilder.addDependency(ConvergedServletContainerService.SERVICE_NAME);

        infoInjectorBuilder.install();

    }

    @Override
    public void undeploy(DeploymentUnit context) {
    }

}
