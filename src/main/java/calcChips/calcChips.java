package calcChips;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class calcChips {

    public static void main(String[] args) {
        // readXML call
        readXML("test-data/PDBSESE174/E142_STRIPMAP_VC339684G09_3138096870_PDBSESE174_20240130T122947+08.xml");
    }

    // readXML method
    public static void readXML(String filePath) {

        try {
            /* Step1: Load the XML file and parse */
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true); // Enable namespace awareness, otherwise cannot read properly
            DocumentBuilder dB = dbFactory.newDocumentBuilder();
            Document doc = dB.parse(filePath);

            /* Step2: Get all SubstrateMap elements */
            // Define the namespace URI
            String namespaceURI = "urn:semi-org:xsd.E142-1.V1005.SubstrateMap";
            // Get all SubstrateMap elements based on semi schema
            NodeList substrateMaps = doc.getElementsByTagNameNS(namespaceURI, "SubstrateMap");

            System.out.println(substrateMaps.getLength());
            // Iterate over SubstrateMap elements
            for (int i = 0; i < substrateMaps.getLength(); i++) {
                Element substrateMap = (Element) substrateMaps.item(i);

                // Get the SubstrateType attribute
                String substrateType = substrateMap.getAttribute("SubstrateType");
                System.out.println("SubstrateType: " + substrateType);

                // Get the SubstrateId attribute
                String substrateId = substrateMap.getAttribute("SubstrateId");
                System.out.println("SubstrateId: " + substrateId);
            }

            /* Step3: Find Wafer id */
            NodeList TransferMap = doc.getElementsByTagNameNS(namespaceURI, "TransferMap");
            System.out.println(TransferMap.getLength());

            // Iterate over TransferMap elements
            for (int i = 0; i < TransferMap.getLength(); i++) {
                Element transferMap = (Element) TransferMap.item(i);
                System.out.println(transferMap.getAttribute("FromSubstrateType")); //Wafer
                System.out.println(transferMap.getAttribute("FromSubstrateId")); //VC339684.05
            }

            /* Step4: Find */
            NodeList T = doc.getElementsByTagNameNS(namespaceURI, "T");
            System.out.println("T length: " + T.getLength());
            // Iterate over TransferMap elements
            for (int i = 0; i < T.getLength(); i++) {
                Element TMap = (Element) T.item(i);
                System.out.print("FX: " + TMap.getAttribute("FX") + " ");
                System.out.print("FY: " + TMap.getAttribute("FY") + " ");
                System.out.print("TX: " + TMap.getAttribute("TX") + " ");
                System.out.print("TY: " + TMap.getAttribute("TY") + " ");
                System.out.println(); //newline
            }

        }
        catch (Exception e) {
            System.out.println("Unexpected error occurred while reading XML file."+ e);
        }


    }
}
