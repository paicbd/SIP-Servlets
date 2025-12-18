package org.mobicents.metadata.sip.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of all the possible XML attributes in the web-app 3.0 schema, by name.
 *
 * @author Remy Maucherat
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.spec package from jboss-as7-mobicents project,
 *         re-implemented for jboss as10 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public enum Attribute {
    // always first
    UNKNOWN(null),
    ID("id"),
    METADATA_COMPLETE("metadata-complete"),
    VERSION("version"),
    IGNORE_CASE("ignore-case"),
    ;

    private final String name;

    Attribute(final String name) {
        this.name = name;
    }

    /**
     * Get the local name of this element.
     *
     * @return the local name
     */
    public String getLocalName() {
        return name;
    }

    private static final Map<String, Attribute> MAP;

    static {
        final Map<String, Attribute> map = new HashMap<String, Attribute>();
        for (Attribute element : values()) {
            final String name = element.getLocalName();
            if (name != null)
                map.put(name, element);
        }
        MAP = map;
    }

    public static Attribute forName(String localName) {
        final Attribute element = MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }
}
