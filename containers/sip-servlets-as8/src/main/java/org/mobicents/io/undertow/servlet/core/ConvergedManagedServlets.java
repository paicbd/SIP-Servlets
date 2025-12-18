package org.mobicents.io.undertow.servlet.core;

import java.util.HashMap;
import java.util.Map;

import org.mobicents.servlet.sip.undertow.SipServletImpl;

import io.undertow.servlet.api.ServletInfo;
import io.undertow.servlet.core.DeploymentImpl;
import io.undertow.servlet.core.ManagedServlet;
import io.undertow.servlet.core.ManagedServlets;
import io.undertow.servlet.handlers.ServletHandler;
import io.undertow.servlet.handlers.ServletPathMatches;
import io.undertow.util.CopyOnWriteMap;

public class ConvergedManagedServlets extends ManagedServlets {

    private final ConvergedDeploymentImpl deployment;
    private final ServletPathMatches servletPaths;

    private final Map<String, ServletHandler> managedServletMap = new CopyOnWriteMap<>();

    public ConvergedManagedServlets(final DeploymentImpl deployment, final ServletPathMatches servletPaths) {
        super(deployment, servletPaths);
        this.deployment = (ConvergedDeploymentImpl) deployment;
        this.servletPaths = servletPaths;
    }

    @Override
    public ServletHandler addServlet(final ServletInfo servletInfo) {
        SipServletImpl managedServlet = new SipServletImpl(servletInfo,
                ((ConvergedDeploymentImpl) deployment).getConvergedServletContext());
        ServletHandler servletHandler = new ServletHandler(managedServlet);
        managedServletMap.put(servletInfo.getName(), servletHandler);
        deployment.addLifecycleObjects(managedServlet);
        this.servletPaths.invalidate();

        return servletHandler;
    }

    @Override
    public ManagedServlet getManagedServlet(final String name) {
        ServletHandler servletHandler = managedServletMap.get(name);
        if (servletHandler == null) {
            return null;
        }
        return servletHandler.getManagedServlet();
    }

    @Override
    public ServletHandler getServletHandler(final String name) {
        return managedServletMap.get(name);
    }

    @Override
    public Map<String, ServletHandler> getServletHandlers() {
        return new HashMap<>(managedServletMap);
    }
}
