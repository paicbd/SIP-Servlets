

package org.mobicents.servlet.sip.core.message;

import javax.servlet.sip.SipServletMessage;
import javax.sip.Transaction;
import javax.sip.message.Message;

import org.mobicents.servlet.sip.core.session.MobicentsSipApplicationSession;
import org.mobicents.servlet.sip.core.session.MobicentsSipSession;
import org.mobicents.servlet.sip.core.session.MobicentsSipSessionKey;

/**
 * Extension to the Sip Servlet Message interface from JSR 289
 * 
 * @author jean.deruelle@gmail.com
 *
 */
public interface MobicentsSipServletMessage extends SipServletMessage {
	MobicentsSipSession getSipSession();
	void setSipSession(MobicentsSipSession sipSession);
	// https://github.com/RestComm/sip-servlets/issues/101
	MobicentsSipSessionKey getSipSessionKey();
	void setSipSessionKey(MobicentsSipSessionKey sipSession);
	
	Transaction getTransaction();
	void setTransaction(Transaction transaction);
	
	MobicentsTransactionApplicationData getTransactionApplicationData();
	
	Message getMessage();

	// https://code.google.com/p/sipservlets/issues/detail?id=21
	boolean isMessageSent();
	
	MobicentsSipApplicationSession getSipApplicationSession(boolean create);
}
