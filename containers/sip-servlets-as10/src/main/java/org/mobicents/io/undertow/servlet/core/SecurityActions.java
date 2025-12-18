package org.mobicents.io.undertow.servlet.core;

import io.undertow.server.HttpHandler;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionManager;
import io.undertow.servlet.api.ThreadSetupAction;
import io.undertow.servlet.handlers.ServletInitialHandler;
import io.undertow.servlet.handlers.ServletPathMatches;
import io.undertow.servlet.handlers.ServletRequestContext;
import org.mobicents.io.undertow.servlet.spec.ConvergedHttpSessionFacade;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * This class is based on protected class io.undertow.servlet.core.SecurityActions.
 *
 * @author kakonyi.istvan@alerant.hu
 */
class SecurityActions {

    static String getSystemProperty(final String prop) {
        if (System.getSecurityManager() == null) {
            return System.getProperty(prop);
        } else {
            return (String) AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    return System.getProperty(prop);
                }
            });
        }
    }

    static HttpSession forSession(final Session session, final ServletContext servletContext, final boolean newSession, final SessionManager manager) {
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

    static ServletRequestContext currentServletRequestContext() {
        if (System.getSecurityManager() == null) {
            return ServletRequestContext.current();
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<ServletRequestContext>() {
                @Override
                public ServletRequestContext run() {
                    return ServletRequestContext.current();
                }
            });
        }
    }

    static void setCurrentRequestContext(final ServletRequestContext servletRequestContext) {
        if (System.getSecurityManager() == null) {
            ServletRequestContext.setCurrentRequestContext(servletRequestContext);
        } else {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    ServletRequestContext.setCurrentRequestContext(servletRequestContext);
                    return null;
                }
            });
        }
    }

    static void clearCurrentServletAttachments() {
        if (System.getSecurityManager() == null) {
            ServletRequestContext.clearCurrentServletAttachments();
        } else {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    ServletRequestContext.clearCurrentServletAttachments();
                    return null;
                }
            });
        }
    }

    static ServletInitialHandler createServletInitialHandler(final ServletPathMatches paths, final HttpHandler next, final ThreadSetupAction setupAction, final ServletContext servletContext) {
        if (System.getSecurityManager() == null) {
            return new ServletInitialHandler(paths, next, currentServletRequestContext().getDeployment(), null);
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<ServletInitialHandler>() {
                @Override
                public ServletInitialHandler run() {
                    return new ServletInitialHandler(paths, next, currentServletRequestContext().getDeployment(), null);
                }
            });
        }
    }

}
