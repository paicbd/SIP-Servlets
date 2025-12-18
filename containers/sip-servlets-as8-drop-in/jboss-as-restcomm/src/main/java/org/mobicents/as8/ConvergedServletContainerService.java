package org.mobicents.as8;

/**
 * This class replaces io.undertow.servlet.api.ServletContainerImpl to org.mobicents.io.undertow.servlet.api.ConvergedServletContainerImpl in ServletContainerService during server startup process.
 * ConvergedServletContainerImpl will create an org.mobicents.io.undertow.servlet.core.ConvergedDeploymentManager object which can be used to create ConvergedSession instead of plain HttpSession.
 *
 * @author kakonyi.istvan@alerant.hu
 * */
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.undertow.servlet.api.ServletContainer;

import org.jboss.as.controller.OperationContext;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.mobicents.io.undertow.servlet.api.ConvergedServletContainer;
import org.wildfly.extension.undertow.ServletContainerService;

public class ConvergedServletContainerService implements Service<ConvergedServletContainerService> {
    public static final ServiceName SERVICE_NAME = ServiceName.of("ConvergedServletContainerService");

    private OperationContext context;
    private List<ServiceName> servletContainerServiceNames;

    public ConvergedServletContainerService(OperationContext context, List<ServiceName> servletContainerServiceNames) {
        this.context = context;

        if(servletContainerServiceNames==null){
            servletContainerServiceNames = new ArrayList<ServiceName>();
        }
        this.servletContainerServiceNames = servletContainerServiceNames;
    }

    @Override
    public ConvergedServletContainerService getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }

    @Override
    public void start(StartContext context) throws StartException {
        ServletContainer servletContainer = ConvergedServletContainer.ConvergedFactory.newInstance();

        for(ServiceName name: this.servletContainerServiceNames){
            ServiceController<ServletContainerService> servletContainerServiceController =
                    (ServiceController<ServletContainerService>) this.context.getServiceRegistry(false).getService(name);

            ServletContainerService servletContainerService = servletContainerServiceController.getValue();
            //using reflection to get the container:
            try{
                Field servletContainerField = ServletContainerService.class.getDeclaredField("servletContainer");
                servletContainerField.setAccessible(true);
                servletContainerField.set(servletContainerService, servletContainer);
                servletContainerField.setAccessible(false);
            }catch(IllegalAccessException | NoSuchFieldException e){
                throw new StartException(e);
            }
        }
    }

    @Override
    public void stop(StopContext context) {
    }

}
