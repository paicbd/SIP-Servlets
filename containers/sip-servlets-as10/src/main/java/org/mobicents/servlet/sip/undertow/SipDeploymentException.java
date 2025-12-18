package org.mobicents.servlet.sip.undertow;

/**
 * Exception occuring when something goes wrong with the deployment of the sip application
 *
 * @author jean.deruelle@gmail.com
 *
 *         This class is based on org.mobicents.servlet.sip.catalina.SipDeploymentException from sip-servlet-as7 project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 *
 */
public class SipDeploymentException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public SipDeploymentException() {
        super();
    }

    /**
     * @param message
     */
    public SipDeploymentException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SipDeploymentException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SipDeploymentException(String message, Throwable cause) {
        super(message, cause);
    }

}
