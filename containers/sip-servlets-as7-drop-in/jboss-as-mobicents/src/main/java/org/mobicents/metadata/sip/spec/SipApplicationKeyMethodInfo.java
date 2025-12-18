package org.mobicents.metadata.sip.spec;

/**
 * Data to fetch the SipApplicationKey method in the proper class loader context.
 * No need to store the args and return type, they are always the same (String, SipServletRequest)
 *
 * @author josemrecio@gmail.com
 */
public class SipApplicationKeyMethodInfo {
	
	final String className;
	final String methodName;
	
	public SipApplicationKeyMethodInfo(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	
	public String toString() {
		return new StringBuilder(className).append("-").append(methodName).toString();
	}
}