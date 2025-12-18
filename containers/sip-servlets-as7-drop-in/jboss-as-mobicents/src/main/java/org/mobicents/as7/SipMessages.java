package org.mobicents.as7;

import org.jboss.logging.Message;
import org.jboss.logging.MessageBundle;
import org.jboss.logging.Messages;
import org.jboss.vfs.VirtualFile;

/**
 * Date: 05.11.2011
 *
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@MessageBundle(projectCode = "MSS")
public interface SipMessages {

    /**
     * The messages
     */
    SipMessages MESSAGES = Messages.getBundle(SipMessages.class);

    @Message(id = 18002, value = "Failed to get metrics %s")
    String failedToGetMetrics(String reason);

    @Message(id = 18003, value = "No metrics available")
    String noMetricsAvailable();

    @Message(id = 18007, value = "Error starting web connector")
    String connectorStartError();

    @Message(id = 18008, value = "Null service value")
    IllegalStateException nullValue();

    @Message(id = 18009, value = "Error starting sip container")
    String errorStartingSip();
    
    @Message(id = 18026, value = "Failed to resolve module for deployment %s")
    String failedToResolveModule(VirtualFile deploymentRoot);

    @Message(id = 18027, value = "Failed to add Mobicents SIP deployment service")
    String failedToAddSipDeployment();

    @Message(id = 18043, value = "%s has the wrong component type, it cannot be used as a SIP component")
    RuntimeException wrongComponentType(String clazz);

    /**
     * A message indicating the metric is unknown.
     *
     * @param metric the unknown metric.
     *
     * @return the message.
     */
    @Message(id = 18098, value = "Unknown metric %s")
    String unknownMetric(Object metric);

}
