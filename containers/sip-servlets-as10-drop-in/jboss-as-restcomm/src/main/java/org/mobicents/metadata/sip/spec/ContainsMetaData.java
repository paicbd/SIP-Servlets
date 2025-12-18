package org.mobicents.metadata.sip.spec;

/**
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 *
 */
public class ContainsMetaData extends ConditionMetaData {
    private static final long serialVersionUID = 1;
    private String var;
    private String value;
    private boolean ignoreCase;

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
     * @param ignoreCase the ignoreCase to set
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * @return the ignoreCase
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     * @param varMetaData the object to copy values from
     */
    public void setFromVarMetaData(VarMetaData varMetaData) {
        setVar(varMetaData.getVar());
        setIgnoreCase(varMetaData.isIgnoreCase());
    }

}
