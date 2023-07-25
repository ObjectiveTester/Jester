package objectivetester;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Steve
 */
interface UserInterface {

    void addCode(String fragment);

    void insertCode(String fragment, int above);

    String enterValue(String title);

    void errorMessage(String message);

    boolean askQuestion(String questionText);

    void delete(DefaultMutableTreeNode target);

    void wipeRes();

    void wipeReq();

    void refresh();

    void update();

    void copyRes(DefaultMutableTreeNode source, DefaultMutableTreeNode target);

    boolean getOption1();

    void writeAssert(String node, String value);

}
