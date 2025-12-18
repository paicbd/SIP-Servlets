package org.mobicents.servlet.sip.undertow.rules.request;

import javax.servlet.sip.URI;

/**
 * @author Thomas Leseney
 *
 *         This class is based on the contents of org.mobicents.servlet.sip.catalina.rules.request package from sip-servlet-as7
 *         project, re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class Scheme implements Extractor {

    public Scheme(String token) {
        if (!token.equals("uri")) {
            throw new IllegalArgumentException("Invalid expression: scheme after " + token);
        }
    }

    public Object extract(Object input) {
        return ((URI) input).getScheme();
    }
}
