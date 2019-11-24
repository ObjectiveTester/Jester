package objectivetester;

/**
 *
 * @author Steve
 */
class TestWriter5 extends DefaultWriter {

    private int n;

    TestWriter5(UserInterface ui) {
        this.n = 0;
        this.ui = ui;
    }

    @Override
    void writeHeader() {

        ui.addCode("import static io.restassured.RestAssured.given;\n"
                + "import static org.hamcrest.Matchers.equalTo;\n"
                + "import org.junit.jupiter.api.AfterAll;\n"
                + "import org.junit.jupiter.api.AfterEach;\n"
                + "import org.junit.jupiter.api.BeforeAll;\n"
                + "import org.junit.jupiter.api.BeforeEach;\n"
                + "import org.junit.jupiter.api.Test;\n"
                + "import static org.junit.jupiter.api.Assertions.*;\n"
                + "import org.junit.jupiter.api.TestMethodOrder;\n"
                + "import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;\n"
                + "import org.junit.jupiter.api.Order;\n\n"
                + "@TestMethodOrder(OrderAnnotation.class)\n"
                + ""
                + "\n"
                + "public class RecordedTest {\n"
                + "\n"
                + "    public RecordedTest() {\n"
                + "    }\n"
                + "\n"
                + "    @BeforeAll\n"
                + "    public static void setUp() {\n"
                + "    }\n"
                + "\n"
                + "    @AfterAll\n"
                + "    public static void tearDown() {\n"
                + "    }\n"
                + "\n"
                + "    @BeforeEach\n"
                + "    public void setUpEach() {\n"
                + "    }\n"
                + "\n"
                + "    @AfterEach\n"
                + "    public void tearDownEach() {\n"
                + "    }\n"
                + "\n}");
        footer = 2; //lines from the insert point to the bottom
    }

    void writeStart() {
        n++;
        ui.insertCode("\n    @Test\n"
                + "    public void test" + n + "() {", footer);
    }

    void writeAssert(String node, String value) {
        System.out.println("writeassert");
        ui.insertCode("\n        //assert:" + value + "\n"
                // some code
                + "        .body(\"" + node + "\", equalTo(\"" + value + "\"))"
                + "", footer + 4);
    }

    void writeGet(String url, int code) {
        ui.insertCode("\n        //get:" + url + "\n"
                + "        given().when().get(\"" + url + "\").then()\n\n\n"
                + "        .statusCode(" + code + ");\n"
                + "", footer);
    }

    void writePost(String url, String data, int code) {
        ui.insertCode("\n        //post:" + url + "\n"
                + "        String data = \"" + data + "\";\n"
                + "        given().contentType(\"application/json\")"
                + ".body(data).post(\"" + url + "\").then()\n\n\n"
                + "        .statusCode(" + code + ");\n"
                + "", footer);
    }

    void writeEnd() {
        ui.insertCode("\n    }\n", footer);
    }
}
