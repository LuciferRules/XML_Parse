import com.example.xmlParser;
import org.junit.Test;


public class xmlParserTest {

    public static final String HAPPY_PATH = "test-data/example.xml";
    public static final String INVALID_PRICE = "test-data/invalid_price.xml";
    public static final String INVALID_PRICE2 = "test-data/invalid_price2.xml";
    public static final String MISSING_TITLE = "test-data/missing_title.xml";
    public static final String INVALID_TITLE = "test-data/invalid_title.xml";

    // test happy path by passing the file path to the readTitle function
    @Test
    public void testHappyPath() throws Exception {
        xmlParser.readTitle(HAPPY_PATH);
    }

    // test invalid price by passing the file path to the readTitle function
    @Test //(expected = NumberFormatException.class)
    public void testInvalidPrice() throws Exception {
        xmlParser.readTitle(INVALID_PRICE);
    }

    // test invalid price by passing the file path to the readTitle function
    @Test //(expected = NumberFormatException.class)
    public void testInvalidPrice2() throws Exception {
        xmlParser.readTitle(INVALID_PRICE2);
    }

    // test missing title by passing the file path to the readTitle function
    @Test
    public void testMissingTitle() throws Exception {
        xmlParser.readTitle(MISSING_TITLE);
    }

    // test invalid title by passing the file path to the readTitle function
    @Test
    public void testInvalidTitle() throws Exception {
        xmlParser.readTitle(INVALID_TITLE);
    }


    // Add more test methods for each test file
}