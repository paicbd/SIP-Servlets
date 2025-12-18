package org.mobicents.metadata.sip.jboss;

import org.mobicents.metadata.sip.spec.SipMetaData;

/**
 * Sip application spec metadata if no xsd is defined in the sip.xml.
 *
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.jboss package from jboss-as7-mobicents project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class JBossSip11MetaData extends SipMetaData {
    private static final long serialVersionUID = 1;
    private boolean metadataComplete;

    public boolean isMetadataComplete() {
        return metadataComplete;
    }

    public void setMetadataComplete(boolean metadataComplete) {
        this.metadataComplete = metadataComplete;
    }

    @Override
    public String getVersion() {
        return "1.1";
    }
}
