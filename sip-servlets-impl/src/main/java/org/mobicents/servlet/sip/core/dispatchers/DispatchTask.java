

package org.mobicents.servlet.sip.core.dispatchers;

import javax.sip.SipProvider;
import javax.sip.message.Request;
import javax.sip.message.Response;

import org.apache.log4j.Logger;
import org.mobicents.servlet.sip.core.DispatcherException;
import org.mobicents.servlet.sip.message.SipServletMessageImpl;
import org.mobicents.servlet.sip.message.SipServletRequestImpl;

/**
 * Generic class that allows async execution of tasks in one of the executors depending
 * on what session isolation you need. Error handling is also done here.
 * 
 * @author Vladimir Ralev
 *
 */
public abstract class DispatchTask implements Runnable {
	
	private static final Logger logger = Logger.getLogger(DispatchTask.class);
	
	protected SipServletMessageImpl sipServletMessage;
	protected SipProvider sipProvider;
	
	public DispatchTask(SipServletMessageImpl sipServletMessage, SipProvider sipProvider) {
		this.sipProvider = sipProvider;
		this.sipServletMessage = sipServletMessage;
	}

	abstract public void dispatch() throws DispatcherException;

	public void run() {
		dispatchAndHandleExceptions();
	}

	public void dispatchAndHandleExceptions () {
		if (logger.isDebugEnabled()){
			logger.debug("dispatchAndHandleExceptions");
		}
		try {
			dispatch();
		} catch (Throwable t) {
			logger.error("Unexpected exception while processing message " + sipServletMessage, t);
			
			if(sipServletMessage instanceof SipServletRequestImpl) {
				if (logger.isDebugEnabled()){
					logger.debug("dispatchAndHandleExceptions - sipServletMessage is instanceof SipServletRequestImpl");
				}
				
				SipServletRequestImpl sipServletRequest = (SipServletRequestImpl) sipServletMessage;
				if(!Request.ACK.equalsIgnoreCase(sipServletRequest.getMethod()) &&
						!Request.PRACK.equalsIgnoreCase(sipServletRequest.getMethod())) {
					if (logger.isDebugEnabled()){
						logger.debug("dispatchAndHandleExceptions - calling sendErrorResponse");
					}
					MessageDispatcher.sendErrorResponse(sipServletRequest.getSipSession().getSipApplicationSession().getSipContext().getSipApplicationDispatcher(), Response.SERVER_INTERNAL_ERROR, sipServletRequest, sipProvider);					
				}
			}
		}		
	}
	
}
