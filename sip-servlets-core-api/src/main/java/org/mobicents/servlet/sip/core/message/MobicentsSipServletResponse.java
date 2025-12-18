

package org.mobicents.servlet.sip.core.message;

import javax.sip.Dialog;

import org.mobicents.javax.servlet.sip.SipServletResponseExt;
import org.mobicents.servlet.sip.core.proxy.MobicentsProxyBranch;

/**
 * 
 * Extension to the Sip Servlet Response interface from JSR 289
 * 
 * @author jean.deruelle@gmail.com
 *
 */
public interface MobicentsSipServletResponse extends MobicentsSipServletMessage, SipServletResponseExt {

	void setBranchResponse(boolean b);	
	/**
	 * Set the Proxy Branch for the given response
	 * @param proxyBranch the proxyBranch to set
	 */
	void setProxyBranch(MobicentsProxyBranch proxyBranch);

	Dialog getDialog();

	boolean isRetransmission();

}
