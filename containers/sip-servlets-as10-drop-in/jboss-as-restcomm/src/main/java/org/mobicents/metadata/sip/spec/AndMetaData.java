package org.mobicents.metadata.sip.spec;

import java.util.List;

/**
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 *
 */
public class AndMetaData extends ConditionMetaData {
    private static final long serialVersionUID = 1;
    private List<ConditionMetaData> conditions;

    /**
     * @param condition the condition to set
     */
    public void setConditions(List<ConditionMetaData> conditions) {
        this.conditions = conditions;
    }

    /**
     * @return the condition
     */
    public List<ConditionMetaData> getConditions() {
        return conditions;
    }
}
