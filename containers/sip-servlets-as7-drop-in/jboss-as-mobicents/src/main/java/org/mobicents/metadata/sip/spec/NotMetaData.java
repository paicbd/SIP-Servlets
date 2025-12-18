package org.mobicents.metadata.sip.spec;

/**
 * @author jean.deruelle@gmail.com
 *
 */
public class NotMetaData extends ConditionMetaData {
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
