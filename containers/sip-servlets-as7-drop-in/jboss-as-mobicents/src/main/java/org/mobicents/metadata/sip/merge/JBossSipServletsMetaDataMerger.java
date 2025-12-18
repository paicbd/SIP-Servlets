package org.mobicents.metadata.sip.merge;

import org.jboss.metadata.merge.web.jboss.JBossServletMetaDataMerger;
import org.jboss.metadata.web.jboss.JBossServletMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.mobicents.metadata.sip.jboss.JBossSipServletsMetaData;
import org.mobicents.metadata.sip.spec.SipServletsMetaData;

/**
 * jboss-web/servlet collection
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66673 $
 */
public class JBossSipServletsMetaDataMerger {
    public static JBossSipServletsMetaData merge(JBossSipServletsMetaData override, SipServletsMetaData original) {
        JBossSipServletsMetaData merged = new JBossSipServletsMetaData();
        if (override == null && original == null)
            return merged;

        if (original != null) {
            for (ServletMetaData smd : original) {
                String key = smd.getKey();
                if (override != null && override.containsKey(key)) {
                    JBossServletMetaData overrideSMD = override.get(key);
                    JBossServletMetaData jbs = new JBossServletMetaData();
                    JBossServletMetaDataMerger.merge(jbs, overrideSMD, smd);
                    merged.add(jbs);
                } else {
                    JBossServletMetaData jbs = new JBossServletMetaData();
                    JBossServletMetaDataMerger.merge(jbs, null, smd);
                    merged.add(jbs);
                }
            }
        }

        // Process the remaining overrides
        if (override != null) {
            for (JBossServletMetaData jbs : override) {
                String key = jbs.getKey();
                if (merged.containsKey(key))
                    continue;
                merged.add(jbs);
            }
        }

        return merged;
    }
}
