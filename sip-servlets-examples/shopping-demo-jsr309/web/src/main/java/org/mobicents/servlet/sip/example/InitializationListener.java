/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.servlet.sip.example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author <A HREF="mailto:jean.deruelle@gmail.com">Jean Deruelle</A>
 */
public class InitializationListener {
    private static final String AUDIO_DIR = "/audio";
    private static final String FILE_PROTOCOL = "file://";
    private static final String[] AUDIO_FILES = new String[]{
            "AdminReConfirm.wav", "OrderApproved.wav", "OrderCancelled.wav", "OrderConfirmed.wav",
            "OrderConfirm.wav", "OrderDeliveryDate.wav", "OrderShipped.wav",
            "ReConfirm.wav"};

    Properties properties = null;
    private static final String MS_CONTROL_FACTORY = "MsControlFactory";
    public static final String PR_JNDI_NAME = "media/trunk/PacketRelay/$";

    // Property key for the Unique MGCP stack name for this application
    public static final String MGCP_STACK_NAME = "mgcp.stack.name";
    // Property key for the IP address where CA MGCP Stack (SIP Servlet 
    // Container) is bound 
    public static final String MGCP_STACK_IP = "mgcp.server.address";
    // Property key for the port where CA MGCP Stack is bound 
    public static final String MGCP_STACK_PORT = "mgcp.local.port";
    // Property key for the IP address where MGW MGCP Stack (MMS) is bound 
    public static final String MGCP_PEER_IP = "mgcp.bind.address";
    // Property key for the port where MGW MGCP Stack is bound 
    public static final String MGCP_PEER_PORT = "mgcp.server.port";
    /**
     * In this case MGW and CA are on same local host
     */
    public static final String LOCAL_ADDRESS = System.getProperty(
            "jboss.bind.address", "127.0.0.1");
    protected static final String CA_PORT = "2728";

    public static final String PEER_ADDRESS = System.getProperty(
            "jboss.bind.address", "127.0.0.1");
    protected static final String MGW_PORT = "2427";


    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */

    private void copyToTempDir(InputStream is, File tempWriteDir,
                               String fileName) {
        File file = new File(tempWriteDir, fileName);

        final int bufferSize = 1000;
        BufferedOutputStream fout = null;
        BufferedInputStream fin = null;
        try {
            fout = new BufferedOutputStream(new FileOutputStream(file));
            fin = new BufferedInputStream(is);
            byte[] buffer = new byte[bufferSize];
            int readCount = 0;
            while ((readCount = fin.read(buffer)) != -1) {
                if (readCount < bufferSize) {
                    fout.write(buffer, 0, readCount);
                } else {
                    fout.write(buffer);
                }
            }
        } catch (IOException e) {
            //logger.error("An unexpected exception occured while copying audio files",
            //e);
        } finally {
            try {
                if (fout != null) {
                    fout.flush();
                    fout.close();
                }
            } catch (IOException e) {
                //logger.error("An unexpected exception while closing stream",
                //e);
            }
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                //	logger.error("An unexpected exception while closing stream",
                //e);
            }
        }
    }

}
