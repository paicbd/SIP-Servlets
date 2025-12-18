package org.mobicents.metadata.sip.spec;

import java.util.List;

/**
 * @author jean.deruelle@gmail.com
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
