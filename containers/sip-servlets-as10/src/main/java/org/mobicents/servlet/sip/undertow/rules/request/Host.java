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
public class Host implements Extractor {
    public Host(String token) {
        if (!token.equals("uri")) {
            throw new IllegalArgumentException("Invalid expression: host after " + token);
        }
    }

    public Object extract(Object input) {
        URI uri = (URI) input;
        if (uri.isSipURI()) {
            return ((SipURI) uri).getHost();
        } else {
            return null;
        }
    }
}
