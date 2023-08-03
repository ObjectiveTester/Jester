package objectivetester;

/**
 *
 * @author Steve
 */
class TestWriterNG extends DefaultWriter {

    TestWriterNG(UserInterface ui) {
        this.n = 0;
        this.ui = ui;
    }

    @Override
    void writeHeader() {

        ui.addCode("import static io.restassured.RestAssured.given;\n"
                + "import io.restassured.filter.cookie.CookieFilter;\n"
                + "import static org.hamcrest.Matchers.equalTo;\n"
                + "import static org.testng.Assert.*;\n"
                + "import org.testng.ITestResult;\n"
                + "import org.testng.annotations.AfterClass;\n"
                + "import org.testng.annotations.AfterMethod;\n"
                + "import org.testng.annotations.AfterTest;\n"
                + "import org.testng.annotations.BeforeClass;\n"
                + "import org.testng.annotations.BeforeMethod;\n"
                + "import org.testng.annotations.BeforeTest;\n" 
                + "import org.testng.annotations.Test;\n"

                + ""
                + "\n"
                + "public class RecordedTest {\n"
                + "\n"
                + "    CookieFilter cookieFilter = new CookieFilter();\n"
                + "\n"
                + "    public RecordedTest() {\n"
                + "    }\n"
                + "\n"
                + "    @BeforeClass\n"
                + "    public static void setUpClass() {\n"
                + "    }\n"
                + "\n"
                + "    @AfterClass\n"
                + "    public static void tearDownClass() {\n"
                + "    }\n"
                + "\n"
                + "    @BeforeMethod\n"
                + "    public void setUp() {\n"
                + "    }\n"
                + "\n"
                + "    @AfterMethod\n"
                + "    public void tearDown() {\n"
                + "    }\n"
                + "\n}");
        footer = 2; //lines from the insert point to the bottom
    }
}
