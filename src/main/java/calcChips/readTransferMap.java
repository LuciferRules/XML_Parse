package calcChips;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class readTransferMap {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        // Load the XML file
        File inputFile = new File("test-data/PDBSESE174/E142_STRIPMAP_VC339684G09_3138096869_PDBSESE174_20240130T123000+08.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        // Get the TransferMap element using XPath
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "//TransferMap";
        Node transferMapNode = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);

        if (transferMapNode != null) {
            Element transferMapElement = (Element) transferMapNode;
//            System.out.println("TransferMap:");
//            System.out.println(transferMapElement.getTextContent());

            // Get the T elements
            NodeList tElements = transferMapElement.getElementsByTagName("T");

            // count the T elements
            int countT = tElements.getLength();
            // print countT
            System.out.println("countT=" + countT );

            for (int i = 0; i < tElements.getLength(); i++) {
                Node tNode = tElements.item(i);
                Element tElement = (Element) tNode;
                System.out.println("T element:");
                System.out.println("FX: " + tElement.getAttribute("FX"));
                System.out.println("FY: " + tElement.getAttribute("FY"));
                System.out.println("TX: " + tElement.getAttribute("TX"));
                System.out.println("TY: " + tElement.getAttribute("TY"));
            }
        } else {
            System.out.println("No TransferMap element found.");
        }

        printDeviceIdMap(xPath, doc);

    }

    public static void printDeviceIdMap (XPath xPath, Document doc) throws XPathExpressionException {
        String expression2 = "//DeviceIdMap";
        Node transferMapNode2 = (Node) xPath.compile(expression2).evaluate(doc, XPathConstants.NODE);
        if (transferMapNode2 != null) {
            Element transferMapElement2 = (Element) transferMapNode2;
            // Get the id elements
            NodeList idElements = transferMapElement2.getElementsByTagName("Id");

            // count id elements
            int countId = idElements.getLength();
            System.out.println("countId total=" + countId);

            for (int j = 0; j < idElements.getLength(); j++){
                Node idNode = idElements.item(j);
                Element idElement = (Element) idNode;
                System.out.println("id element: ");
                System.out.print("X= " + idElement.getAttribute("X"));
                System.out.println();
                System.out.println("Y= " + idElement.getAttribute("Y"));
            }
        }
    }
}
