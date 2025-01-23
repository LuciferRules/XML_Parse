package calcChips;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class calcChips {

    public static void main(String[] args) {
        // readXML call
        readXML("test-data/PDBSESE174/E142_STRIPMAP_VC339684G09_3138096869_PDBSESE174_20240130T123000+08.xml");
    }

    // readXML method
    public static void readXML(String filePath) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            // create a new document builder
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // parse the file and get the document
            Document document = documentBuilder.parse(filePath);

            //
            NodeList SubstrateMaps = document.getElementsByTagName("SubstrateMaps");
            Element SubstrateMap = (Element) SubstrateMaps;
            Node overlay= SubstrateMap.getElementsByTagName("Overlay").item(2);

            String MapName = overlay.getAttributes().toString();
            System.out.println(MapName);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
