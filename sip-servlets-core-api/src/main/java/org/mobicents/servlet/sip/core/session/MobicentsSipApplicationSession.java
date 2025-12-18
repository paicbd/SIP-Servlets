package org.mobicents.servlet.sip.core.session;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.sip.ServletTimer;
import javax.servlet.sip.SipApplicationSession;

import org.mobicents.javax.servlet.sip.SipApplicationSessionExt;
import org.mobicents.servlet.sip.core.SipContext;
import org.mobicents.servlet.sip.core.timers.SipApplicationSessionTimerTask;

/**
 * Extension to the SipApplicationSession interface from JSR 289
 * 
 * @author <A HREF="mailto:jean.deruelle@gmail.com">Jean Deruelle</A> 
 *
 */
public interface MobicentsSipApplicationSession extends SipApplicationSession, SipApplicationSessionExt {
	public static final String SIP_APPLICATION_KEY_PARAM_NAME = "org.mobicents.servlet.sip.ApplicationSessionKey";
	
	boolean addHttpSession(HttpSession httpSession);
	
	boolean removeHttpSession(HttpSession httpSession);
	
	HttpSession findHttpSession(String sessionId);

	SipContext getSipContext();

	void onSipSessionReadyToInvalidate(MobicentsSipSession mobicentsSipSession);

	boolean addSipSession(MobicentsSipSession mobicentsSipSession);

	MobicentsSipApplicationSessionKey getKey();

	void access();	

	boolean hasTimerListener();

	void addServletTimer(ServletTimer servletTimer);
	
	void removeServletTimer(ServletTimer servletTimer, boolean updateAppSessionReadyToInvalidateState);

	void notifySipApplicationSessionListeners(SipApplicationSessionEventType expiration);

	boolean isExpired();
	void setExpired(boolean hasExpired);
	
	long getExpirationTimeInternal();
	boolean isValidInternal();
	
	String getCurrentRequestHandler();

	void setCurrentRequestHandler(String currentRequestHandler);
	
	void tryToInvalidate();
	
	void acquire();
	void release();
	
	MobicentsSipApplicationSession getFacade();
	
	String getJvmRoute();
	void setJvmRoute(String jvmRoute);

	void setExpirationTimerTask(SipApplicationSessionTimerTask expirationTimerTask);
	SipApplicationSessionTimerTask getExpirationTimerTask();
//	void setExpirationTimerFuture(ScheduledFuture<MobicentsSipApplicationSession> schedule);

	long getSipApplicationSessionTimeout();
	
	Set<MobicentsSipSession> getSipSessions(boolean internal);

	void invalidate(boolean bypassCheck);
	public void setOrphan(boolean orphan);

	public boolean isOrphan();
}
