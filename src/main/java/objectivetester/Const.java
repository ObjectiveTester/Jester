package objectivetester;

/**
 *
 * @author Steve
 */
interface Const {

    //these are used internally to denote action
    static final String ASSERT = "assert";
    
    static final String EDIT = "edit";
    
    static final String INSERT = "insert";
    
    static final String DELETE = "delete";
    
    static final String REFRESH = "refresh";
    
    //max text to put in a dialog box
    static final int MAX_SIZ = 2048;

    static final int POP_ASSERT = 0;

    static final int POP_EDIT = 1;
    
    static final int POP_INS = 2;
    
    static final int POP_DEL = 3;
    
    static final int POP_REF = 4;
}
