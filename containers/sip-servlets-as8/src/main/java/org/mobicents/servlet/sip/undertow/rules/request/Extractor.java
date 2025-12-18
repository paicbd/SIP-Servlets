package org.mobicents.servlet.sip.undertow.rules.request;

/**
 * @author Thomas Leseney
 *
 *         This class is based on the contents of org.mobicents.servlet.sip.catalina.rules.request package from sip-servlet-as7
 *         project, re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public interface Extractor {
    Object extract(Object input);
}
