package org.mobicents.servlet.sip.message;

import javax.sip.Dialog;
import javax.sip.Transaction;
import javax.sip.message.Response;

import org.mobicents.servlet.sip.core.message.MobicentsSipServletResponse;
import org.mobicents.servlet.sip.core.session.MobicentsSipSession;

/**
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on org.mobicents.servlet.sip.message.Servlet3SipServletResponseImpl class from sip-servlet-as7
 *         project, re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 *
 */
public class Servlet3SipServletResponseImpl extends SipServletResponseImpl implements MobicentsSipServletResponse {

    /**
     *
     */
    public Servlet3SipServletResponseImpl() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param response
     * @param sipFactoryImpl
     * @param transaction
     * @param session
     * @param dialog
     * @param hasBeenReceived
     * @param isRetransmission
     */
    public Servlet3SipServletResponseImpl(Response response, SipFactoryImpl sipFactoryImpl, Transaction transaction,
            MobicentsSipSession session, Dialog dialog, boolean hasBeenReceived, boolean isRetransmission) {
        super(response, sipFactoryImpl, transaction, session, dialog, hasBeenReceived, isRetransmission);
    }

	public void setContentLengthLong(long len) {
		setContentLength((int)len);
	}
}
