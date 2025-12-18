package org.mobicents.as10.deployment;

import io.undertow.servlet.api.Deployment;

import javax.servlet.ServletException;


import org.jboss.as.server.logging.ServerLogger;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.modules.Module;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.wildfly.extension.undertow.deployment.UndertowDeploymentService;

/**
 * @author kakonyi.istvan@alerant.hu
 */
public class UndertowSipDeploymentService implements Service<UndertowSipDeploymentService> {
    public static final ServiceName SERVICE_NAME = ServiceName.of("UndertowSipDeploymentService");

    private UndertowDeploymentService deploymentService = null;
    private DeploymentUnit deploymentUnit = null;

    public UndertowSipDeploymentService(UndertowDeploymentService deploymentService, DeploymentUnit deploymentUnit) {
        this.deploymentService = deploymentService;
        this.deploymentUnit = deploymentUnit;
    }

    @Override
    public UndertowSipDeploymentService getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }

    @Override
    public void start(StartContext context) throws StartException {
        ServerLogger.DEPLOYMENT_LOGGER.debug("UndertowSipDeploymentService.start()");

        Deployment deployment = this.deploymentService.getDeployment();

        DeploymentUnit anchorDu = SIPWebContext.getSipContextAnchorDu(this.deploymentUnit);
        SIPWebContext sipWebContext = anchorDu.getAttachment(SIPWebContext.ATTACHMENT_KEY);

        try {
            Module module = this.deploymentUnit.getAttachment(Attachments.MODULE);
            sipWebContext.init(deployment, module.getClassLoader());
            sipWebContext.start();

        } catch (ServletException e) {
            ServerLogger.DEPLOYMENT_LOGGER.error(e.getMessage(), e);
        }
        ServerLogger.DEPLOYMENT_LOGGER.debug("UndertowSipDeploymentService.start() finished");
    }

    @Override
    public void stop(StopContext context) {
        DeploymentUnit anchorDu = SIPWebContext.getSipContextAnchorDu(this.deploymentUnit);
        SIPWebContext sipWebContext = anchorDu.getAttachment(SIPWebContext.ATTACHMENT_KEY);

        try {
            if (sipWebContext != null) {
                sipWebContext.stop();
                sipWebContext.listenerStop();
            }
        } catch (ServletException e) {
            ServerLogger.DEPLOYMENT_LOGGER.error("UndertowSipDeploymentService.stop() ends with error: " + e.getMessage(), e);
        }
    }
}
