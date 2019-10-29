package objectivetester;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Steve
 */
class EventListener extends MouseAdapter implements ActionListener {
    
    JPopupMenu popup;
    JTree tree;
    UserInterface ui;
    int current;
    TreePath nodePath;
    DefaultMutableTreeNode nodeSelected;
    
    EventListener(JPopupMenu popup, JTree tree, UserInterface ui) {
        this.popup = popup;
        this.tree = tree;
        this.ui = ui;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        clickEvent(e);
        popupEvent(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        popupEvent(e);
    }
    
    private void popupEvent(MouseEvent e) {
        if (e.isPopupTrigger()) {
            //reenable all choices
            popup.getComponent(Const.POP_ASSERT).setEnabled(true); //assert
            popup.getComponent(Const.POP_EDIT).setEnabled(true);  //edit
            popup.getComponent(Const.POP_INS).setEnabled(true);   //insert
            popup.getComponent(Const.POP_DEL).setEnabled(true);   //delete

            nodePath = tree.getPathForLocation(e.getPoint().x, e.getPoint().y);
            if (nodePath != null) {
                //nodeSelected = (DefaultMutableTreeNode) nodePath.getLastPathComponent();
                nodeSelected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            } else {
                nodeSelected = null;
            }

            //show the popup menu
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    
    private void clickEvent(MouseEvent e) {
        nodePath = tree.getPathForLocation(e.getPoint().x, e.getPoint().y);
        if (nodePath != null) {
            nodeSelected = (DefaultMutableTreeNode) nodePath.getLastPathComponent();
        } else {
            nodeSelected = null;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().contentEquals(Const.EDIT)) {
            JsonElement je = (JsonElement) nodeSelected.getUserObject();
            if (je.elementType.equals(Type.ARRAY))  {
                String val = ui.enterValue("new " + je.elementType);
                if (val != null) {
                    je.elementObject = val;
                    nodeSelected.setUserObject(je);
                }
            }
        }
        
        if (e.getActionCommand().contentEquals(Const.ASSERT)) {
            System.out.println(Const.ASSERT + " " + nodePath);
        }
        
        if (e.getActionCommand().contentEquals(Const.INSERT)) {
        }
        
        if (e.getActionCommand().contentEquals(Const.DELETE)) {
            ui.delete(nodeSelected);            
        }
    }
}
