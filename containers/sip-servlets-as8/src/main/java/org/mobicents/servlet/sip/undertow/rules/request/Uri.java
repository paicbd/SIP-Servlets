package org.mobicents.servlet.sip.undertow.rules.request;

import javax.servlet.sip.Address;
import javax.servlet.sip.SipServletRequest;

/**
 * @author Thomas Leseney
 *
 *         This class is based on the contents of org.mobicents.servlet.sip.catalina.rules.request package from sip-servlet-as7
 *         project, re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class Uri implements Extractor {

    private static final int REQUEST = 1;
    private static final int ADDRESS = 2;

    private int inputType;

    public Uri(String token) {
        if (token.equals("request")) {
            inputType = REQUEST;
        } else if (token.equals("from")) {
            inputType = ADDRESS;
        } else if (token.equals("to")) {
            inputType = ADDRESS;
        } else {
            throw new IllegalArgumentException("Invalid expression: uri after " + token);
        }
    }

    public Object extract(Object input) {
        if (inputType == REQUEST) {
            return ((SipServletRequest) input).getRequestURI();
        } else {
            return ((Address) input).getURI();
        }
    }
}
