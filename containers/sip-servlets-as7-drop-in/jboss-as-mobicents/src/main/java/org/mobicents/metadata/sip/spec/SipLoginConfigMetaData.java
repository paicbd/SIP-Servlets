package org.mobicents.metadata.sip.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author jean.deruelle@gmail.com
 * @version $Revision$
 */
public class SipLoginConfigMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    protected String authMethod;
    protected String realmName;
    protected IdentityAssertionMetaData identityAssertion;

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public IdentityAssertionMetaData getIdentityAssertion() {
        return identityAssertion;
    }

    public void setIdentityAssertion(IdentityAssertionMetaData identityAssertion) {
        this.identityAssertion = identityAssertion;
    }
}
