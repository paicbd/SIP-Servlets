package org.mobicents.servlet.sip.undertow.rules.request;

import javax.servlet.sip.SipURI;
import javax.servlet.sip.URI;

/**
 * @author Thomas Leseney
 *
 *         This class is based on the contents of org.mobicents.servlet.sip.catalina.rules.request package from sip-servlet-as7
 *         project, re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class Port implements Extractor {

    public Port(String token) {
        if (!token.equals("uri")) {
            throw new IllegalArgumentException("Invalid expression: port after " + token);
        }
    }

    public Object extract(Object input) {
        URI uri = (URI) input;
        if (uri.isSipURI()) {
            SipURI sipuri = (SipURI) uri;
            int port = sipuri.getPort();
            if (port < 0) {
                String scheme = sipuri.getScheme();
                if (scheme.equals("sips")) {
                    return "5061";
                } else {
                    return "5060";
                }
            } else {
                return Integer.toString(port);
            }
        } else {
            return null;
        }
    }
}
