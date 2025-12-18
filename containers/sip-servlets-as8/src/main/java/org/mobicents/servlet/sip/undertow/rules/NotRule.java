package org.mobicents.servlet.sip.undertow.rules;

import javax.servlet.sip.SipServletRequest;

import org.mobicents.servlet.sip.core.descriptor.MatchingRule;

/**
 * @author Thomas Leseney
 *
 *         This class is based on the contents of org.mobicents.servlet.sip.catalina.rules package from sip-servlet-as7 project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class NotRule implements MatchingRule {

    private MatchingRule criterion;

    public NotRule() {
    }

    public void setCriterion(MatchingRule c) {
        criterion = c;
    }

    public boolean matches(SipServletRequest request) {
        return !criterion.matches(request);
    }

    public String getExpression() {
        return "!" + criterion.getExpression();
    }
}
