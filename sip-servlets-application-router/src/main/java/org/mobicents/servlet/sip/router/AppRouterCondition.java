package org.mobicents.servlet.sip.router;

import javax.servlet.sip.SipServletRequest;

/**
 * Interface modelling a condition evaluated during router handling.
 */
public interface AppRouterCondition {

    /**
     * 
     * @param initialRequest the request being evaluated
     * @param info the router configuration in case the condition requires it
     * @return true if the condition is met/enabled
     */
    boolean checkCondition(SipServletRequest initialRequest, DefaultSipApplicationRouterInfo info);
}
