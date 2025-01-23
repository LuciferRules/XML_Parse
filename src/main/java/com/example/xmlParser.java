package com.example;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

public class xmlParser {

    private static final Logger logger = LogManager.getLogger(xmlParser.class);

    public static void main(String[] args) {
        // You can add code here to call readTitle with a file path
    }

    public static void readTitle(String filePath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try { //Document object that represents the parsed XML file, which contains the book elements and their child nodes (title, author)
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            NodeList books = document.getElementsByTagName("book");
            printTitlesWithPriceGreaterThanTen(books);
        }
//        catch (SAXException | IOException e) {
//            logger.error("Error reading XML file: " + filePath, e);
//        }
        catch (Exception e) {
            logger.error("Unexpected error occurred while reading XML file.", e);
            System.out.println("Unexpected error occurred while reading XML file."+ e);
        }
    }

    private static void printTitlesWithPriceGreaterThanTen(NodeList books) {
        for (int i = 0; i < books.getLength(); i++) {
            Node book = books.item(i);
            if (book.getNodeType() == Node.ELEMENT_NODE) {
                Element bookElement = (Element) book;
                String price = bookElement.getAttribute("price");

                try {
                    if (Float.parseFloat(price) > 10) {
                        System.out.println(bookElement.getElementsByTagName("title").item(0).getTextContent());
                    }
                }
//                catch (NumberFormatException e) {
//                    logger.error("Invalid price format for book: " + bookElement.getElementsByTagName("title").item(0).getTextContent(), e);
//                }
                catch (Exception e) {
                    logger.error("Unexpected error occurred while processing book: " + bookElement.getElementsByTagName("title").item(0).getTextContent(), e);
                    System.out.println("Unexpected error occurred while processing book: " + bookElement.getElementsByTagName("title").item(0).getTextContent() + e);
                }
            }
        }
    }
}
