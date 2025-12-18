package org.mobicents.metadata.sip.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65943 $
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
