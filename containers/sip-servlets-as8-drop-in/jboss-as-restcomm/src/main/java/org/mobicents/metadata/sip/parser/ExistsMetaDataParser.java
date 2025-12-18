package org.mobicents.metadata.sip.parser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.mobicents.metadata.sip.spec.ExistsMetaData;
import org.mobicents.metadata.sip.spec.VarMetaData;

/**
 * @author josemrecio@gmail.com
 *
 *         This class is based on the contents of org.mobicents.metadata.sip.parser package from jboss-as7-mobicents project,
 *         re-implemented for jboss as8 (wildfly) by:
 * @author kakonyi.istvan@alerant.hu
 *
 */
public class ExistsMetaDataParser extends MetaDataElementParser {

    public static ExistsMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        ExistsMetaData existsMetaData = new ExistsMetaData();
        VarMetaData varMetaData = VarMetaDataParser.parse(reader);
        existsMetaData.setFromVarMetaData(varMetaData);
        return existsMetaData;
    }

}