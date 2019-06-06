package objectivetester;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Steve
 */
public class Jester extends javax.swing.JFrame implements UserInterface {

    //JSON Tester
    //based on the Wisard code, hence all the mess...
    

    private ImageIcon icon;
    DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    JTree jTree;
    int arrayIndex = 0;
    Boolean array = false;
    private final JPopupMenu popup;
    private final APIConnector apiCon = new APIConnector(this);
    private final Preferences prefs;

    /**
     * Creates new form
     */
    public Jester() {
        icon = new ImageIcon(getClass().getResource("/images/wisard.png"));

        rootNode = new DefaultMutableTreeNode("JSON");
        treeModel = new DefaultTreeModel(rootNode);
        jTree = new JTree(treeModel);

        initComponents();
        paneTree.setVisible(true);

        //redirect output to text area
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                textConsole.append(String.valueOf((char) b));
                textConsole.setCaretPosition(textConsole.getDocument().getLength());
            }
        });
        System.setOut(printStream);
        System.setErr(printStream);

        //create the popup menu for the element table
        popup = new JPopupMenu();
        //MouseListener popupListener = new EventListener(popup, tableElements, apiCon);

        JMenuItem menuItem = new JMenuItem(Const.CLICK);
        //menuItem.addActionListener((ActionListener) popupListener);
        popup.add(menuItem);
        menuItem = new JMenuItem(Const.FIND);
        //menuItem.addActionListener((ActionListener) popupListener);
        popup.add(menuItem);
        menuItem = new JMenuItem(Const.ASSERT);
        //menuItem.addActionListener((ActionListener) popupListener);
        popup.add(menuItem);
        menuItem = new JMenuItem(Const.IDENTIFY);
        //menuItem.addActionListener((ActionListener) popupListener);
        popup.add(menuItem);
        //tableElements.addMouseListener(popupListener);
        //hide the webElements
        //tableElements.removeColumn(tableElements.getColumn("webElement"));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });

        //get prefs
        prefs = Preferences.userRoot().node("jester");
        if (prefs.get("serviceURI", "").contentEquals("")) {
            //System.out.println("no prefs, writing defaults");
            //prefs.put("browser", "FF");
            //prefs.put("output", "junit");
            //prefs.put("driverFF", "./geckodriver.exe");
            //prefs.put("driverCR", "./chromedriver.exe");
            //prefs.put("driverIE", "./IEDriverServer.exe");
            //prefs.put("driverED", "./MicrosoftWebDriver.exe");
            //prefs.putBoolean("showId", false);
            //prefs.putBoolean("showInvis", false);
            prefs.put("serviceURI", "https://api.github.com/users/objectivetester");
        }
        //pathFF.setText(prefs.get("driverFF", ""));
        //pathCR.setText(prefs.get("driverCR", ""));
        //pathIE.setText(prefs.get("driverIE", ""));
        //pathED.setText(prefs.get("driverED", ""));
        currentURI.setText(prefs.get("serviceURI", ""));
        serviceURI.setText((prefs.get("serviceURI", "")));
        if (prefs.get("browser", "").contentEquals("FF")) {
            buttonFF.setSelected(true);
        }
        if (prefs.get("browser", "").contentEquals("CR")) {
            buttonCR.setSelected(true);
        }
        if (prefs.get("browser", "").contentEquals("IE")) {
            buttonIE.setSelected(true);
        }
        if (prefs.get("browser", "").contentEquals("ED")) {
            buttonED.setSelected(true);
        }
        if (prefs.get("browser", "").contentEquals("SA")) {
            buttonSA.setSelected(true);
        }
        if (prefs.getBoolean("showId", false)) {
            checkBoxId.setSelected(true);
        } else {
            //hide id column
            //tableElements.removeColumn(tableElements.getColumn("id"));
        }
        if (prefs.get("output", "").contentEquals("java")) {
            buttonJava.setSelected(true);
        }
        if (prefs.get("output", "").contentEquals("junit")) {
            buttonJunit.setSelected(true);
        }
        if (prefs.get("output", "").contentEquals("junit5")) {
            buttonJunit5.setSelected(true);
        }
        if (prefs.get("output", "").contentEquals("js")) {
            buttonJs.setSelected(true);
        }
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonsBrowser = new javax.swing.ButtonGroup();
        buttonsOutput = new javax.swing.ButtonGroup();
        dialogAbout = new javax.swing.JDialog();
        panelAbout = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        labelDesc = new javax.swing.JLabel();
        labelCopyright = new javax.swing.JLabel();
        labelLink = new javax.swing.JLabel();
        buttonCool = new javax.swing.JButton();
        dialogSettings = new javax.swing.JDialog();
        panelSettings = new javax.swing.JPanel();
        labelDefuri = new javax.swing.JLabel();
        serviceURI = new javax.swing.JTextField();
        labelDispopts = new javax.swing.JLabel();
        checkBoxId = new javax.swing.JCheckBox();
        checkBoxInvis = new javax.swing.JCheckBox();
        labelOutput = new javax.swing.JLabel();
        buttonJunit = new javax.swing.JRadioButton();
        buttonJunit5 = new javax.swing.JRadioButton();
        buttonJava = new javax.swing.JRadioButton();
        buttonJs = new javax.swing.JRadioButton();
        labelDriver = new javax.swing.JLabel();
        buttonFF = new javax.swing.JRadioButton();
        buttonCR = new javax.swing.JRadioButton();
        buttonIE = new javax.swing.JRadioButton();
        buttonED = new javax.swing.JRadioButton();
        buttonSA = new javax.swing.JRadioButton();
        pathFF = new javax.swing.JTextField();
        pathCR = new javax.swing.JTextField();
        pathIE = new javax.swing.JTextField();
        labelPlugin = new javax.swing.JLabel();
        buttonSave = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        pathED = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        labelUri = new javax.swing.JLabel();
        currentURI = new javax.swing.JTextField();
        buttonGET = new javax.swing.JButton();
        buttonPOST = new javax.swing.JButton();
        buttonDELETE = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        paneTree = new javax.swing.JScrollPane(jTree);
        paneCode = new javax.swing.JScrollPane();
        code = new javax.swing.JTextArea();
        paneConsole = new javax.swing.JScrollPane();
        textConsole = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuSettings = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuAbout = new javax.swing.JMenuItem();

        dialogAbout.setTitle("About");
        dialogAbout.setIconImage(icon.getImage());
        dialogAbout.setMinimumSize(new java.awt.Dimension(400, 400));
        dialogAbout.setModal(true);

        panelAbout.setLayout(new java.awt.GridBagLayout());

        labelName.setFont(new java.awt.Font("Tahoma", 0, 72)); // NOI18N
        labelName.setText("Jester");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panelAbout.add(labelName, gridBagConstraints);

        labelDesc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelDesc.setText("JSON Test Generator");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelAbout.add(labelDesc, gridBagConstraints);

        labelCopyright.setText("© Steve Mellor 2019");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelAbout.add(labelCopyright, gridBagConstraints);

        labelLink.setText("<html> <a href=\"https://github.com/objectivetester/jester\">Jester on github</a></html>");
        labelLink.setToolTipText("");
        labelLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelLinkMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelLinkMouseEntered(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        panelAbout.add(labelLink, gridBagConstraints);

        buttonCool.setText("Cool");
        buttonCool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCoolActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 0, 0);
        panelAbout.add(buttonCool, gridBagConstraints);

        javax.swing.GroupLayout dialogAboutLayout = new javax.swing.GroupLayout(dialogAbout.getContentPane());
        dialogAbout.getContentPane().setLayout(dialogAboutLayout);
        dialogAboutLayout.setHorizontalGroup(
            dialogAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogAboutLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelAbout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        dialogAboutLayout.setVerticalGroup(
            dialogAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogAboutLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelAbout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        dialogSettings.setTitle("Settings");
        dialogSettings.setIconImage(icon.getImage());
        dialogSettings.setMinimumSize(new java.awt.Dimension(500, 500));
        dialogSettings.setModal(true);

        panelSettings.setLayout(new java.awt.GridBagLayout());

        labelDefuri.setText("Default URI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        panelSettings.add(labelDefuri, gridBagConstraints);

        serviceURI.setColumns(20);
        serviceURI.setText("defaultURI");
        serviceURI.setToolTipText("Default URI");
        serviceURI.setMinimumSize(new java.awt.Dimension(166, 20));
        serviceURI.setName(""); // NOI18N
        serviceURI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceURIActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSettings.add(serviceURI, gridBagConstraints);
        serviceURI.getAccessibleContext().setAccessibleName("Default URI");

        labelDispopts.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelDispopts.setText("Display Options");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panelSettings.add(labelDispopts, gridBagConstraints);

        checkBoxId.setText("Show Element 'id'");
        checkBoxId.setToolTipText("Show Element 'id'");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        panelSettings.add(checkBoxId, gridBagConstraints);
        checkBoxId.getAccessibleContext().setAccessibleDescription("Show id");

        checkBoxInvis.setText("List invisible Elements");
        checkBoxInvis.setToolTipText("List invisible Elements");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        panelSettings.add(checkBoxInvis, gridBagConstraints);
        checkBoxInvis.getAccessibleContext().setAccessibleDescription("List Invisible");

        labelOutput.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelOutput.setText("Generated Output");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panelSettings.add(labelOutput, gridBagConstraints);

        buttonsOutput.add(buttonJunit);
        buttonJunit.setText("JUnit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelSettings.add(buttonJunit, gridBagConstraints);

        buttonsOutput.add(buttonJunit5);
        buttonJunit5.setText("JUnit5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelSettings.add(buttonJunit5, gridBagConstraints);

        buttonsOutput.add(buttonJava);
        buttonJava.setText("Java");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelSettings.add(buttonJava, gridBagConstraints);

        buttonsOutput.add(buttonJs);
        buttonJs.setText("JavaScript");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelSettings.add(buttonJs, gridBagConstraints);

        labelDriver.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelDriver.setText("Target Browser and driver");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 20);
        panelSettings.add(labelDriver, gridBagConstraints);

        buttonsBrowser.add(buttonFF);
        buttonFF.setText("Firefox");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        panelSettings.add(buttonFF, gridBagConstraints);

        buttonsBrowser.add(buttonCR);
        buttonCR.setText("Chrome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelSettings.add(buttonCR, gridBagConstraints);

        buttonsBrowser.add(buttonIE);
        buttonIE.setText("Internet Explorer");
        buttonIE.setToolTipText("Internet Explorer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelSettings.add(buttonIE, gridBagConstraints);
        buttonIE.getAccessibleContext().setAccessibleDescription("IE");

        buttonsBrowser.add(buttonED);
        buttonED.setText("Edge");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelSettings.add(buttonED, gridBagConstraints);

        buttonsBrowser.add(buttonSA);
        buttonSA.setText("Safari");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelSettings.add(buttonSA, gridBagConstraints);

        pathFF.setColumns(20);
        pathFF.setText("pathFF");
        pathFF.setToolTipText("Path to Gecko driver");
        pathFF.setName(""); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSettings.add(pathFF, gridBagConstraints);

        pathCR.setColumns(20);
        pathCR.setText("pathCR");
        pathCR.setToolTipText("Path to Chrome driver");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSettings.add(pathCR, gridBagConstraints);
        pathCR.getAccessibleContext().setAccessibleName("Chrome driver");

        pathIE.setColumns(20);
        pathIE.setText("pathIE");
        pathIE.setToolTipText("Path to IE driver");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSettings.add(pathIE, gridBagConstraints);
        pathIE.getAccessibleContext().setAccessibleName("IE driver");

        labelPlugin.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelPlugin.setText("/usr/bin/safaridriver");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        panelSettings.add(labelPlugin, gridBagConstraints);

        buttonSave.setText("Save");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panelSettings.add(buttonSave, gridBagConstraints);

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panelSettings.add(buttonCancel, gridBagConstraints);

        pathED.setColumns(20);
        pathED.setText("pathED");
        pathED.setToolTipText("Path to Edge driver");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSettings.add(pathED, gridBagConstraints);

        javax.swing.GroupLayout dialogSettingsLayout = new javax.swing.GroupLayout(dialogSettings.getContentPane());
        dialogSettings.getContentPane().setLayout(dialogSettingsLayout);
        dialogSettingsLayout.setHorizontalGroup(
            dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 400, Short.MAX_VALUE))
        );
        dialogSettingsLayout.setVerticalGroup(
            dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
            .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Jester");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(icon.getImage());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        labelUri.setText("URI");
        jToolBar1.add(labelUri);

        currentURI.setColumns(15);
        currentURI.setText("URI");
        currentURI.setToolTipText("Service URI");
        currentURI.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        currentURI.setMinimumSize(new java.awt.Dimension(6, 15));
        currentURI.setPreferredSize(new java.awt.Dimension(12, 20));
        currentURI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentURIActionPerformed(evt);
            }
        });
        jToolBar1.add(currentURI);
        currentURI.getAccessibleContext().setAccessibleName("URI");
        currentURI.getAccessibleContext().setAccessibleDescription("Service URI");

        buttonGET.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/inspect.png"))); // NOI18N
        buttonGET.setToolTipText("GET");
        buttonGET.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonGET.setFocusable(false);
        buttonGET.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonGET.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonGET.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGETActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonGET);

        buttonPOST.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        buttonPOST.setFocusable(false);
        buttonPOST.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPOST.setPreferredSize(new java.awt.Dimension(28, 28));
        buttonPOST.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(buttonPOST);

        buttonDELETE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel.png"))); // NOI18N
        buttonDELETE.setFocusable(false);
        buttonDELETE.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonDELETE.setPreferredSize(new java.awt.Dimension(28, 28));
        buttonDELETE.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(buttonDELETE);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        paneTree.setPreferredSize(new java.awt.Dimension(300, 300));
        jSplitPane1.setLeftComponent(paneTree);

        paneCode.setPreferredSize(new java.awt.Dimension(300, 300));

        code.setEditable(false);
        code.setColumns(20);
        code.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        code.setRows(5);
        code.setTabSize(4);
        paneCode.setViewportView(code);

        jSplitPane1.setRightComponent(paneCode);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        textConsole.setEditable(false);
        textConsole.setColumns(20);
        textConsole.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        textConsole.setRows(5);
        paneConsole.setViewportView(textConsole);

        getContentPane().add(paneConsole, java.awt.BorderLayout.PAGE_END);

        menuFile.setText("File");

        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        menuFile.add(menuExit);

        menuBar.add(menuFile);

        menuEdit.setText("Edit");

        menuSettings.setText("Settings");
        menuSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSettingsActionPerformed(evt);
            }
        });
        menuEdit.add(menuSettings);

        menuBar.add(menuEdit);

        menuHelp.setText("Help");

        menuAbout.setText("About");
        menuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuAbout);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void currentURIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentURIActionPerformed
        buttonGET.doClick();
    }//GEN-LAST:event_currentURIActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed

        //update UI
        currentURI.setText(serviceURI.getText());

        if (!prefs.getBoolean("showId", false) && checkBoxId.isSelected()) {
            //reset table to show id

            //re-hide webElements
        }
        if (prefs.getBoolean("showId", false) && !checkBoxId.isSelected()) {
            //hide id column

        }

        //save new settings
        prefs.put("serviceURI", serviceURI.getText());

        prefs.putBoolean("showId", checkBoxId.isSelected());
        prefs.putBoolean("showInvis", checkBoxInvis.isSelected());

        if (buttonFF.isSelected()) {
            prefs.put("browser", "FF");
        }
        if (buttonIE.isSelected()) {
            prefs.put("browser", "IE");
        }
        if (buttonED.isSelected()) {
            prefs.put("browser", "ED");
        }
        if (buttonCR.isSelected()) {
            prefs.put("browser", "CR");
        }
        if (buttonSA.isSelected()) {
            prefs.put("browser", "SA");
        }
        if (buttonJunit.isSelected()) {
            prefs.put("output", "junit");
        }
        if (buttonJunit5.isSelected()) {
            prefs.put("output", "junit5");
        }
        if (buttonJava.isSelected()) {
            prefs.put("output", "java");
        }
        if (buttonJs.isSelected()) {
            prefs.put("output", "js");
        }

        prefs.put("driverFF", pathFF.getText());
        prefs.put("driverCR", pathCR.getText());
        prefs.put("driverIE", pathIE.getText());
        prefs.put("driverED", pathED.getText());

        dialogSettings.setVisible(false);
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonGETActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGETActionPerformed
        rootNode.removeAllChildren(); //this removes all nodes
        treeModel.reload(); //this notifies the listeners and changes the GUI
        System.out.println("GET " + currentURI.getText());
        System.out.println(apiCon.reqGet(currentURI.getText()));
    }//GEN-LAST:event_buttonGETActionPerformed

    private void labelLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelLinkMouseClicked
        // TODO add your handling code here:
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/objectivetester/jester"));
            } catch (URISyntaxException | IOException | UnsupportedOperationException ex) {
                this.errorMessage("Unable to open browser, please visit:\n https://github.com/objectivetester/jester");
            }
        } else {
            this.errorMessage("Unable to open browser, please visit:\n https://github.com/objectivetester/jester");
        }
    }//GEN-LAST:event_labelLinkMouseClicked

    private void labelLinkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelLinkMouseEntered
        // TODO add your handling code here:
        labelLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_labelLinkMouseEntered

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSettingsActionPerformed
        // TODO add your handling code here:
        dialogSettings.setVisible(true);
    }//GEN-LAST:event_menuSettingsActionPerformed

    private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
        // TODO add your handling code here:
        dialogAbout.setVisible(true);
    }//GEN-LAST:event_menuAboutActionPerformed

    private void buttonCoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCoolActionPerformed
        // TODO add your handling code here:
        dialogAbout.setVisible(false);
    }//GEN-LAST:event_buttonCoolActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        // TODO add your handling code here:
        //restore original settings
        serviceURI.setText((prefs.get("serviceURI", "")));
        checkBoxId.setSelected(prefs.getBoolean("showId", true));
        checkBoxInvis.setSelected(prefs.getBoolean("showInvis", true));

        if (prefs.get("browser", "").contentEquals("FF")) {
            buttonFF.setSelected(true);
        }
        if (prefs.get("browser", "").contentEquals("CR")) {
            buttonCR.setSelected(true);
        }
        if (prefs.get("browser", "").contentEquals("IE")) {
            buttonIE.setSelected(true);
        }
        if (prefs.get("browser", "").contentEquals("ED")) {
            buttonED.setSelected(true);
        }
        if (prefs.get("browser", "").contentEquals("SA")) {
            buttonSA.setSelected(true);
        }

        pathFF.setText(prefs.get("driverFF", ""));
        pathCR.setText(prefs.get("driverCR", ""));
        pathIE.setText(prefs.get("driverIE", ""));
        pathED.setText(prefs.get("driverED", ""));

        dialogSettings.setVisible(false);
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void serviceURIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceURIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviceURIActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">

        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Jester().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton buttonCR;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonCool;
    private javax.swing.JButton buttonDELETE;
    private javax.swing.JRadioButton buttonED;
    private javax.swing.JRadioButton buttonFF;
    private javax.swing.JButton buttonGET;
    private javax.swing.JRadioButton buttonIE;
    private javax.swing.JRadioButton buttonJava;
    private javax.swing.JRadioButton buttonJs;
    private javax.swing.JRadioButton buttonJunit;
    private javax.swing.JRadioButton buttonJunit5;
    private javax.swing.JButton buttonPOST;
    private javax.swing.JRadioButton buttonSA;
    private javax.swing.JButton buttonSave;
    private javax.swing.ButtonGroup buttonsBrowser;
    private javax.swing.ButtonGroup buttonsOutput;
    private javax.swing.JCheckBox checkBoxId;
    private javax.swing.JCheckBox checkBoxInvis;
    private javax.swing.JTextArea code;
    private javax.swing.JTextField currentURI;
    private javax.swing.JDialog dialogAbout;
    private javax.swing.JDialog dialogSettings;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel labelCopyright;
    private javax.swing.JLabel labelDefuri;
    private javax.swing.JLabel labelDesc;
    private javax.swing.JLabel labelDispopts;
    private javax.swing.JLabel labelDriver;
    private javax.swing.JLabel labelLink;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelOutput;
    private javax.swing.JLabel labelPlugin;
    private javax.swing.JLabel labelUri;
    private javax.swing.JMenuItem menuAbout;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuSettings;
    private javax.swing.JScrollPane paneCode;
    private javax.swing.JScrollPane paneConsole;
    private javax.swing.JScrollPane paneTree;
    private javax.swing.JPanel panelAbout;
    private javax.swing.JPanel panelSettings;
    private javax.swing.JTextField pathCR;
    private javax.swing.JTextField pathED;
    private javax.swing.JTextField pathFF;
    private javax.swing.JTextField pathIE;
    private javax.swing.JTextField serviceURI;
    private javax.swing.JTextArea textConsole;
    // End of variables declaration//GEN-END:variables

    private void exitProcedure() {
        apiCon.close();
        this.dispose();
        System.exit(0);
    }

    @Override
    public void addItem(String element, Object stack, String name, String id, String value, Object webElement, Boolean displayed) {

    }

    @Override
    public void abort() {
        //triggers a UI cleanup
        this.errorMessage("Unexpected error!");
        //buttonGet.doClick();
    }

    @Override
    public void addCode(String fragment) {
        //adds content to the generated code
        code.setText(fragment);
    }

    @Override
    public void insertCode(String fragment, int above) {
        //inserts content to the generated code
        int point;
        try {
            point = code.getLineEndOffset(code.getLineCount() - above);
        } catch (BadLocationException e) {
            point = 1;
            //it'll be messy
        }
        code.insert(fragment, point - 1);

    }

    @Override
    public boolean alertResponse(String title) {
        int ok = JOptionPane.showConfirmDialog(new JFrame(), title, "Alert box opened by browser", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ok == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String enterValue(String title) {
        return (String) JOptionPane.showInputDialog(new JFrame(), title, "Enter Value", JOptionPane.QUESTION_MESSAGE);
    }

    @Override
    public String enterSelection(String title, String choices[]) {
        return (String) JOptionPane.showInputDialog(new JFrame(), title, "Make Selection", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
    }

    @Override
    public void elementIdent(String text) {
        if (text.length() > Const.MAX_SIZ) {
            //crop the text
            text = text.substring(0, Const.MAX_SIZ);
        }
        JOptionPane.showMessageDialog(new JFrame(), text, "Element Highlighted in Browser", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void errorMessage(String message) {
        if (message.length() > Const.MAX_SIZ) {
            //crop the text
            message = message.substring(0, Const.MAX_SIZ);
        }
        JOptionPane.showMessageDialog(new JFrame(), message.replace(". ", ". \n"), "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void finished() {
        //tableElements.setForeground(Color.BLACK);
    }

    @Override
    public void start(Object blob) {
        //wipe the structure first
        process(rootNode, "", blob, new AtomicInteger(-1));
    }

    public void process(DefaultMutableTreeNode parent, String key, Object value, AtomicInteger idx) {

        AtomicInteger innerIndex = new AtomicInteger(idx.get());

        //System.out.println("class:"+value.getClass());
        if (value instanceof JSONArray) {

            DefaultMutableTreeNode arraynode = new DefaultMutableTreeNode(key);
            parent.add(arraynode);

            JSONArray arr = (JSONArray) value;
            Iterator<Object> innerobjects = arr.iterator();
            while (innerobjects.hasNext()) {
                Object innerobject = innerobjects.next();

                innerIndex.addAndGet(1);

                if (arraynode.getUserObject().toString().contentEquals(key)) {
                    process(arraynode, String.valueOf(innerIndex.get()), innerobject, innerIndex);
                } else {
                    process(arraynode, key, innerobject, innerIndex);
                }

            }

        } else if (value instanceof JSONObject) {

            DefaultMutableTreeNode objectnode = parent;
            if (!key.isEmpty()) {
                if (innerIndex.get() > -1) {
                    objectnode = new DefaultMutableTreeNode(innerIndex);
                } else {
                    objectnode = new DefaultMutableTreeNode(key);
                }
                parent.add(objectnode);
            }

            JSONObject obj = (JSONObject) value;
            Iterator<String> innerkeys = obj.keys();
            while (innerkeys.hasNext()) {
                String innerkey = innerkeys.next();
                Object innervalue = obj.get(innerkey);

                process(objectnode, innerkey, innervalue, new AtomicInteger(-1));
            }
        } else {

            DefaultMutableTreeNode keynode = new DefaultMutableTreeNode(key);
            parent.add(keynode);
            DefaultMutableTreeNode valuenode = new DefaultMutableTreeNode(value);
            keynode.add(valuenode);

        }

    }

}
