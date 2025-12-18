package org.mobicents.as7;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.PathAddress;
import org.jboss.dmr.ModelNode;

/**
 * Update removing a web connector
 *
 * @author Emanuel Muckenhuber
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
        // TODO:  RE-ADD SERVICES
    }

}
