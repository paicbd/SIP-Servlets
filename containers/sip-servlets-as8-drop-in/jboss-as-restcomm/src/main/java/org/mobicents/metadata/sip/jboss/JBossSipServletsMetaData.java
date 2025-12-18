package org.mobicents.metadata.sip.jboss;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.web.jboss.JBossServletMetaData;

/**
 * jboss-web/serlvet collection
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66673 $
 *
 *          This class is based on the contents of org.mobicents.metadata.sip.jboss package from jboss-as7-mobicents project,
 *          re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class JBossSipServletsMetaData extends AbstractMappedMetaData<JBossServletMetaData> {
    private static final long serialVersionUID = 1;

    public JBossSipServletsMetaData() {
        super("jboss sip app servlets");
    }
}
