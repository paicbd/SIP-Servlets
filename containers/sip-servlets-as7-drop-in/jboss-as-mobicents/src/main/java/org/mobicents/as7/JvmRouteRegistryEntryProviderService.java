package org.mobicents.as7;

import org.apache.catalina.Engine;
import org.jboss.as.clustering.registry.Registry;
import org.jboss.as.clustering.registry.Registry.RegistryEntryProvider;
import org.jboss.msc.service.AbstractService;
import org.jboss.msc.value.Value;

/**
 * @author Paul Ferraro
 */
public class JvmRouteRegistryEntryProviderService extends AbstractService<Registry.RegistryEntryProvider<String, Void>> {
    private final Value<SipServer> server;

    public JvmRouteRegistryEntryProviderService(Value<SipServer> server) {
        this.server = server;
    }

    @Override
    public RegistryEntryProvider<String, Void> getValue() {
        return new JvmRouteRegistryEntryProvider((Engine) this.server.getValue().getService().getContainer());
    }

    class JvmRouteRegistryEntryProvider implements Registry.RegistryEntryProvider<String, Void> {
        private final Engine engine;

        JvmRouteRegistryEntryProvider(Engine engine) {
            this.engine = engine;
        }

        @Override
        public String getKey() {
            return this.engine.getJvmRoute();
        }

        @Override
        public Void getValue() {
            return null;
        }
    }
}
