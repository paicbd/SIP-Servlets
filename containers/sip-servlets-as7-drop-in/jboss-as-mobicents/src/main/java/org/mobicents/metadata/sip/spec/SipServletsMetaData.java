package org.mobicents.metadata.sip.spec;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65943 $
 */
//public class SipServletsMetaData extends AbstractMappedMetaData<ServletMetaData> {
public class SipServletsMetaData extends ServletsMetaData {

    private static final long serialVersionUID = 1;

    public SipServletsMetaData() {
        super("sip app servlets");
    }
}