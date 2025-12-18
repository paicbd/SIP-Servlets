package org.mobicents.metadata.sip.parser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.mobicents.metadata.sip.spec.Attribute;
import org.mobicents.metadata.sip.spec.Element;
import org.mobicents.metadata.sip.spec.IdentityAssertionMetaData;

/**
 * @author josemrecio@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.parser package from jboss-as7-mobicents project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class IdentityAssertionParser extends MetaDataElementParser {

    public static IdentityAssertionMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        IdentityAssertionMetaData idAssertionConfig = new IdentityAssertionMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    idAssertionConfig.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case IDENTITY_ASSERTION_SCHEME:
                    idAssertionConfig.setIdentityAssertionScheme(getElementText(reader));
                    break;
                case IDENTITY_ASSERTION_SUPPORT:
                    idAssertionConfig.setIdentityAssertionSupport(getElementText(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return idAssertionConfig;
    }

}
