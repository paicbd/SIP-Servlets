

package org.mobicents.servlet.sip.core.session;

import org.apache.log4j.Logger;
import org.mobicents.javax.servlet.sip.SipSessionAsynchronousWork;
import org.mobicents.servlet.sip.core.MobicentsSipFactory;
import org.mobicents.servlet.sip.core.SipContext;
import org.mobicents.servlet.sip.core.SipManager;

/**
 * Wrapper around the AsynchronousWork to make sure the work is done in a thread safe manner
 * 
 * @author jean.deruelle@gmail.com
 *
 */
public class SipSessionAsyncTask implements Runnable {
	private static final Logger logger = Logger.getLogger(SipSessionAsyncTask.class);	
	private SipSessionKey key;
	private SipSessionAsynchronousWork work;
	private MobicentsSipFactory sipFactoryImpl;
	
	public SipSessionAsyncTask(SipSessionKey key,
			SipSessionAsynchronousWork work, MobicentsSipFactory sipFactory) {
		this.key = key;
		this.work = work;
		this.sipFactoryImpl = sipFactory;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (logger.isDebugEnabled()){
    		logger.debug("run");
    	}
		
		final SipContext sipContext = sipFactoryImpl.getSipApplicationDispatcher().findSipApplication(key.getApplicationName());
		if(sipContext != null) {
			SipManager sipManager = sipContext.getSipManager();
			final SipApplicationSessionKey sipApplicationSessionKey = SessionManagerUtil.getSipApplicationSessionKey(
					key.getApplicationName(), 
					key.getApplicationSessionId(),
					null);								
			MobicentsSipApplicationSession sipApplicationSession = sipManager.getSipApplicationSession(sipApplicationSessionKey, false);
			MobicentsSipSession sipSession = sipManager.getSipSession(key, false, sipFactoryImpl, sipApplicationSession);	
					
			if(sipSession != null) {				
				boolean batchStarted = false;
				ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
				try {
					sipContext.enterSipContext();	
					sipContext.enterSipApp(sipApplicationSession, sipSession, false, true);
					batchStarted = sipContext.enterSipAppHa(true);
					
					work.doAsynchronousWork(sipSession);
				} catch(Throwable t) {
					logger.error("An unexpected exception happened in the SipSessionAsynchronousWork callback on sip session " + key, t);
				} finally {
					sipContext.exitSipAppHa(null, null, batchStarted);
					sipContext.exitSipApp(sipApplicationSession, sipSession);
					sipContext.exitSipContext(oldClassLoader);				
				}
			} else {
				if(logger.isDebugEnabled()) {
					logger.debug("SipSession " + key + " couldn't be found, it may have been already invalidated.");
				}
			}
		}
	}
}
