package org.mobicents.metadata.sip.spec;

/**
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 *
 */
public class ExistsMetaData extends ConditionMetaData {
    private static final long serialVersionUID = 1;
    private String var;

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
     * @param varMetaData the object to copy values from
     */
    public void setFromVarMetaData(VarMetaData varMetaData) {
        setVar(varMetaData.getVar());
    }
}
