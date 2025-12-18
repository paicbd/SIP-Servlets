package org.mobicents.as7.clustering.sip;

import java.util.Collection;
import java.util.Collections;

import org.jboss.as.clustering.web.ClusteringNotSupportedException;
import org.jboss.as.clustering.web.DistributedCacheManager;
import org.jboss.as.clustering.web.DistributedCacheManagerFactory;
import org.jboss.as.clustering.web.LocalDistributableSessionManager;
import org.jboss.as.clustering.web.OutgoingDistributableSessionData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistry;
import org.jboss.msc.service.ServiceTarget;
import org.mobicents.metadata.sip.jboss.JBossConvergedSipMetaData;

/**
 * 
 * 
 * @author Jose M Recio
 * 
 * @version $Revision: $
 */
public class MockDistributedCacheManagerFactory implements DistributedCacheManagerFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T extends OutgoingDistributableSessionData> DistributedCacheManager<T> getDistributedCacheManager(LocalDistributableSessionManager localManager) throws ClusteringNotSupportedException {
        return (DistributedCacheManager<T>) MockDistributedCacheManager.INSTANCE;
    }

    @Override
    public boolean addDeploymentDependencies(ServiceName deploymentServiceName, ServiceRegistry registry, ServiceTarget target, ServiceBuilder<?> builder, JBossWebMetaData metaData) {
    	if (metaData instanceof JBossConvergedSipMetaData) {
    		return true;
    	}
    	return true;
    }

    @Override
    public Collection<ServiceController<?>> installServices(ServiceTarget target) {
        return Collections.emptySet();
    }
}
