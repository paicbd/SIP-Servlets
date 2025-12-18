package org.mobicents.io.undertow.servlet.core;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mobicents.io.undertow.servlet.api.ConvergedServletContainer;

import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.core.ServletContainerImpl;

/**
 * This class extends io.undertow.servlet.core.ServletContainerImpl to override its addDeployment() method. This method creates
 * ConvergedDeploymentManagerImpl which uses ConvergedServletContextImpl.
 *
 * @author kakonyi.istvan@alerant.hu
 * @author balogh.gabor@alerant.hu
 */
public class ConvergedServletContainerImpl extends ServletContainerImpl implements ConvergedServletContainer {

    private Set<String> convergedDeployments = Collections.synchronizedSet(new HashSet<String>());

    @Override
    public DeploymentManager addDeployment(final DeploymentInfo deployment) {
        final DeploymentInfo dep = deployment.clone();
        DeploymentManager deploymentManager = null;
        if (isConvergedDeployment(dep.getDeploymentName())) {
            deploymentManager = new ConvergedDeploymentManagerImpl(dep, this);

            // accessing parent fields using reflection:
            try {
                Field deploymentsField = ServletContainerImpl.class.getDeclaredField("deployments");
                deploymentsField.setAccessible(true);
                Map<String, DeploymentManager> deployments = (Map<String, DeploymentManager>) deploymentsField.get(this);
                deployments.put(dep.getDeploymentName(), deploymentManager);
                deploymentsField.setAccessible(false);
                Field deploymentsByPathField = ServletContainerImpl.class.getDeclaredField("deploymentsByPath");
                deploymentsByPathField.setAccessible(true);
                Map<String, DeploymentManager> deploymentsByPath = (Map<String, DeploymentManager>) deploymentsByPathField
                        .get(this);
                deploymentsByPath.put(dep.getContextPath(), deploymentManager);
                deploymentsByPathField.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            deploymentManager = super.addDeployment(deployment);
        }
        return deploymentManager;
    }

    public void addConvergedDeployment(String deploymentName) {
        this.convergedDeployments.add(deploymentName);
    }

    public void removeConvergedDeployment(String deploymentName) {
        this.convergedDeployments.remove(deploymentName);
    }

    public boolean isConvergedDeployment(String deploymentName) {
        for (String convergedDeploymentName : convergedDeployments) {
            if (deploymentName.contains(convergedDeploymentName)) {
                return true;
            }
        }
        return false;
    }
}
