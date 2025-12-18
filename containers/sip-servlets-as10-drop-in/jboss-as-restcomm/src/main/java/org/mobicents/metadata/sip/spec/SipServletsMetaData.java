package org.mobicents.metadata.sip.spec;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65943 $
 *
 *          This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *          re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
// public class SipServletsMetaData extends AbstractMappedMetaData<ServletMetaData> {
public class SipServletsMetaData extends ServletsMetaData {

    private static final long serialVersionUID = 1;

    public SipServletsMetaData() {
        super("sip app servlets");
    }
}