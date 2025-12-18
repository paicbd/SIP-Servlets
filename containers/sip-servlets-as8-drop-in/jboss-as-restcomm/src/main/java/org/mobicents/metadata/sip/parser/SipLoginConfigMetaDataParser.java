package org.mobicents.metadata.sip.parser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.mobicents.metadata.sip.spec.Attribute;
import org.mobicents.metadata.sip.spec.Element;
import org.mobicents.metadata.sip.spec.SipLoginConfigMetaData;

/**
 * @author Remy Maucherat
 * @author josemrecio@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.parser package from jboss-as7-mobicents project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class SipLoginConfigMetaDataParser extends MetaDataElementParser {

    public static SipLoginConfigMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        SipLoginConfigMetaData loginConfig = new SipLoginConfigMetaData();

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
                    loginConfig.setId(value);
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
                case AUTH_METHOD:
                    loginConfig.setAuthMethod(getElementText(reader));
                    break;
                case REALM_NAME:
                    loginConfig.setRealmName(getElementText(reader));
                    break;
                case IDENTITY_ASSERTION:
                    loginConfig.setIdentityAssertion(IdentityAssertionParser.parse(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return loginConfig;
    }

}
