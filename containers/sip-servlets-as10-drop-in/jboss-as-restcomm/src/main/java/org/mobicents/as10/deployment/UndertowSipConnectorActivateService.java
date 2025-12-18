package org.mobicents.as10.deployment;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceController.Mode;
import org.mobicents.as10.SipConnectorService;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

/**
 * This service will start up and activate sip connector(s) AFTER all sip deployments finished in order to prevent the sip server to handle incoming messages before sip applications ready.
 *
 * @author kakonyi.istvan@alerant.hu
 */
public class UndertowSipConnectorActivateService implements Service<UndertowSipConnectorActivateService> {
    public static final ServiceName SERVICE_NAME = ServiceName.of("UndertowSipConnectorActivateService");

    private Map<ServiceName,ServiceController<SipConnectorService>> serviceControllers = null;

    public void addServiceController(ServiceController<SipConnectorService> serviceController){
        if(serviceControllers == null){
            serviceControllers = new LinkedHashMap<ServiceName,ServiceController<SipConnectorService>>();
        }
        this.serviceControllers.put(serviceController.getName(),serviceController);
    }

    public ServiceController<SipConnectorService> getServiceController(ServiceName serviceName){
        if(serviceControllers == null){
            return null;
        }
        return this.serviceControllers.get(serviceName);
    }

    @Override
    public UndertowSipConnectorActivateService getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }

    @Override
    public void start(StartContext context) throws StartException {
        if(serviceControllers!=null){
            for(ServiceController<SipConnectorService> serviceController : serviceControllers.values()){
                //activate the sip connector:
                if(serviceController!=null && serviceController.getMode() != Mode.ACTIVE){
                    serviceController.setMode(Mode.ACTIVE);
                }
            }
        }
    }

    @Override
    public void stop(StopContext context) {
    }

}
