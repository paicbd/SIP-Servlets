package org.mobicents.servlet.sip.example;

import java.io.Serializable;

/**
 * http://code.google.com/p/sipservlets/issues/detail?id=90
 * 
 * @author jean.deruelle@gmail.com
 *
 */
public class SessionIdPlaceHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
