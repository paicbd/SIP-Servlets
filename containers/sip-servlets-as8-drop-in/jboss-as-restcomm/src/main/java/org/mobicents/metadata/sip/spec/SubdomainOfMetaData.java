package org.mobicents.metadata.sip.spec;

/**
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 *
 */
public class SubdomainOfMetaData extends ConditionMetaData {
    private static final long serialVersionUID = 1;
    private String var;
    private String value;

    /**
     * @param var the var to set
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * @return the var
     */
    public String getVar() {
        return var;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param varMetaData the object to copy values from
     */
    public void setFromVarMetaData(VarMetaData varMetaData) {
        setVar(varMetaData.getVar());
    }
}
