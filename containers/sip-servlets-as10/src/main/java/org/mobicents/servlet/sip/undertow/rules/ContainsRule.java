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
public class ContainsRule extends RequestRule implements MatchingRule {

    private String value;
    private boolean ignoreCase;

    public ContainsRule(String var, String value, boolean ignoreCase) {
        super(var);
        this.value = value;
        if (ignoreCase) {
            this.value = value.toLowerCase();
        }
        this.ignoreCase = ignoreCase;
    }

    public boolean matches(SipServletRequest request) {
        String requestValue = getValue(request);
        if (requestValue == null) {
            return false;
        }
        if (ignoreCase) {
            requestValue = requestValue.toLowerCase();
        }
        return (requestValue.indexOf(value) != -1);
    }

    public String getExpression() {
        return "(" + getVarName() + " contains " + value + ")";
    }
}
