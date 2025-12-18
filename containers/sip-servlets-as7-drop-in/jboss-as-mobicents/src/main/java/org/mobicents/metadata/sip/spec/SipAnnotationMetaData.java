package org.mobicents.metadata.sip.spec;

import java.util.HashMap;

import org.jboss.as.server.deployment.AttachmentKey;

/**
 *
 * @author josemrecio@gmail.com
 *
 */
public class SipAnnotationMetaData extends HashMap<String, SipMetaData> {
    private static final long serialVersionUID = 1L;

    public static final AttachmentKey<SipAnnotationMetaData> ATTACHMENT_KEY = AttachmentKey.create(SipAnnotationMetaData.class);

    // http://code.google.com/p/sipservlets/issues/detail?id=168
	// When no sip.xml but annotations only, Application is not recognized as SIP App by AS7
    private boolean sipApplicationAnnotationPresent = false;

	/**
	 * @return the sipApplicationAnnotationPresent
	 */
	public boolean isSipApplicationAnnotationPresent() {
		return sipApplicationAnnotationPresent;
	}

	/**
	 * @param sipApplicationAnnotationPresent the sipApplicationAnnotationPresent to set
	 */
	public void setSipApplicationAnnotationPresent(
			boolean sipApplicationAnnotationPresent) {
		this.sipApplicationAnnotationPresent = sipApplicationAnnotationPresent;
	}
    
    
}
