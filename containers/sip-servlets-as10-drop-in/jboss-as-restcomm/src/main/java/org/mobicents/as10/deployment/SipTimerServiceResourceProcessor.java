package org.mobicents.as10.deployment;

import javax.servlet.sip.TimerService;

import org.jboss.as.ee.component.InjectionSource;
import org.jboss.as.ee.component.deployers.EEResourceReferenceProcessor;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;

/**
 * Processes {@link javax.annotation.Resource @Resource} and {@link javax.annotation.Resources @Resources} annotations for a
 * {@link javax.servlet.sip.TimerService} type resource
 * <p/>
 *
 * @author Jaikiran Pai
 * @author josemrecio@gmail.com
 *
 *         This class is based on the contents of org.mobicents.as7.deployment package from jboss-as7-mobicents project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 *
 */
public final class SipTimerServiceResourceProcessor implements EEResourceReferenceProcessor {

    DeploymentUnit sipDeploymentUnit;

    SipTimerServiceResourceProcessor(DeploymentUnit du) {
        super();
        sipDeploymentUnit = du;
    }

    @Override
    public String getResourceReferenceType() {
        return TimerService.class.getName();
    }

    @Override
    public InjectionSource getResourceReferenceBindingSource() throws DeploymentUnitProcessingException {
        return new SipTimerServiceInjectionSource(sipDeploymentUnit);
    }

}
