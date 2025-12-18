package org.mobicents.metadata.sip.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * sip-app/servlet-mapping metadata
 *
 * @author jean.deruelle@gmail.com
 * @version $Revision$
 *
 *          This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *          re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class SipServletMappingMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;
    protected String servletName;
    protected PatternMetaData pattern;

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public PatternMetaData getPattern() {
        return pattern;
    }

    public void setPattern(PatternMetaData pattern) {
        this.pattern = pattern;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("SipServletMappingMetaData(id=");
        tmp.append(getId());
        tmp.append(",servletName=");
        tmp.append(servletName);
        tmp.append(",pattern=");
        tmp.append(pattern);
        tmp.append(')');
        return tmp.toString();
    }

}
