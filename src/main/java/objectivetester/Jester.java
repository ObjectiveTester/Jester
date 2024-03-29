package objectivetester;

/**
 *
 * @author Steve
 */
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
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
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;

public class Jester extends javax.swing.JFrame implements UserInterface, ActionListener {

    //JSON Tester
    private ImageIcon icon;

    DefaultMutableTreeNode nodeReq;
    private DefaultTreeModel tmReq;
    JTree treeReq;

    DefaultMutableTreeNode nodeRes;
    private DefaultTreeModel tmRes;
    JTree treeRes;

    private final JPopupMenu popupReq;
    private final JPopupMenu popupRes;
    private final APIConnector apiCon = new APIConnector(this);
    DefaultWriter writer;
    private final Preferences prefs;
    String lastUsedURI = "";
    String updatedCookies="";

    /**
     * Creates new form
     */
    public Jester() {
        icon = new ImageIcon(getClass().getResource("/images/jester.png"));

        nodeReq = new DefaultMutableTreeNode("Request");
        tmReq = new DefaultTreeModel(nodeReq);
        treeReq = new JTree(tmReq);

        nodeRes = new DefaultMutableTreeNode("Response");
        tmRes = new DefaultTreeModel(nodeRes);
        treeRes = new JTree(tmRes);

        initComponents();
        paneRequest.setVisible(true);
        paneResponse.setVisible(true);

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

        //create the popup menu
        popupReq = new JPopupMenu();
        popupRes = new JPopupMenu();
        MouseListener popupListenerReq = new ReqEventListener(popupReq, treeReq, this);
        MouseListener popupListenerRes = new ResEventListener(popupRes, treeRes, this);

        JMenuItem menuItem;

        //This should be only on response
        menuItem = new JMenuItem(Const.ASSERT);
        menuItem.addActionListener((ActionListener) popupListenerRes);
        popupRes.add(menuItem);
        treeRes.addMouseListener(popupListenerRes);

        //This should only be on request
        menuItem = new JMenuItem(Const.EDIT);
        menuItem.addActionListener((ActionListener) popupListenerReq);
        popupReq.add(menuItem);
        menuItem = new JMenuItem(Const.INSERTK);
        menuItem.addActionListener((ActionListener) popupListenerReq);
        popupReq.add(menuItem);
        menuItem = new JMenuItem(Const.INSERTV);
        menuItem.addActionListener((ActionListener) popupListenerReq);
        popupReq.add(menuItem);
        menuItem = new JMenuItem(Const.DELETE);
        menuItem.addActionListener((ActionListener) popupListenerReq);
        popupReq.add(menuItem);
        menuItem = new JMenuItem(Const.REFRESH);
        menuItem.addActionListener((ActionListener) popupListenerReq);
        popupReq.add(menuItem);
        treeReq.addMouseListener(popupListenerReq);

        //get prefs
        prefs = Preferences.userRoot().node("jester");
        if (prefs.get("serviceURI", "").contentEquals("")) {
            //set defaults
            prefs.putBoolean("manageCookies", true);
            //prefs.putBoolean("option2", false);
            prefs.put("output", "junit5");
            prefs.put("serviceURI", "https://httpbin.org/anything");
        }
        currentURI.setText(prefs.get("serviceURI", ""));
        serviceURI.setText(prefs.get("serviceURI", ""));
        checkBox1.setSelected(prefs.getBoolean("manageCookies", true));
        //checkBox2.setSelected(prefs.getBoolean("option2", true));
        if (prefs.get("output", "").contentEquals("junit5")) {
            buttonJunit5.setSelected(true);
            writer = new TestWriter5(this);
            writer.writeHeader();
        }
        if (prefs.get("output", "").contentEquals("testng")) {
            buttonTestNG.setSelected(true);
            writer = new TestWriterNG(this);
            writer.writeHeader();
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
        labelOpts = new javax.swing.JLabel();
        checkBox1 = new javax.swing.JCheckBox();
        checkBox2 = new javax.swing.JCheckBox();
        labelOutput = new javax.swing.JLabel();
        buttonJunit5 = new javax.swing.JRadioButton();
        buttonTestNG = new javax.swing.JRadioButton();
        buttonSave = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        labelUri = new javax.swing.JLabel();
        currentURI = new javax.swing.JTextField();
        buttonGET = new javax.swing.JButton();
        buttonPOST = new javax.swing.JButton();
        buttonDELETE = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        labelHeaders = new javax.swing.JLabel();
        textHeaders = new javax.swing.JTextField();
        labelCookies = new javax.swing.JLabel();
        textCookies = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        paneRequest = new javax.swing.JScrollPane(treeReq);
        paneResponse = new javax.swing.JScrollPane(treeRes);
        jPanel2 = new javax.swing.JPanel();
        paneCode = new javax.swing.JScrollPane();
        code = new javax.swing.JTextArea();
        paneConsole = new javax.swing.JScrollPane();
        textConsole = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuImport = new javax.swing.JMenuItem();
        menuCopyRes = new javax.swing.JMenuItem();
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

        labelCopyright.setText("© Steve Mellor 2019-2023");
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
        dialogSettings.setMinimumSize(new java.awt.Dimension(500, 300));
        dialogSettings.setModal(true);
        dialogSettings.setSize(new java.awt.Dimension(500, 300));

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelSettings.add(serviceURI, gridBagConstraints);
        serviceURI.getAccessibleContext().setAccessibleName("Default URI");

        labelOpts.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelOpts.setText("Options");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panelSettings.add(labelOpts, gridBagConstraints);

        checkBox1.setText("Manage Cookies");
        checkBox1.setToolTipText("Manage Cookies");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        panelSettings.add(checkBox1, gridBagConstraints);

        checkBox2.setText("Option2");
        checkBox2.setToolTipText("Option2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        panelSettings.add(checkBox2, gridBagConstraints);

        labelOutput.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelOutput.setText("Generated Output");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panelSettings.add(labelOutput, gridBagConstraints);

        buttonsOutput.add(buttonJunit5);
        buttonJunit5.setText("JUnit5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelSettings.add(buttonJunit5, gridBagConstraints);

        buttonsOutput.add(buttonTestNG);
        buttonTestNG.setText("TestNG");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelSettings.add(buttonTestNG, gridBagConstraints);

        buttonSave.setText("Save");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
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
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panelSettings.add(buttonCancel, gridBagConstraints);

        javax.swing.GroupLayout dialogSettingsLayout = new javax.swing.GroupLayout(dialogSettings.getContentPane());
        dialogSettings.getContentPane().setLayout(dialogSettingsLayout);
        dialogSettingsLayout.setHorizontalGroup(
            dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
            .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
        );
        dialogSettingsLayout.setVerticalGroup(
            dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
            .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jester");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(icon.getImage());
        setPreferredSize(new java.awt.Dimension(850, 750));

        jPanel1.setPreferredSize(new java.awt.Dimension(688, 80));

        jToolBar1.setRollover(true);

        labelUri.setText("URI");
        jToolBar1.add(labelUri);

        currentURI.setColumns(15);
        currentURI.setText("URI");
        currentURI.setToolTipText("Service URI");
        currentURI.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        currentURI.setMinimumSize(new java.awt.Dimension(6, 15));
        currentURI.setPreferredSize(new java.awt.Dimension(12, 20));
        jToolBar1.add(currentURI);
        currentURI.getAccessibleContext().setAccessibleName("URI");

        buttonGET.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/get.png"))); // NOI18N
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

        buttonPOST.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/post.png"))); // NOI18N
        buttonPOST.setToolTipText("POST");
        buttonPOST.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonPOST.setFocusable(false);
        buttonPOST.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPOST.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonPOST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPOSTActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonPOST);

        buttonDELETE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        buttonDELETE.setToolTipText("DELETE");
        buttonDELETE.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonDELETE.setFocusable(false);
        buttonDELETE.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonDELETE.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonDELETE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDELETEActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonDELETE);

        jToolBar2.setRollover(true);

        labelHeaders.setText("Headers");
        jToolBar2.add(labelHeaders);

        textHeaders.setText("Authorization=Bearer abcde");
        textHeaders.setToolTipText("Comma seperated list of headers (e.g. Header=foo)");
        textHeaders.setMinimumSize(new java.awt.Dimension(275, 23));
        textHeaders.setPreferredSize(new java.awt.Dimension(275, 23));
        jToolBar2.add(textHeaders);

        labelCookies.setText("Cookies");
        jToolBar2.add(labelCookies);

        textCookies.setToolTipText("Comma seperated list of cookies (e.g. ID=foo)");
        textCookies.setMinimumSize(new java.awt.Dimension(275, 23));
        textCookies.setPreferredSize(new java.awt.Dimension(275, 23));
        jToolBar2.add(textCookies);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 905, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(40, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(39, Short.MAX_VALUE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setLastDividerLocation(-1);

        paneRequest.setPreferredSize(new java.awt.Dimension(400, 400));
        jSplitPane1.setLeftComponent(paneRequest);

        paneResponse.setPreferredSize(new java.awt.Dimension(400, 400));
        jSplitPane1.setRightComponent(paneResponse);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        paneCode.setPreferredSize(new java.awt.Dimension(300, 300));

        code.setEditable(false);
        code.setColumns(20);
        code.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        code.setRows(5);
        code.setTabSize(4);
        paneCode.setViewportView(code);

        textConsole.setEditable(false);
        textConsole.setColumns(20);
        textConsole.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        textConsole.setRows(5);
        paneConsole.setViewportView(textConsole);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paneCode, javax.swing.GroupLayout.DEFAULT_SIZE, 905, Short.MAX_VALUE)
                    .addComponent(paneConsole))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paneCode, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paneConsole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        menuFile.setText("File");

        menuImport.setText("Import Request");
        menuImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuImportActionPerformed(evt);
            }
        });
        menuFile.add(menuImport);

