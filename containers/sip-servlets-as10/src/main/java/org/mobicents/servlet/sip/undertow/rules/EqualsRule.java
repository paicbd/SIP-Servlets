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
public class EqualsRule extends RequestRule implements MatchingRule {
    private String value;
    private boolean ignoreCase;

    public EqualsRule(String var, String value, boolean ignoreCase) {
        super(var);
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    public boolean matches(SipServletRequest request) {
        if (!ignoreCase) {
            return value.equals(getValue(request));
        }
        return value.equalsIgnoreCase(getValue(request));
    }

    public String getExpression() {
        return "(" + getVarName() + " == " + value + ")";
    }
}
