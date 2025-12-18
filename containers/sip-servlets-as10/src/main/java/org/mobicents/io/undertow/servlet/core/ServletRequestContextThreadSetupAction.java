package org.mobicents.io.undertow.servlet.core;

import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.api.ThreadSetupAction;
import io.undertow.servlet.handlers.ServletRequestContext;

/**
 * This class is based on protected class io.undertow.servlet.core.ServletRequestContextThreadSetupAction.
 *
 * @author kakonyi.istvan@alerant.hu
 */
class ServletRequestContextThreadSetupAction implements ThreadSetupAction {

    static final ServletRequestContextThreadSetupAction INSTANCE = new ServletRequestContextThreadSetupAction();

    private ServletRequestContextThreadSetupAction() {

    }

    private static final Handle HANDLE = new Handle() {
        @Override
        public void tearDown() {
            SecurityActions.clearCurrentServletAttachments();
        }
    };

    @Override
    public Handle setup(HttpServerExchange exchange) {
        if(exchange == null) {
            return null;
        }
        ServletRequestContext servletRequestContext = exchange.getAttachment(ServletRequestContext.ATTACHMENT_KEY);
        SecurityActions.setCurrentRequestContext(servletRequestContext);
        return HANDLE;
    }
}