        menuCopyRes.setText("Copy Response into Request");
        menuCopyRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCopyResActionPerformed(evt);
            }
        });
        menuFile.add(menuCopyRes);

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

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed

        //update UI
        currentURI.setText(serviceURI.getText());

        //save new settings
        prefs.put("serviceURI", serviceURI.getText().trim());

        prefs.putBoolean("manageCookies", checkBox1.isSelected());
        //prefs.putBoolean("option2", checkBox2.isSelected());

        if (buttonJunit5.isSelected()) {
            //if the output type has changed, reset
            if (prefs.get("output", "").contentEquals("testng")) {
                code.setText("");
                writer = new TestWriter5(this);
                writer.writeHeader();
            }
            prefs.put("output", "junit5");
        }
        if (buttonTestNG.isSelected()) {
            //if the output type has changed, reset
            if (prefs.get("output", "").contentEquals("junit5")) {
                code.setText("");
                writer = new TestWriterNG(this);
                writer.writeHeader();
            }
            prefs.put("output", "testng");
        }
        dialogSettings.setVisible(false);
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonGETActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGETActionPerformed
        boolean proceed = true;
        if (currentURI.getText().trim().contentEquals(lastUsedURI)) {
            proceed = askQuestion("Use this URI?");
        }

        if (proceed) {
            wipeRes();
            System.out.print("GET " + currentURI.getText().trim() + " ");
            int respCode = apiCon.reqGet(currentURI.getText().trim(), textHeaders.getText(), textCookies.getText(), nodeRes);
            System.out.println();

            writer.writeStart();
            writer.writeGet(currentURI.getText().trim(), respCode, textHeaders.getText(), textCookies.getText());
            writer.writeEnd();

            if (!updatedCookies.isEmpty() && prefs.getBoolean("manageCookies", true)) {
                textCookies.setText(updatedCookies);
            }
            lastUsedURI = currentURI.getText().trim();
        }
    }//GEN-LAST:event_buttonGETActionPerformed

    private void labelLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelLinkMouseClicked
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
        labelLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_labelLinkMouseEntered

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        dispose();
        System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSettingsActionPerformed
        dialogSettings.setVisible(true);
    }//GEN-LAST:event_menuSettingsActionPerformed

    private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
        dialogAbout.setVisible(true);
    }//GEN-LAST:event_menuAboutActionPerformed

    private void buttonCoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCoolActionPerformed
        dialogAbout.setVisible(false);
    }//GEN-LAST:event_buttonCoolActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        //restore original settings
        serviceURI.setText((prefs.get("serviceURI", "")));
        checkBox1.setSelected(prefs.getBoolean("manageCookies", true));
        //checkBox2.setSelected(prefs.getBoolean("option2", true));

        dialogSettings.setVisible(false);
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonPOSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPOSTActionPerformed
        boolean proceed = true;
        if (currentURI.getText().trim().contentEquals(lastUsedURI)) {
            proceed = askQuestion("Use this URI?");
        }

        if (proceed) {
            System.out.print("POST " + currentURI.getText().trim() + " ");

            //save the outgoing data with escaped doube quotes
            String data = apiCon.repack(nodeReq).replace("\"", "\\\"");

            int respCode = apiCon.reqPost(currentURI.getText().trim(), textHeaders.getText(), textCookies.getText(), nodeReq, nodeRes);
            System.out.println();

            writer.writeStart();
            writer.writePost(currentURI.getText().trim(), data, respCode, textHeaders.getText(), textCookies.getText());
            writer.writeEnd();

            if (!updatedCookies.isEmpty() && prefs.getBoolean("manageCookies", true)) {
                textCookies.setText(updatedCookies);
            }
            lastUsedURI = currentURI.getText().trim();
        }

    }//GEN-LAST:event_buttonPOSTActionPerformed

    private void buttonDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDELETEActionPerformed
        boolean proceed = true;
        if (currentURI.getText().trim().contentEquals(lastUsedURI)) {
            proceed = askQuestion("Use this URI?");
        }

        if (proceed) {
            System.out.print("DELETE " + currentURI.getText().trim() + " ");

            int respCode = apiCon.reqDelete(currentURI.getText().trim(), textHeaders.getText(), textCookies.getText(), nodeRes);
            System.out.println();

            writer.writeStart();
            writer.writeDelete(currentURI.getText().trim(), respCode, textHeaders.getText(), textCookies.getText());
            writer.writeEnd();

            if (!updatedCookies.isEmpty() && prefs.getBoolean("manageCookies", true)) {
                textCookies.setText(updatedCookies);
            }
            lastUsedURI = currentURI.getText().trim();
        }

    }//GEN-LAST:event_buttonDELETEActionPerformed

    private void menuImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuImportActionPerformed
        String rawjson = enterValue("import JSON data");
        if (rawjson != null) {
            apiCon.importData(rawjson, nodeReq);
        }
    }//GEN-LAST:event_menuImportActionPerformed

    private void menuCopyResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCopyResActionPerformed
        apiCon.copyRes(nodeRes, nodeReq);
    }//GEN-LAST:event_menuCopyResActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">

        try {
            //javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            if (Theme.light()) {
                javax.swing.UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
            } else {
                javax.swing.UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            }
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
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonCool;
    private javax.swing.JButton buttonDELETE;
    private javax.swing.JButton buttonGET;
    private javax.swing.JRadioButton buttonJunit5;
    private javax.swing.JButton buttonPOST;
    private javax.swing.JButton buttonSave;
    private javax.swing.JRadioButton buttonTestNG;
    private javax.swing.ButtonGroup buttonsOutput;
    private javax.swing.JCheckBox checkBox1;
    private javax.swing.JCheckBox checkBox2;
    private javax.swing.JTextArea code;
    private javax.swing.JTextField currentURI;
    private javax.swing.JDialog dialogAbout;
    private javax.swing.JDialog dialogSettings;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel labelCookies;
    private javax.swing.JLabel labelCopyright;
    private javax.swing.JLabel labelDefuri;
    private javax.swing.JLabel labelDesc;
    private javax.swing.JLabel labelHeaders;
    private javax.swing.JLabel labelLink;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelOpts;
    private javax.swing.JLabel labelOutput;
    private javax.swing.JLabel labelUri;
    private javax.swing.JMenuItem menuAbout;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuCopyRes;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuImport;
    private javax.swing.JMenuItem menuSettings;
    private javax.swing.JScrollPane paneCode;
    private javax.swing.JScrollPane paneConsole;
    private javax.swing.JScrollPane paneRequest;
    private javax.swing.JScrollPane paneResponse;
    private javax.swing.JPanel panelAbout;
    private javax.swing.JPanel panelSettings;
    private javax.swing.JTextField serviceURI;
    private javax.swing.JTextArea textConsole;
    private javax.swing.JTextField textCookies;
    private javax.swing.JTextField textHeaders;
    // End of variables declaration//GEN-END:variables

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
    public void writeAssert(String node, String value) {
        writer.writeAssert(node, value);
    }

    @Override
    public String enterValue(String title) {
        return (String) JOptionPane.showInputDialog(new JFrame(), title, "Enter Value", JOptionPane.QUESTION_MESSAGE);
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
    public boolean askQuestion(String questionText) {
        int ok = JOptionPane.showConfirmDialog(new JFrame(), questionText, questionText, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ok == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("ae:" + ae);
    }

    @Override
    public void delete(DefaultMutableTreeNode target) {
        tmReq.removeNodeFromParent(target);
    }

    @Override
    public void copyRes(DefaultMutableTreeNode source, DefaultMutableTreeNode target) {
        apiCon.copyRes(nodeRes, nodeReq);
    }

    @Override
    public boolean getOption() {
        //not used
        return prefs.getBoolean("optionX", true);
    }

    @Override
    public void refresh() {
        //DELETEME - debug only
        apiCon.refresh(nodeReq);
    }

    @Override
    public void update() {
        treeReq.updateUI();
        treeRes.updateUI();
    }

    @Override
    public void wipeRes() {
        nodeRes.removeAllChildren(); //this removes all nodes
        tmRes.reload(); //this notifies the listeners and changes the GUI
    }

    @Override
    public void wipeReq() {
        nodeReq.removeAllChildren(); //this removes all nodes
        tmReq.reload(); //this notifies the listeners and changes the GUI
    }

    @Override
    public void newCookies(String cookies) {
        updatedCookies = cookies;
    }
}
