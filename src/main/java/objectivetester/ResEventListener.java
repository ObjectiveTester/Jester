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
class ResEventListener extends MouseAdapter implements ActionListener {

    JPopupMenu popup;
    JTree tree;
    UserInterface ui;
    int current;
    TreePath nodePath;
    DefaultMutableTreeNode nodeSelected;

    ResEventListener(JPopupMenu popup, JTree tree, UserInterface ui) {
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

            nodePath = tree.getPathForLocation(e.getPoint().x, e.getPoint().y);
            if (nodePath != null) {
                nodeSelected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            } else {
                nodeSelected = null;
            }
            //disable choices that are invalid for the item selected
            //for example, empty space or value, etc.

            //block all edit options when nothing selected
            if (nodeSelected == null) {
                popup.getComponent(Const.POP_ASSERT).setEnabled(false); //assert
            }
            //block edit options on root
            if ((nodeSelected != null) && (nodeSelected.isRoot())) {
                popup.getComponent(Const.POP_ASSERT).setEnabled(false); //assert
            }

            //block invalid array operations
            if ((nodeSelected != null) && (!nodeSelected.isRoot())) {
                JsonElement element = (JsonElement) nodeSelected.getUserObject();
                if (element.elementType.equals(Type.ARRAY)) {
                    popup.getComponent(Const.POP_ASSERT).setEnabled(false); //assert
                }
            }
            //block invalid arraykey operations
            if ((nodeSelected != null) && (!nodeSelected.isRoot())) {
                JsonElement element = (JsonElement) nodeSelected.getUserObject();
                if (element.elementType.equals(Type.ARRAYKEY)) {
                    popup.getComponent(Const.POP_ASSERT).setEnabled(false); //assert
                }
            }
            //block assert on key
            if ((nodeSelected != null) && (!nodeSelected.isRoot())) {
                JsonElement element = (JsonElement) nodeSelected.getUserObject();
                if (element.elementType.equals(Type.KEY)) {
                    popup.getComponent(Const.POP_ASSERT).setEnabled(false); //assert
                }
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

        if (e.getActionCommand().contentEquals(Const.ASSERT)) {
            JsonElement element = (JsonElement) nodeSelected.getUserObject();
            String elementPath = "";
            for (int count = 0; count < nodePath.getPath().length; count++) {
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) nodePath.getPath()[count];

                if (!treeNode.isRoot() && count < nodePath.getPath().length - 1) {
                    //not the root or last
                    element = (JsonElement) treeNode.getUserObject();
                    if (element.elementType.equals(Type.ARRAY)) {
                        if (elementPath.length() != 0) {
                            elementPath = elementPath.substring(0, elementPath.length() - 1);
                        }
                        elementPath = elementPath + "[" + element.elementObject.toString() + "]";
                    } else {
                        elementPath = elementPath + element.elementObject.toString();
                    }
                    if (count < nodePath.getPath().length - 2) {
                        elementPath = elementPath + ".";
                    }
                }
            }
            ui.writeAssert(elementPath, nodeSelected.toString());
        }
    }
}
