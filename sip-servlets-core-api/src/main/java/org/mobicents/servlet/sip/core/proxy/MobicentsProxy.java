

package org.mobicents.servlet.sip.core.proxy;

import java.util.Map;

import javax.servlet.sip.Proxy;
import javax.servlet.sip.ProxyBranch;

import org.mobicents.javax.servlet.sip.ProxyExt;
import org.mobicents.servlet.sip.core.message.MobicentsSipServletRequest;

/**
 * Extension to the Proxy interface from JSR 289
 * 
 * @author jean.deruelle@gmail.com
 *
 */
public interface MobicentsProxy extends Proxy, ProxyExt {

	MobicentsProxyBranch getFinalBranchForSubsequentRequests();
	Map getTransactionMap();
	boolean isTerminationSent();
	void setAckReceived(boolean equalsIgnoreCase);
	void setOriginalRequest(MobicentsSipServletRequest sipServletRequest);
	// https://code.google.com/p/sipservlets/issues/detail?id=266
	void cancelAllExcept(ProxyBranch except, String[] protocol, int[] reasonCode, String[] reasonText, boolean throwExceptionIfCannotCancel);
}
