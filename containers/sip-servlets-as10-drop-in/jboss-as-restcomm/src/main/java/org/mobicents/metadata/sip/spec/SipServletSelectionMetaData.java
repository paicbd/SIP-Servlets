package org.mobicents.metadata.sip.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;
import org.mobicents.servlet.sip.ruby.SipRubyController;

/**
 * @author jean.deruelle@gmail.com
 * @version $Revision$
 *
 *          This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *          re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class SipServletSelectionMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private String mainServlet;

    private SipRubyController sipRubyController;

    private List<SipServletMappingMetaData> sipServletMappings = null;

    public String getMainServlet() {
        return mainServlet;
    }

    public void setMainServlet(String mainServlet) {
        this.mainServlet = mainServlet;
    }

    public List<SipServletMappingMetaData> getSipServletMappings() {
        return sipServletMappings;
    }

    public void addToSipServletMappings(SipServletMappingMetaData sipServletMapping) {
        if (sipServletMappings == null) {
            sipServletMappings = new ArrayList<SipServletMappingMetaData>();
        }
        sipServletMappings.add(sipServletMapping);
    }

    public void setSipServletMappings(List<SipServletMappingMetaData> sipServletMappings) {
        this.sipServletMappings = sipServletMappings;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("ServletSelectionMetaData(id=");
        tmp.append(getId());
        tmp.append(",mainServlet=");
        tmp.append(mainServlet);
        tmp.append(')');
        return tmp.toString();
    }

    /**
     * @param rubyController the rubyController to set
     */
    public void setSipRubyController(SipRubyController sipRubyController) {
        this.sipRubyController = sipRubyController;
    }

    /**
     * @return the rubyController
     */
    public SipRubyController getSipRubyController() {
        return sipRubyController;
    }
}
