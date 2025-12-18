package org.mobicents.metadata.sip.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author jean.deruelle@gmail.com
 *
 */
public class IdentityAssertionMetaData extends IdMetaDataImpl {

    private static final long serialVersionUID = 1;

    private String identityAssertionScheme;
    private String identityAssertionSupport;

    /**
     * @param identityAssertionScheme the identityAssertionScheme to set
     */
    public void setIdentityAssertionScheme(String identityAssertionScheme) {
        this.identityAssertionScheme = identityAssertionScheme;
    }

    /**
     * @return the identityAssertionScheme
     */
    public String getIdentityAssertionScheme() {
        return identityAssertionScheme;
    }

    /**
     * @param identityAssertionSupport the identityAssertionSupport to set
     */
    public void setIdentityAssertionSupport(String identityAssertionSupport) {
        this.identityAssertionSupport = identityAssertionSupport;
    }

    /**
     * @return the identityAssertionSupport
     */
    public String getIdentityAssertionSupport() {
        return identityAssertionSupport;
    }

}
