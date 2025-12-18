package org.mobicents.servlet.sip.catalina;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardService;
import org.apache.log4j.Logger;
import org.mobicents.servlet.sip.core.SipService;

/**
 * Task runnning at regular interval that checks whether or not there is still
 * active sip application running
 * 
 * @author jean.deruelle@gmail.com
 *
 */
public class ServiceGracefulStopTask implements Runnable {
	private static final Logger logger = Logger.getLogger(ServiceGracefulStopTask.class);		
	
	StandardService sipService;
	
	public ServiceGracefulStopTask(StandardService sipService) {
		this.sipService = sipService;
	}
	
	public void run() {
		int numberOfSipApplicationsDeployed = ((SipService)sipService).getSipApplicationDispatcher().findInstalledSipApplications().length;
		if(logger.isTraceEnabled()) {
			logger.trace("ServiceGracefulStopTask running, number of Sip Application still running " + numberOfSipApplicationsDeployed);
		}
		if(numberOfSipApplicationsDeployed <= 0) {
			try {
//				sipService.stop();
				((SipStandardService)sipService).getServer().stop();
			} catch (LifecycleException e) {
				logger.error("Couldn't gracefully stop service", e);
			}
		}
	}
}
