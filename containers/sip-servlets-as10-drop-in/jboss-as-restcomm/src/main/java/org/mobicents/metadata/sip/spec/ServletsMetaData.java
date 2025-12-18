package org.mobicents.metadata.sip.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65943 $
 *
 *          This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *          re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class ServletsMetaData extends AbstractMappedMetaData<ServletMetaData> {
    private static final long serialVersionUID = 1;

    public ServletsMetaData() {
        super("sip app servlets");
    }

    public ServletsMetaData(String key) {
        super(key);
    }
}
