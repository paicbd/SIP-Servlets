package org.mobicents.servlet.sip.message;

import javax.sip.Dialog;
import javax.sip.Transaction;
import javax.sip.message.Request;
import javax.sip.message.Response;

import org.mobicents.servlet.sip.core.MobicentsSipFactory;
import org.mobicents.servlet.sip.core.MobicentsSipServletMessageFactory;
import org.mobicents.servlet.sip.core.message.MobicentsSipServletRequest;
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
public class Servlet3SipServletMessageFactory implements MobicentsSipServletMessageFactory {

    private SipFactoryImpl sipFactoryImpl;

    /*
     * (non-Javadoc)
     *
     * @see org.mobicents.servlet.sip.core.MobicentsSipServletMessageFactory# createSipServletRequest(javax.sip.message.Request,
     * org.mobicents.servlet.sip.core.MobicentsSipFactory, org.mobicents.servlet.sip.core.session.MobicentsSipSession,
     * javax.sip.Transaction, javax.sip.Dialog, boolean)
     */
    @Override
    public MobicentsSipServletRequest createSipServletRequest(Request request, MobicentsSipSession sipSession,
            Transaction transaction, Dialog dialog, boolean createDialog) {
        return new Servlet3SipServletRequestImpl(request, sipFactoryImpl, sipSession, transaction, dialog, createDialog);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mobicents.servlet.sip.core.MobicentsSipServletMessageFactory#
     * createSipServletResponse(javax.sip.message.Response, org.mobicents.servlet.sip.core.MobicentsSipFactory,
     * javax.sip.Transaction, org.mobicents.servlet.sip.core.session.MobicentsSipSession, javax.sip.Dialog, boolean, boolean)
     */
    @Override
    public MobicentsSipServletResponse createSipServletResponse(Response response, Transaction transaction,
            MobicentsSipSession session, Dialog dialog, boolean hasBeenReceived, boolean isRetransmission) {
        return new Servlet3SipServletResponseImpl(response, sipFactoryImpl, transaction, session, dialog, hasBeenReceived,
                isRetransmission);
    }

    /**
     * @param mobicentsSipFactory the mobicentsSipFactory to set
     */
    public void setMobicentsSipFactory(MobicentsSipFactory mobicentsSipFactory) {
        this.sipFactoryImpl = (SipFactoryImpl) mobicentsSipFactory;
    }

    /**
     * @return the mobicentsSipFactory
     */
    public MobicentsSipFactory getMobicentsSipFactory() {
        return sipFactoryImpl;
    }
}
