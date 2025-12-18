package org.mobicents.metadata.sip.spec;

/**
 * Sip application spec metadata.
 *
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class Sip11MetaData extends SipMetaData {
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
