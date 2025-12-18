package org.mobicents.io.undertow.servlet.api;

import org.mobicents.io.undertow.servlet.core.ConvergedServletContainerImpl;

import io.undertow.servlet.api.ServletContainer;
/**
 * Factory class for creating org.mobicents.io.undertow.servlet.core.ConvergedServletContainerImpl
 *
 * @author kakonyi.istvan@alerant.hu
 */
public interface ConvergedServletContainer extends ServletContainer{

    public static class ConvergedFactory {

        public static ConvergedServletContainer newInstance() {
            return new ConvergedServletContainerImpl();
        }
    }

    public void addConvergedDeployment(String deploymentName);
    public void removeConvergedDeployment(String deploymentName);
    public boolean isConvergedDeployment(String deploymentName);
}
