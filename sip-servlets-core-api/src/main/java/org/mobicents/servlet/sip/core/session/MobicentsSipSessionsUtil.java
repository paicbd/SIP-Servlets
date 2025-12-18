

package org.mobicents.servlet.sip.core.session;

import org.mobicents.javax.servlet.sip.SipSessionsUtilExt;

/**
 * @author jean.deruelle@gmail.com
 *
 */
public interface MobicentsSipSessionsUtil extends SipSessionsUtilExt {

	void addCorrespondingSipSession(MobicentsSipSession sipSessionImpl,
			MobicentsSipSession joinReplacesSipSession, String headerName);

	void addCorrespondingSipApplicationSession(
			MobicentsSipApplicationSessionKey sipApplicationSessionKey,
			MobicentsSipApplicationSessionKey key, String headerName);

	MobicentsSipApplicationSessionKey getCorrespondingSipApplicationSession(
			MobicentsSipApplicationSessionKey sipApplicationSessionKey,
			String name);

	void removeCorrespondingSipApplicationSession(MobicentsSipApplicationSessionKey key);

	void removeCorrespondingSipSession(MobicentsSipSessionKey key);

}
