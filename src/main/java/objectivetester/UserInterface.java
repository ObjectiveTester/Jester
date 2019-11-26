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
    
    void edit(DefaultMutableTreeNode target, String value);
    
    void wipe();
    
    void refresh();
    
    boolean getIgnorePref();
    
    void writeAssert(String node, String value);
    
}
