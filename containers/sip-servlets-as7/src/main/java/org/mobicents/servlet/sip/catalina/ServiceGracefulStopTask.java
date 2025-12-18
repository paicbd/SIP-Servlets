package org.mobicents.servlet.sip.catalina;

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
	long timeToWait;
	long startTime;	
	
	public ServiceGracefulStopTask(StandardService sipService, long timeToWait) {
		this.sipService = sipService;
		this.timeToWait = timeToWait;
		startTime = System.currentTimeMillis();
	}
	
	public void run() {
		int numberOfSipApplicationsDeployed = ((SipService)sipService).getSipApplicationDispatcher().findInstalledSipApplications().length;
		boolean stopPrematuraly = false;
		long currentTime = System.currentTimeMillis();
		// if timeToWait is positive, then we check the time since the task started, if the time is greater than timeToWait we can safely stop the context 
		if(timeToWait > 0 && ((currentTime - startTime) > timeToWait)) {
			stopPrematuraly = true;			
		}
		if(logger.isDebugEnabled()) {
			logger.debug("ServiceGracefulStopTask running, number of Sip Application still running " + numberOfSipApplicationsDeployed 
					+ ", stopPrematurely " + stopPrematuraly);
		}
		if(numberOfSipApplicationsDeployed <= 0 || stopPrematuraly) {
			try {
				sipService.stop();
				((SipStandardService)sipService).shutdownServer();
			} catch (Exception e) {
				logger.error("Couldn't gracefully stop service", e);
			} 
		}
	}
}
