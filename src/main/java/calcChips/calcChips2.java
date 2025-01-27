package calcChips;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class calcChips2 {

    public static void main(String[] args) {
        // Read and process the XML files
        readXML("test-data/PDBSESE174/E142_STRIPMAP_VC339684G09_3138096870_PDBSESE174_20240130T122947+08.xml");
        System.out.println("-".repeat(30));
        readXML("test-data/PDBSESE174/E142_STRIPMAP_VC339684G09_3138096869_PDBSESE174_20240130T123000+08.xml");
    }

    public static void readXML(String filePath) {
        try {
            // Read the XML file content into a string
            StringBuilder content = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line.trim());
                }
            }

            // Process SubstrateMap elements
            System.out.println("SubstrateMap elements:");
            processElements(content.toString(), "SubstrateMap", "SubstrateType", "SubstrateId");

            // Process TransferMap elements
            System.out.println("TransferMap elements:");
            processElements(content.toString(), "TransferMap", "FromSubstrateType", "FromSubstrateId");

            // Process T elements
            System.out.println("T elements:");
            String[] tElements = findElements(content.toString(), "T");
            System.out.println("T length: " + tElements.length);
            for (String element : tElements) {
                String fx = extractAttribute(element, "FX");
                String fy = extractAttribute(element, "FY");
                String tx = extractAttribute(element, "TX");
                String ty = extractAttribute(element, "TY");
                System.out.printf("FX: %s FY: %s TX: %s TY: %s%n", fx, fy, tx, ty);
            }

            // Find unique TX and TY combinations
            findUniqueTxTy(tElements);

        } catch (IOException e) {
            System.out.println("Error reading the XML file: " + e.getMessage());
        }
    }

    private static void processElements(String content, String tagName, String attr1, String attr2) {
        String[] elements = findElements(content, tagName);
        for (String element : elements) {
            String value1 = extractAttribute(element, attr1);
            String value2 = extractAttribute(element, attr2);
            System.out.printf("%s: %s, %s: %s%n", attr1, value1, attr2, value2);
        }
    }

    private static String[] findElements(String content, String tagName) {
        String startTag = "<" + tagName;
        String endTag = "/>";
        String[] parts = content.split(startTag);
        String[] elements = new String[parts.length - 1];

        for (int i = 1; i < parts.length; i++) {
            int endIndex = parts[i].indexOf(endTag) + endTag.length();
            elements[i - 1] = startTag + parts[i].substring(0, endIndex);
        }
        return elements;
    }

    private static String extractAttribute(String element, String attributeName) {
        String attribute = attributeName + "=\"";
        int start = element.indexOf(attribute);
        if (start == -1) {
            return null; // Attribute not found
        }
        start += attribute.length();
        int end = element.indexOf("\"", start);
        return element.substring(start, end);
    }

    private static void findUniqueTxTy(String[] tElements) {
        HashMap<String, Integer> uniqueCombinations = new HashMap<>();

        for (String element : tElements) {
            String tx = extractAttribute(element, "TX");
            String ty = extractAttribute(element, "TY");
            if (tx != null && ty != null) {
                String key = tx + "_" + ty;
                uniqueCombinations.put(key, uniqueCombinations.getOrDefault(key, 0) + 1);
            }
        }

        System.out.println("Unique TX and TY combinations:");
        for (String key : uniqueCombinations.keySet()) {
            String[] parts = key.split("_");
            System.out.printf("TX: %s, TY: %s, Count: %d%n", parts[0], parts[1], uniqueCombinations.get(key));
        }
        System.out.printf("Total unique TX and TY combinations: %d%n", uniqueCombinations.size());
    }
}
