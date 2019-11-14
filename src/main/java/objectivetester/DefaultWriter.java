package objectivetester;

/**
 *
 * @author Steve
 */
public class DefaultWriter {

    UserInterface ui;
    int footer = 0;

    void writeHeader() {}
    
    void writeStart() {}
    
    void writeEnd() {}
    
    void writeGet(String url, int code) {}
    
    void writePost(String url, String data, int code) {}
    
}
