package org.mobicents.servlet.sip.undertow.rules;

import java.util.ArrayList;

import javax.servlet.sip.SipServletRequest;

import org.mobicents.servlet.sip.core.descriptor.MatchingRule;

/**
 * @author Thomas Leseney
 *
 *         This class is based on the contents of org.mobicents.servlet.sip.catalina.rules package from sip-servlet-as7 project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class OrRule implements MatchingRule {

    private ArrayList<MatchingRule> criteria = new ArrayList<MatchingRule>();

    public OrRule() {
    }

    public void addCriterion(MatchingRule c) {
        criteria.add(c);
    }

    public boolean matches(SipServletRequest request) {
        for (MatchingRule rule : criteria) {
            if (rule.matches(request)) {
                return true;
            }
        }
        return false;
    }

    public String getExpression() {
        StringBuffer sb = new StringBuffer("(");
        boolean first = true;

        for (MatchingRule rule : criteria) {
            if (first) {
                first = false;
            } else {
                sb.append(" or ");
            }
            sb.append(rule.getExpression());
        }
        sb.append(")");
        return sb.toString();
    }
}
