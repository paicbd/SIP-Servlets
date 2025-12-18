package org.mobicents.as10;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.PathAddress;
import org.jboss.dmr.ModelNode;

/**
 * Update removing a web connector
 *
 * @author Emanuel Muckenhuber
 *
 *         This class is based on the contents of org.mobicents.as7 package from jboss-as7-mobicents project, re-implemented for
 *         jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
class SipConnectorRemove extends AbstractRemoveStepHandler {

    static final SipConnectorRemove INSTANCE = new SipConnectorRemove();

    private SipConnectorRemove() {
        //
    }

    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) {
        final PathAddress address = PathAddress.pathAddress(operation.require(OP_ADDR));
        final String name = address.getLastElement().getValue();
        context.removeService(SipSubsystemServices.JBOSS_SIP_CONNECTOR.append(name));
    }

    protected void recoverServices(OperationContext context, ModelNode operation, ModelNode model) {
        // TODO: RE-ADD SERVICES
    }

}
