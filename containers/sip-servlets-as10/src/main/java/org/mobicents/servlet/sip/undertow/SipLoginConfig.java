package org.mobicents.servlet.sip.undertow;

import io.undertow.servlet.api.LoginConfig;

import java.util.HashMap;

import org.mobicents.servlet.sip.core.security.MobicentsSipLoginConfig;

/**
 *
 * This class is based on org.mobicents.servlet.sip.catalina.SipLoginConfig class from sip-servlet-as7 project, re-implemented
 * for jboss as10 (wildfly) by:
 *
 * @author kakonyi.istvan@alerant.hu
 *
 */
public class SipLoginConfig extends LoginConfig implements MobicentsSipLoginConfig {
    private static final long serialVersionUID = 1L;

    private HashMap<String, String> identityAssertionSchemes = new HashMap<String, String>();

    public SipLoginConfig(String realmName, String loginPage, String errorPage) {
        super(realmName, loginPage, errorPage);
    }

    public SipLoginConfig(String realmName) {
        super(realmName);
    }

    public SipLoginConfig(String mechanismName, String realmName, String loginPage, String errorPage) {
        super(realmName, loginPage, errorPage);
        super.addFirstAuthMethod(mechanismName);
    }

    public SipLoginConfig(String mechanismName, final String realmName) {
        super(mechanismName, realmName, null, null);
    }

    public void addIdentityAssertion(String scheme, String support) {
        identityAssertionSchemes.put(scheme, support);
    }

    public String getIdentitySchemeSettings(String scheme) {
        return identityAssertionSchemes.get(scheme);
    }
}
