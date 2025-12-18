package org.mobicents.as8.deployment;

import java.util.List;

import org.jboss.as.server.ServerLogger;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.mobicents.as8.SipConnectorService;
import org.mobicents.metadata.sip.spec.SipAnnotationMetaData;
import org.mobicents.metadata.sip.spec.SipMetaData;
/**
 * @author kakonyi.istvan@alerant.hu
 */
public class UndertowSipConnectorActivateProcessor implements DeploymentUnitProcessor {

    @SuppressWarnings("unchecked")
    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        SipMetaData sipMetaData = deploymentUnit.getAttachment(SipMetaData.ATTACHMENT_KEY);
        if (sipMetaData == null) {
            SipAnnotationMetaData sipAnnotationMetaData = deploymentUnit.getAttachment(SipAnnotationMetaData.ATTACHMENT_KEY);

            if (sipAnnotationMetaData == null || !sipAnnotationMetaData.isSipApplicationAnnotationPresent()) {
                // http://code.google.com/p/sipservlets/issues/detail?id=168
                // When no sip.xml but annotations only, Application is not recognized as SIP App by AS7

                // In case there is no metadata attached, it means this is not a sip deployment, so we can safely
                // ignore
                // it!
                return;
            }
        }

        //only one thread can execute this part of the method at any time:
        synchronized(UndertowSipConnectorActivateProcessor.class){
            // lets check first if any other sip deployment chain added this service already:
            final ServiceName sipConnectorActivateServiceName = UndertowSipConnectorActivateService.SERVICE_NAME;

            ServiceController<?> sipConnectorActivateServiceController = phaseContext.getServiceRegistry().getService(sipConnectorActivateServiceName);

            if(sipConnectorActivateServiceController==null){
                //this service will start up and activate sip connector(s) AFTER all sip deployments finished in order to prevent the sip server to handle incoming messages before sip applications ready:
                final UndertowSipConnectorActivateService sipConnectorActivateService = new UndertowSipConnectorActivateService();

                final ServiceBuilder<UndertowSipConnectorActivateService> sipConnectorActivateServiceBuilder = phaseContext.getServiceTarget()
                        .addService(sipConnectorActivateServiceName, sipConnectorActivateService);

                List<ServiceName> serviceNames = phaseContext.getServiceRegistry().getServiceNames();
                for(ServiceName serviceName: serviceNames){
                    ServiceController<?> serviceController = phaseContext.getServiceRegistry().getService(serviceName);
                    if(serviceController!=null){
                        if(serviceController.getService() instanceof UndertowSipDeploymentService){
                            //don't start connector service activator until all sip deployments finished:
                            sipConnectorActivateServiceBuilder.addDependency(serviceName);
                        }
                        else if(serviceController.getService() instanceof SipConnectorService) {
                            //set connector service controller to activator service:
                            sipConnectorActivateService.addServiceController((ServiceController<SipConnectorService>)serviceController);
                        }
                    }else{
                        ServerLogger.DEPLOYMENT_LOGGER.warn("ServiceController with name '"+serviceName+"' is null!");
                    }
                }
                sipConnectorActivateServiceBuilder.install();
            }else{
                ServerLogger.DEPLOYMENT_LOGGER.debug("SIP connector activator service has been already installed!");
            }
        }
    }

    @Override
    public void undeploy(DeploymentUnit context) {
    }

}
