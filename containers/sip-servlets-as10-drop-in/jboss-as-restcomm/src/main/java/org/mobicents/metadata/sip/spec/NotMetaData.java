package org.mobicents.metadata.sip.spec;

/**
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
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
