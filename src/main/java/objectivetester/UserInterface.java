package objectivetester;

/**
 *
 * @author Steve
 */
interface UserInterface {

    void addCode(String fragment);

    void insertCode(String fragment, int above);

    String enterValue(String title);

    void errorMessage(String message);

    void start(Object data);

}
