package org.mobicents.servlet.sip.undertow;

import org.apache.log4j.Logger;

/**
 * Task runnning at regular interval that checks whether or not there is still
 * active sip application running
 * 
 * @author jean.deruelle@gmail.com
 *
 */
public class ServiceGracefulStopTask implements Runnable {
	private static final Logger logger = Logger.getLogger(ServiceGracefulStopTask.class);		
	
	SipStandardService sipService;
	
	public ServiceGracefulStopTask(SipStandardService sipService) {
		this.sipService = sipService;
	}
	
	public void run() {
		int numberOfSipApplicationsDeployed = sipService.getSipApplicationDispatcher().findInstalledSipApplications().length;
		if(logger.isTraceEnabled()) {
			logger.trace("ServiceGracefulStopTask running, number of Sip Application still running " + numberOfSipApplicationsDeployed);
		}
		if(numberOfSipApplicationsDeployed <= 0) {
			try {
//				sipService.stop();
				((SipStandardService)sipService).stop();
			} catch (Exception e) {
				logger.error("Couldn't gracefully stop service", e);
			}
		}
	}
}
