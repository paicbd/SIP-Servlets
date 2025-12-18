package org.mobicents.servlet.sip.undertow.rules;

import javax.servlet.sip.SipServletRequest;

import org.mobicents.servlet.sip.core.descriptor.MatchingRule;

/**
 * @author Thomas Leseney
 *
 *         This class is based on the contents of org.mobicents.servlet.sip.catalina.rules package from sip-servlet-as7 project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class ExistsRule extends RequestRule implements MatchingRule {

    public ExistsRule(String var) {
        super(var);
    }

    public boolean matches(SipServletRequest request) {
        return getValue(request) != null;
    }

    public String getExpression() {
        return "(" + getVarName() + " != null)";
    }
}
