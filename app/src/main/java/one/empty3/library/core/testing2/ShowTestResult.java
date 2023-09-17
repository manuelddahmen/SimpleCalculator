/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 * 2013 Manuel Dahmen
 */
package one.empty3.library.core.testing2;

import com.formdev.flatlaf.*;
import net.miginfocom.swing.MigLayout;
import one.empty3.library.ECBufferedImage;
import one.empty3.library.core.testing.ImageContainer;
import one.empty3.library.core.testing.TestObjet;
import one.empty3.library.core.testing.TextAreaOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author Manuel DAHMEN
 */
public final class ShowTestResult extends JFrame implements Runnable {

    /*__
     *
     */
    private static final long serialVersionUID = -7844993762133687210L;
    private final JTextArea jTextAreaMessage = new JTextArea("");
    private ECBufferedImage image = null;
    private ImageContainer biic;
    private boolean stop = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem4;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JSplitPane jSplitPane1;
    private JPanel jPanel1;
    private JSplitPane jSplitPane2;
    private JPanel jPanel2;
    private JCheckBox jCheckBoxOGL;
    private JCheckBox jCheckBoxModeles;
    private JCheckBox jCheckBoxFilmRec;
    private JCheckBox jCheckBoxImagesRec;
    private JTextField jTextField2;
    private JButton button3;
    private JButton jButtonDemarrerNouveauFilm;
    private JTextField jTextField1;
    private JButton button4;
    private JButton buttonShowModel;
    private JButton button2;
    private JScrollPane scrollPane1;
    private JEditorPane editorPane1;
    // End of variables declaration//GEN-END:variables
    private TestObjet testRef;

    private Throwable throwable;
    private int movieNo = 1;
    private int frameNo = 1;
    private JTable jTableEquations = new JTable();
    private boolean displaying = true;

