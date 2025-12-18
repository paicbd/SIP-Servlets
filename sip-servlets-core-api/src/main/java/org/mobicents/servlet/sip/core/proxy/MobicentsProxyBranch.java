package org.mobicents.servlet.sip.core.proxy;

import javax.servlet.sip.ProxyBranch;
import javax.servlet.sip.URI;

import org.mobicents.javax.servlet.sip.ProxyBranchExt;
import org.mobicents.servlet.sip.core.DispatcherException;
import org.mobicents.servlet.sip.core.message.MobicentsSipServletRequest;
import org.mobicents.servlet.sip.core.message.MobicentsSipServletResponse;
import org.mobicents.servlet.sip.core.session.MobicentsSipApplicationSession;

/**
 * Extension to the ProxyBranch interface from JSR 289
 * 
 * @author jean.deruelle@gmail.com
 *
 */
public interface MobicentsProxyBranch extends ProxyBranch, ProxyBranchExt {

	boolean isTimedOut();

	boolean isCanceled();

	void start();

	String getTargetURI();

	void cancel1xxTimer();

	void updateTimer(boolean b, MobicentsSipApplicationSession sipApplicationSession);

	void cancelTimer();

	MobicentsSipServletRequest getPrackOriginalRequest();

	void setResponse(MobicentsSipServletResponse sipServletResponse);
	/**
	 * A callback. Here we receive all responses from the proxied requests we have sent.
	 * 
	 * @param response
	 * @throws DispatcherException 
	 */
	void onResponse(MobicentsSipServletResponse sipServletResponse, int status) throws DispatcherException;

	void proxySubsequentRequest(MobicentsSipServletRequest sipServletRequest);

}
