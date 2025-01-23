package calcChips;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class test {
    public static void main(String[] args) {
        try {
            // Load the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // Enable namespace awareness
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("test-data/PDBSESE174/E142_STRIPMAP_VC339684G09_3138096869_PDBSESE174_20240130T123000+08.xml");

            // Define the namespace URI
            String namespaceURI = "urn:semi-org:xsd.E142-1.V1005.SubstrateMap";

            // Get all SubstrateMap elements
            NodeList substrateMaps = doc.getElementsByTagNameNS(namespaceURI, "SubstrateMap");

            // Iterate over SubstrateMap elements
            for (int i = 0; i < substrateMaps.getLength(); i++) {
                Element substrateMap = (Element) substrateMaps.item(i);

                // Get the SubstrateType attribute
                String substrateType = substrateMap.getAttribute("SubstrateType");
                System.out.println("SubstrateType: " + substrateType);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
