package org.mobicents.io.undertow.servlet.handlers;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.mobicents.io.undertow.servlet.spec.ConvergedHttpServletRequestFacade;
import org.mobicents.io.undertow.servlet.spec.ConvergedHttpServletResponseFacade;

import io.undertow.servlet.api.Deployment;
import io.undertow.servlet.handlers.ServletPathMatch;
import io.undertow.servlet.handlers.ServletRequestContext;

/**
 * This class extends io.undertow.servlet.handlers.ServletRequestContext to override get/setServletRequest/Response methods.
 * These methods will handle ConvergedHttpServletRequestFacade and ConvergedHttpServletResponseFacade objects.
 *
 * @author kakonyi.istvan@alerant.hu
 * */
public class ConvergedServletRequestContext extends ServletRequestContext{
    private final ConvergedHttpServletRequestFacade originalRequest;
    private final ConvergedHttpServletResponseFacade originalResponse;
    private ServletResponse servletResponse;
    private ServletRequest servletRequest;

    public ConvergedServletRequestContext(final Deployment deployment, final ConvergedHttpServletRequestFacade originalRequest,
            final ConvergedHttpServletResponseFacade originalResponse, final ServletPathMatch originalServletPathMatch) {
        super(deployment, originalRequest.getHttpServletRequestDelegated(), originalResponse.getHttpServletResponseDelegated(), originalServletPathMatch);
        this.originalRequest = originalRequest;
        this.originalResponse = originalResponse;
        this.servletRequest = originalRequest;
        this.servletResponse = originalResponse;
    }

    public ConvergedHttpServletRequestFacade getConvergedOriginalRequest() {
        return originalRequest;
    }

    public ConvergedHttpServletResponseFacade getConvergedOriginalResponse() {
        return originalResponse;
    }

    @Override
    public ServletResponse getServletResponse() {
        return servletResponse;
    }

    @Override
    public void setServletResponse(ServletResponse servletResponse) {
        super.setServletResponse(servletResponse);
        this.servletResponse = servletResponse;
    }

    @Override
    public ServletRequest getServletRequest() {
        return servletRequest;
    }

    @Override
    public void setServletRequest(ServletRequest servletRequest) {
        super.setServletRequest(servletRequest);
        this.servletRequest = servletRequest;
    }


}