    /*__
     * Creates new form ShowTestResult
     */
    public ShowTestResult() {
        initComponents();

        jPanel1.setSize(jPanel1.getWidth(), 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// Now create a new TextAreaOutputStream to write to our JTextArea control and wrap a
// PrintStream around it to support the println/printf methods.
        PrintStream out = new PrintStream(new TextAreaOutputStream(jTextAreaMessage));

// redirect standard output stream to the TextAreaOutputStream
        System.setOut(out);

// redirect standard error stream to the TextAreaOutputStream
        System.setErr(out);

// now test the mechanism
        Logger.getAnonymousLogger().log(Level.INFO, "Hello World");

    }

    public ShowTestResult(BufferedImage ri) {
        initComponents();

        loadImage(ri);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public ShowTestResult(ECBufferedImage ri) {
        initComponents();

        image = ri;

        if (image != null) {
            setSize(new Dimension(image.getWidth(), image.getHeight()));
        }
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(new KeyListener() {

            private boolean stop;

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
                    if (testRef != null && testRef instanceof Runnable) {
                        testRef.PAUSE();
                    }
                }
                if (e.getKeyChar() == 'S' || e.getKeyChar() == 's') {
                    if (testRef != null && testRef instanceof Runnable) {
                        testRef.STOP();
                        stop = true;
                        dispose();
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

        });
    }

    public void dessine() {
        if (biic != null && biic.getImage() != null) {
            image = new ECBufferedImage(biic.getImage());
            if (image != null) {
                if (jPanel1 != null) {
                    Graphics g = jPanel1.getGraphics();
                    if (g != null) {
                        if(isDisplaying()) {
                            jPanel1.getGraphics().drawImage(image, 0, 0,
                                    jPanel1.getWidth(), jPanel1.getHeight(), 0, 0,
                                    image.getWidth(), image.getHeight(), null);
                        }
                        jPanel1.getGraphics().drawString(" ? Pause ? " + testRef.isPause() + " ? Pause active ? " + testRef.isPauseActive(), 50, 10);
                        jTextField1.setText("Frame n° " + (testRef.frame() + 1)+"/"+testRef.getMaxFrames());
                    }
                    //Graphics gg = jPanel4.getGraphics();
                    //gimballs.draw(gg, new Rectangle(jPanel4.getWidth()-30, jPanel4.getHeight()-30, jPanel4.getWidth()-1,jPanel4.getHeight()-1));

                }
            }
        }
    }

    private void parcourir(ActionEvent e) {
        jButton1ActionPerformed(e);
    }

    private void pause(ActionEvent e) {
        jButton2ActionPerformed(e);
    }

    private void formWindowClosing1(ActionEvent e) {
        jButton3ActionPerformed(e);
    }

    private void formWindowClosing(ActionEvent e) {
        jButton3ActionPerformed(e);
    }

    public JSplitPane getJSplitPane1() {
        return this.jSplitPane1;
    }

    public JPanel getJPanel1() {
        return this.jPanel1;
    }

    public JSplitPane getJSplitPane2() {
        return this.jSplitPane2;
    }

    public JPanel getJPanel2() {
        return this.jPanel2;
    }

    public JCheckBox getJCheckBoxOGL() {
        return this.jCheckBoxOGL;
    }

    public JCheckBox getJCheckBoxModeles() {
        return this.jCheckBoxModeles;
    }

    public JCheckBox getJCheckBoxFilmRec() {
        return this.jCheckBoxFilmRec;
    }

    public JCheckBox getJCheckBoxImagesRec() {
        return this.jCheckBoxImagesRec;
    }

    public JTextField getJTextField1() {
        return this.jTextField1;
    }

    public JTextField getJTextField2() {
        return this.jTextField2;
    }

    public JButton getJButtonDemarrerNouveauFilm() {
        return this.jButtonDemarrerNouveauFilm;
    }

    public JButton getButton2() {
        return this.button2;
    }

    public JButton getButton3() {
        return this.button3;
    }

    public JButton getButton4() {
        return this.button4;
    }

    public JMenuBar getMenuBar1() {
        return this.menuBar1;
    }

    public JMenu getMenu1() {
        return this.menu1;
    }

    public JMenuItem getMenuItem1() {
        return this.menuItem1;
    }

    public JMenuItem getMenuItem2() {
        return this.menuItem2;
    }

    public JMenuItem getMenuItem3() {
        return this.menuItem3;
    }

    public JMenuItem getMenuItem4() {
        return this.menuItem4;
    }

    public JButton getButtonShowModel() {
        return this.buttonShowModel;
    }

    private void showModel(ActionEvent e) {
        File writtenFile = testRef.getWrittenFile();
        if (writtenFile != null) {
            if (!Desktop.isDesktopSupported()) {
                return;
            }

            Desktop desktop = Desktop.getDesktop();
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                return;
            }

            try {
                desktop.browse(writtenFile.toURI());
            } catch (IOException ex) {
                // Log an error
                ex.printStackTrace();
                return;
            }

            return;
        }
    }

    public JScrollPane getScrollPane1() {
        return this.scrollPane1;
    }

    public JEditorPane getEditorPane1() {
        return this.editorPane1;
    }

    private void jPanelPreviewImageMouseClicked(MouseEvent e) {
        displaying = !displaying;
    }

    private void editorPane1MouseClicked(MouseEvent e) {
        // TODO add your code here
    }
    public void exceptionReception(Exception t) {
        this.throwable = t;
        try {
            image = new ECBufferedImage(
                    ImageIO.read(
                            getClass().getResourceAsStream("one/empty3/library/core/testing2/RESULT_FAILURE.png")
                    )
            );
        } catch (IOException e) {

        }

    }

    /*__
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        this.menuBar1 = new JMenuBar();
        this.menu1 = new JMenu();
        this.menuItem4 = new JMenuItem();
        this.menuItem1 = new JMenuItem();
        this.menuItem2 = new JMenuItem();
        this.menuItem3 = new JMenuItem();
        this.jSplitPane1 = new JSplitPane();
        this.jPanel1 = new JPanel();
        this.jSplitPane2 = new JSplitPane();
        this.jPanel2 = new JPanel();
        this.jCheckBoxOGL = new JCheckBox();
        this.jCheckBoxModeles = new JCheckBox();
        this.jCheckBoxFilmRec = new JCheckBox();
        this.jCheckBoxImagesRec = new JCheckBox();
        this.jTextField2 = new JTextField();
        this.button3 = new JButton();
        this.jButtonDemarrerNouveauFilm = new JButton();
        this.jTextField1 = new JTextField();
        this.button4 = new JButton();
        this.buttonShowModel = new JButton();
        this.button2 = new JButton();
        this.scrollPane1 = new JScrollPane();
        this.editorPane1 = new JEditorPane();

        //======== this ========
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        // create UI here...
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new Color(0xccccff));
        setIconImage(new ImageIcon(getClass().getResource("/one/empty3/library/mite.png")).getImage());
        setUndecorated(false);
        setPreferredSize(new Dimension(640, 800));
        var contentPane = getContentPane();

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                this.menu1.add(this.menuItem4);
                this.menu1.add(this.menuItem1);
                this.menu1.add(this.menuItem2);
                this.menu1.add(this.menuItem3);
            }
            this.menuBar1.add(this.menu1);
        }
        setJMenuBar(this.menuBar1);

        //======== jSplitPane1 ========
        {
            try {
                UIManager.setLookAndFeel( new FlatLightLaf() );
            } catch( Exception ex ) {
                System.err.println( "Failed to initialize LaF" );
            }

            // create UI here...
            this.jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
            this.jSplitPane1.setContinuousLayout(true);
            this.jSplitPane1.setResizeWeight(0.8);

            //======== jPanel1 ========
            {
                this.jPanel1.setBackground(new Color(0xccffcc));
                this.jPanel1.setMinimumSize(new Dimension(200, 200));
                this.jPanel1.setAlignmentX(0.0F);
                this.jPanel1.setAlignmentY(0.0F);
                this.jPanel1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        jPanelPreviewImageMouseClicked(e);
                    }
                });
            }
            this.jSplitPane1.setTopComponent(this.jPanel1);

            //======== jSplitPane2 ========
            {
                this.jSplitPane2.setMinimumSize(new Dimension(200, 200));

                //======== jPanel2 ========
                {
                    this.jPanel2.setLayout(new MigLayout(
                        "fill,insets 0,hidemode 3,gap 5 5",
                        // columns
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]",
                        // rows
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[]"));

                    //---- jCheckBoxOGL ----
                    this.jCheckBoxOGL.setText("Open GL");
                    this.jCheckBoxOGL.setSelectedIcon(new ImageIcon(getClass().getResource("/one/empty3/library/core/testing2/RESULT_SUCCESS.jpg")));
                    this.jCheckBoxOGL.addActionListener(e -> jCheckBoxOGLActionPerformed(e));
                    this.jPanel2.add(this.jCheckBoxOGL, "cell 0 0");

                    //---- jCheckBoxModeles ----
                    this.jCheckBoxModeles.setText("Mod\u00e8les");
                    this.jCheckBoxModeles.setSelectedIcon(new ImageIcon(getClass().getResource("/one/empty3/library/core/testing2/RESULT_SUCCESS.jpg")));
                    this.jCheckBoxModeles.addActionListener(e -> jCheckBoxModelesActionPerformed(e));
                    this.jPanel2.add(this.jCheckBoxModeles, "cell 1 0");

                    //---- jCheckBoxFilmRec ----
                    this.jCheckBoxFilmRec.setText("Enregistrer film");
                    this.jCheckBoxFilmRec.setSelectedIcon(new ImageIcon(getClass().getResource("/one/empty3/library/core/testing2/RESULT_SUCCESS.jpg")));
                    this.jCheckBoxFilmRec.addActionListener(e -> jCheckBoxFilmRecActionPerformed(e));
                    this.jPanel2.add(this.jCheckBoxFilmRec, "cell 1 0");

                    //---- jCheckBoxImagesRec ----
                    this.jCheckBoxImagesRec.setText("Enregistrer images");
                    this.jCheckBoxImagesRec.setSelectedIcon(new ImageIcon(getClass().getResource("/one/empty3/library/core/testing2/RESULT_SUCCESS.jpg")));
                    this.jCheckBoxImagesRec.addActionListener(e -> jCheckBoxImagesRecActionPerformed(e));
                    this.jPanel2.add(this.jCheckBoxImagesRec, "cell 2 0");

                    //---- jTextField2 ----
                    this.jTextField2.setText("movie#no");
                    this.jPanel2.add(this.jTextField2, "cell 0 1");

                    //---- button3 ----
                    this.button3.setText("Pause");
                    this.button3.addActionListener(e -> pause(e));
                    this.jPanel2.add(this.button3, "cell 0 2");

                    //---- jButtonDemarrerNouveauFilm ----
                    this.jButtonDemarrerNouveauFilm.setText("(fermer et) cr\u00e9er nouveau");
                    this.jButtonDemarrerNouveauFilm.addActionListener(e -> jButtonDemarrerNouveauFilmActionPerformed(e));
                    this.jPanel2.add(this.jButtonDemarrerNouveauFilm, "cell 1 1");

                    //---- jTextField1 ----
                    this.jTextField1.setText("frame#no");
                    this.jPanel2.add(this.jTextField1, "cell 2 1");

                    //---- button4 ----
                    this.button4.setText("Quitter");
                    this.button4.addActionListener(e -> formWindowClosing1(e));
                    this.jPanel2.add(this.button4, "cell 1 2");

                    //---- buttonShowModel ----
                    this.buttonShowModel.addActionListener(e -> showModel(e));
                    this.jPanel2.add(this.buttonShowModel, "cell 2 2");

                    //---- button2 ----
                    this.button2.setText("Parcourir");
                    this.button2.addActionListener(e -> parcourir(e));
                    this.jPanel2.add(this.button2, "cell 0 3");
                }
                this.jSplitPane2.setLeftComponent(this.jPanel2);

                //======== scrollPane1 ========
                {

                    //---- editorPane1 ----
                    this.editorPane1.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            editorPane1MouseClicked(e);
                        }
                    });
                    this.scrollPane1.setViewportView(this.editorPane1);
                }
                this.jSplitPane2.setRightComponent(this.scrollPane1);
            }
            this.jSplitPane1.setBottomComponent(this.jSplitPane2);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(this.jSplitPane1)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(this.jSplitPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );

        initComponentsI18n();

        setSize(915, 435);
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("explorer \"" + testRef.getSubfolder().getAbsolutePath() + "\"");
        } catch (IOException ex) {
            Logger.getLogger(ShowTestResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        testRef.PAUSE();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        testRef.STOP();
        stop = true;
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    public void toggleTestOption(int GEN_OPT, boolean value) {
        testRef.setGenerate(testRef.getGenerate() | ((value ? 1 : 0) | GEN_OPT));
    }

    private void jCheckBoxOGLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOGLActionPerformed

        toggleTestOption(TestObjet.GENERATE_OPENGL, jCheckBoxOGL.isSelected());
    }//GEN-LAST:event_jCheckBoxOGLActionPerformed

    private void jCheckBoxModelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxModelesActionPerformed
        toggleTestOption(TestObjet.GENERATE_MODEL, jCheckBoxModeles.isSelected());
    }//GEN-LAST:event_jCheckBoxModelesActionPerformed

    private void jCheckBoxFilmRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFilmRecActionPerformed
        toggleTestOption(TestObjet.GENERATE_MOVIE, jCheckBoxFilmRec.isSelected());
    }//GEN-LAST:event_jCheckBoxFilmRecActionPerformed

    private void jCheckBoxImagesRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxImagesRecActionPerformed
        toggleTestOption(TestObjet.GENERATE_IMAGE, jCheckBoxImagesRec.isSelected());
    }//GEN-LAST:event_jCheckBoxImagesRecActionPerformed

    private void jButtonDemarrerNouveauFilmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDemarrerNouveauFilmActionPerformed
        testRef.startNewMovie();
        jTextField2.setText("New movie" + ++movieNo);
    }//GEN-LAST:event_jButtonDemarrerNouveauFilmActionPerformed

    private void jSliderXPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSliderXPropertyChange
    }//GEN-LAST:event_jSliderXPropertyChange

    private void jSliderYPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSliderYPropertyChange
    }//GEN-LAST:event_jSliderYPropertyChange

    private void jSliderZPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSliderZPropertyChange
    }//GEN-LAST:event_jSliderZPropertyChange

    private void jSliderXYZPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSliderXYZPropertyChange
    }//GEN-LAST:event_jSliderXYZPropertyChange

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String sx = "0", sy = "0", sz = "0";
        sx = (String) jTableEquations.getCellEditor(0, 1).getCellEditorValue();
        sy = (String) jTableEquations.getCellEditor(1, 1).getCellEditorValue();
        sz = (String) jTableEquations.getCellEditor(2, 1).getCellEditorValue();
/*
        TRIObjetSurfaceEquationParametrique eq
                = new TRIObjetSurfaceEquationParametrique(
                new AnalyseurEquationJep(sx),
                new AnalyseurEquationJep(sy), new AnalyseurEquationJep(sz));
        testRef.scene().add(eq);
*/
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        testRef.STOP();
    }//GEN-LAST:event_formWindowClosing

    public double valuePC(int v) {
        double vv = 2 * Math.PI / 100 * v;
        return vv;
    }

    public void loadImage(BufferedImage ri) {
        this.image = new ECBufferedImage(ri);
        if (image != null) {
            setSize(new Dimension(image.getWidth(), image.getHeight()));
        }
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(ShowTestResult.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
    }

    public void setImageContainer(ImageContainer ic) {
        this.biic = ic;
    }

    public void setTestObjet(TestObjet testObjet) {
        this.testRef = testObjet;

        jCheckBoxImagesRec.setSelected(
                testRef.getGenerate(TestObjet.GENERATE_IMAGE));
        jCheckBoxFilmRec.setSelected(
                testRef.getGenerate(TestObjet.GENERATE_MOVIE));
        jCheckBoxModeles.setSelected(
                testRef.getGenerate(TestObjet.GENERATE_MODEL));
        //jCheckBoxOpenGl.setSelected(
        //toggleTestOption(testRef.GENERATE_OPENGL, testRef.getGenerate(testRef.GENERATE_IMAGE));
        setTitle(testObjet.getClass().getCanonicalName());

    }

    public void stopThreads() {
        stop = true;
    }

    public void setMessage(String message) {
        //jTextAreaMessage.setText(jTextAreaMessage.getText() + "\n" + message);
    }


    private void initComponentsI18n() {
        // JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("one.empty3.library.core.testing2.bundle");
        this.menu1.setText(bundle.getString("ShowTestResult.menu1.text"));
        this.menuItem4.setText(bundle.getString("ShowTestResult.menuItem4.text"));
        this.menuItem1.setText(bundle.getString("ShowTestResult.menuItem1.text"));
        this.menuItem2.setText(bundle.getString("ShowTestResult.menuItem2.text"));
        this.menuItem3.setText(bundle.getString("ShowTestResult.menuItem3.text"));
        this.buttonShowModel.setText(bundle.getString("ShowTestResult.buttonShowModel.text"));
        // JFormDesigner - End of component i18n initialization  //GEN-END:initI18n  @formatter:on
    }


    private boolean isDisplaying() {
        return true;
    }

    public void setDisplaying(boolean b) {
        this.displaying = b;
    }
}

