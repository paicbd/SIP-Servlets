package org.mobicents.servlet.sip.undertow.rules.request;

import javax.servlet.sip.Address;

/**
 * @author Thomas Leseney
 *
 *         This class is based on the contents of org.mobicents.servlet.sip.catalina.rules.request package from sip-servlet-as7
 *         project, re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class DisplayName implements Extractor {
    public DisplayName(String token) {
        if (!token.equals("from") && !token.equals("to")) {
            throw new IllegalArgumentException("Invalid expression: display-name after " + token);
        }
    }

    public Object extract(Object input) {
        return ((Address) input).getDisplayName();
    }
}
