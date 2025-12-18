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
public class SubdomainRule extends RequestRule implements MatchingRule {

    private String value;

    public SubdomainRule(String var, String value) {
        super(var);
        this.value = value;
    }

    public boolean matches(SipServletRequest request) {
        String requestValue = getValue(request);
        if (requestValue == null) {
            return false;
        }
        if (requestValue.endsWith(value)) {
            int len1 = requestValue.length();
            int len2 = value.length();
            return (len1 == len2 || (requestValue.charAt(len1 - len2 - 1) == '.'));
        }
        return false;
    }

    public String getExpression() {
        return "(" + getVarName() + " subdomainOf " + value + ")";
    }
}
