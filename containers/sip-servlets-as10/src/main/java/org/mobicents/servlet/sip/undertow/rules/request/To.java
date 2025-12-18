package org.mobicents.servlet.sip.undertow.rules.request;

import javax.servlet.sip.SipServletRequest;

/**
 * @author Thomas Leseney
 *
 *         This class is based on the contents of org.mobicents.servlet.sip.catalina.rules.request package from sip-servlet-as7
 *         project, re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class To implements Extractor {
    public To(String token) {
        if (!token.equals("request")) {
            throw new IllegalArgumentException("Invalid expression: from after " + token);
        }
    }

    public Object extract(Object input) {
        return ((SipServletRequest) input).getTo();
    }
}
