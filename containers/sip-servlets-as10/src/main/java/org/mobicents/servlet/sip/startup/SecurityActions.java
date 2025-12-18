package org.mobicents.servlet.sip.startup;

import io.undertow.server.session.Session;
import io.undertow.server.session.SessionManager;

import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.mobicents.io.undertow.servlet.spec.ConvergedHttpSessionFacade;

/**
 * This class based on io.undertow.servlet.spec.SecurityActions to create ConvergedHttpSession objects.
 *
 * @author kakonyi.istvan@alerant.hu
 * */
class SecurityActions {

    static HttpSession forSession(final Session session, final ServletContext servletContext, final boolean newSession,  final SessionManager manager ) {
        if (System.getSecurityManager() == null) {
            return ConvergedHttpSessionFacade.forConvergedSession(session, servletContext, newSession, manager);
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<HttpSession>() {
                @Override
                public HttpSession run() {
                    return ConvergedHttpSessionFacade.forConvergedSession(session, servletContext, newSession, manager);
                }
            });
        }
    }
}
