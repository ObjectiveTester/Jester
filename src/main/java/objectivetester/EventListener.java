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
            popup.getComponent(Const.POP_INS_K).setEnabled(true);   //insert key
            popup.getComponent(Const.POP_INS_V).setEnabled(true);   //insert value
            popup.getComponent(Const.POP_DEL).setEnabled(true);   //delete
            popup.getComponent(Const.POP_REF).setEnabled(true);   //refresh

            nodePath = tree.getPathForLocation(e.getPoint().x, e.getPoint().y);
            if (nodePath != null) {
                //nodeSelected = (DefaultMutableTreeNode) nodePath.getLastPathComponent();
                nodeSelected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            } else {
                nodeSelected = null;
            }
            //disable choices that are invalid for the item selected
            //for example, empty space or value, etc.
            //

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
            if (nodeSelected != null && !nodeSelected.isRoot()) {
                JsonElement element = (JsonElement) nodeSelected.getUserObject();
                if (!element.elementType.equals(Type.ARRAY)) {
                    String val = ui.enterValue("new " + element.elementObject.getClass().getSimpleName());
                    if (val != null) {
                        if (element.elementObject.getClass() == Integer.class) {
                            element.elementObject = Integer.parseInt(val);
                        } else if (element.elementObject.getClass() == Boolean.class) {
                            element.elementObject = Boolean.valueOf(val);
                        } else if (element.elementObject.getClass() == Double.class) {
                            element.elementObject = Double.parseDouble(val);
                        } else {
                            element.elementObject = (String) val;
                        }
                        nodeSelected.setUserObject(element);

                    }
                }
            }
        }

        if (e.getActionCommand().contentEquals(Const.ASSERT)) {
            if (nodeSelected != null && !nodeSelected.isRoot()) {
                JsonElement element = (JsonElement) nodeSelected.getUserObject();
                System.out.println(Const.ASSERT + " " + nodePath + " " + element.elementObject.getClass().getCanonicalName());
            }
        }

        if (e.getActionCommand().contentEquals(Const.INSERTK)) {
            if (nodeSelected != null) {
                String val = ui.enterValue("new key");
                if (val != null) {
                    DefaultMutableTreeNode objectnode = new DefaultMutableTreeNode(new JsonElement(val, Type.KEY));
                    nodeSelected.add(objectnode);
                }
            }
        }

        if (e.getActionCommand().contentEquals(Const.INSERTV)) {
            //do a lot more checking here
            //do not add if key already has a value
            //
            if (nodeSelected != null) {
                Object rawVal = null;
                String val = ui.enterValue("new value");
                if (val != null) {

                    //integer?
                    try {
                        Integer intVal = Integer.parseInt(val);
                        rawVal = intVal;
                    } catch (NumberFormatException nfe) {
                    }

                    //double?
                    if (rawVal == null) {
                        try {
                            Double dblVal = Double.parseDouble(val);
                            rawVal = dblVal;
                        } catch (NumberFormatException nfe) {
                        }
                    }

                    //boolean or string?
                    if (rawVal == null) {
                        if ((val.equals("true")) || (val.equals("false"))) {
                            rawVal = Boolean.valueOf(val);
                        } else {
                            rawVal = val;
                        }
                    }

                    DefaultMutableTreeNode objectnode = new DefaultMutableTreeNode(new JsonElement(rawVal, Type.VALUE));

                    nodeSelected.add(objectnode);
                }
            }
        }

        if (e.getActionCommand().contentEquals(Const.DELETE)) {
            if (nodeSelected != null && !nodeSelected.isRoot()) {
                ui.delete(nodeSelected);
            }
        }

        if (e.getActionCommand().contentEquals(Const.REFRESH)) {
            ui.refresh();
        }
    }
}
