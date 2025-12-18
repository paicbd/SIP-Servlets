package org.mobicents.metadata.sip.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * sip-app/servlet-mapping/pattern metadata
 *
 * @author jean.deruelle@gmail.com
 * @version $Revision$
 */
public class PatternMetaData extends IdMetaDataImpl {

    private static final long serialVersionUID = 1;
    private ConditionMetaData condition;

    /**
     * @param condition the condition to set
     */
    public void setCondition(ConditionMetaData condition) {
        this.condition = condition;
    }

    /**
     * @return the condition
     */
    public ConditionMetaData getCondition() {
        return condition;
    }

}
