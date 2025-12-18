package org.mobicents.as8;

import org.mobicents.servlet.sip.undertow.SipProtocolHandler;

/**
 * @author kakonyi.istvan@alerant.hu
 */
public class SipConnectorListener {

    private SipProtocolHandler protocolHandler;

    public SipConnectorListener(SipProtocolHandler protocolHandler) {

        this.protocolHandler = protocolHandler;

    }

    public SipProtocolHandler getProtocolHandler() {
        return protocolHandler;
    }

    public void init() {
        try {
            this.protocolHandler.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            this.protocolHandler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        this.protocolHandler.resume();
    }

    public void stop() {
        try {
            this.protocolHandler.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
