package objectivetester;

/**
 *
 * @author Steve
 */
public class DefaultWriter {

    UserInterface ui;
    int footer = 0;
    int n;

    void writeHeader() {
    }

    void writeStart() {
        n++;
        ui.insertCode("\n    @Test\n"
                + "    public void test" + n + "() {", footer);
    }

    void writeAssert(String node, String value) {
        ui.insertCode("\n        //assert:" + node + "=" + value + "\n"
                // some code
                + "        .body(\"" + node + "\", equalTo(\"" + value + "\"))"
                + "", footer + 4);
    }

    void writeGet(String url, int code) {
        ui.insertCode("\n        //get:" + url + "\n"
                + "        given().when().get(\"" + url + "\").then()\n"
                + "        .statusCode(" + code + ");\n"
                + "", footer);
    }

    void writePost(String url, String data, int code) {
        ui.insertCode("\n        //post:" + url + "\n"
                + "        String data = \"" + data + "\";\n"
                + "        given().contentType(\"application/json\")"
                + ".body(data).post(\"" + url + "\").then()\n"
                + "        .statusCode(" + code + ");\n"
                + "", footer);
    }

    void writeDelete(String url, int code) {
        ui.insertCode("\n        //delete:" + url + "\n"
                + "        given().when().delete(\"" + url + "\").then()\n"
                + "        .statusCode(" + code + ");\n"
                + "", footer);
    }

    void writeEnd() {
        ui.insertCode("    }\n", footer);
    }

}
