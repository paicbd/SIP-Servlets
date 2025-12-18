package org.mobicents.metadata.sip.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.mobicents.metadata.sip.spec.Attribute;
import org.mobicents.metadata.sip.spec.Element;
import org.mobicents.metadata.sip.spec.SipResourceCollectionMetaData;

/**
 * @author Remy Maucherat
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.parser package from jboss-as7-mobicents project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 */
public class SipResourceCollectionsMetaDataParser extends MetaDataElementParser {

    public static SipResourceCollectionMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        SipResourceCollectionMetaData sipResourceCollection = new SipResourceCollectionMetaData();

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
                    sipResourceCollection.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions)) {
                if (sipResourceCollection.getDescriptions() == null) {
                    sipResourceCollection.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case RESOURCE_NAME:
                    sipResourceCollection.setResourceName(getElementText(reader));
                    break;
                case SIP_METHOD:
                    List<String> sipMethods = sipResourceCollection.getSipMethods();
                    if (sipMethods == null) {
                        sipMethods = new ArrayList<String>();
                        sipResourceCollection.setSipMethods(sipMethods);
                    }
                    sipMethods.add(getElementText(reader));
                    break;
                case SERVLET_NAME:
                    List<String> servletNames = sipResourceCollection.getServletNames();
                    if (servletNames == null) {
                        servletNames = new ArrayList<String>();
                        sipResourceCollection.setServletNames(servletNames);
                    }
                    servletNames.add(getElementText(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return sipResourceCollection;
    }

}