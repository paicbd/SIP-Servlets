package org.mobicents.servlet.sip.undertow;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.mobicents.servlet.sip.core.SipContext;

/**
 * @author jean.deruelle@gmail.com
 *
 */
public class ContextGracefulStopTask implements Runnable {
	private static final Logger logger = Logger.getLogger(ContextGracefulStopTask.class);		
	SipContext sipContext;
	long timeToWait;
	long startTime;	

	public ContextGracefulStopTask(SipContext context, long timeToWait) {
		sipContext = context;
		this.timeToWait = timeToWait;
		startTime = System.currentTimeMillis();
	}

	public void run() {		
		int numberOfActiveSipApplicationSessions = sipContext.getSipManager().getActiveSipApplicationSessions();
		int numberOfActiveHttpSessions = ((UndertowSipManager) ((SipContextImpl)sipContext).getSessionManager()).getActiveSessions().size();
		if(logger.isTraceEnabled()) {
			logger.trace("ContextGracefulStopTask running for context " + sipContext.getApplicationName() + ", number of Sip Application Sessions still active " + numberOfActiveSipApplicationSessions + " number of HTTP Sessions still active " + numberOfActiveHttpSessions);
		}
		boolean stopPrematuraly = false;
		long currentTime = System.currentTimeMillis();
		// if timeToWait is positive, then we check the time since the task started, if the time is greater than timeToWait we can safely stop the context 
		if(timeToWait > 0 && ((currentTime - startTime) > timeToWait)) {
			stopPrematuraly = true;			
		}
		if((numberOfActiveSipApplicationSessions <= 0 &&  numberOfActiveHttpSessions <= 0) || stopPrematuraly) {
			try {
				((SipContextImpl)sipContext).stop();					
			} catch (ServletException e) {
				logger.error("Couldn't gracefully stop context " + sipContext.getApplicationName(), e);
			}
		}
	}
}
