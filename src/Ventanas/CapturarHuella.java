/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusListener;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Lenovo
 */
public class CapturarHuella extends javax.swing.JDialog {
private DPFPCapture lector = DPFPGlobal.getCaptureFactory().createCapture();
 conectar cc = new conectar();
    Connection conexion = cc.conexion();
    /**
     * Creates new form CapturarHuella
     */
    public CapturarHuella(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.addComponentListener(new ComponentAdapter(){
    
    @Override
    public void componentShown(ComponentEvent e){
        init();
        start();
    }
    @Override
    public void componentHidden(ComponentEvent e){
    }
    
    });
        
    }
    void cargar() {
    ImageIcon icon = null;
        BufferedImage img = null;
        String sql = "SELECT * FROM fondo ";
        String imagen_string = null;

        try {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                imagen_string = rs.getString("logo");
               
            }
            
                img = decodeToImage(imagen_string);
                 icon = new ImageIcon(img);
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_DEFAULT));
                jLabel1.setText(null);
                jLabel1.setIcon(icono);
            

        } catch (SQLException ex) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    public void init(){
         this.setLocationRelativeTo(null);
         
    lector.addDataListener((DPFPDataEvent dpfpde) -> {
            procesarHuella(dpfpde.getSample());
        });
        
        lector.addReaderStatusListener(new DPFPReaderStatusListener() {
            @Override
            public void readerConnected(DPFPReaderStatusEvent dpfprs) {
                lblHuella.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/imagenes/lector_conectado_2.png")).getImage().getScaledInstance(lblHuella.getWidth(), lblHuella.getHeight(), Image.SCALE_DEFAULT)));
            }

            @Override
            public void readerDisconnected(DPFPReaderStatusEvent dpfprs) {
                lblHuella.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/imagenes/lector_desconectado.png")).getImage().getScaledInstance(lblHuella.getWidth(), lblHuella.getHeight(), Image.SCALE_DEFAULT)));
            }
        });
    
    }
    protected void procesarHuella(DPFPSample sample) {
        Image image = DPFPGlobal.getSampleConversionFactory().createImage(sample);
        drawPicture(image);
    }
    
    public void drawPicture(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(180), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        bufferedImage = op.filter(bufferedImage, null);
        lblHuella.setIcon(new ImageIcon(image.getScaledInstance(lblHuella.getWidth(), lblHuella.getHeight(), Image.SCALE_DEFAULT)));
    }
    protected void start(){
        lector.startCapture();
    }
    protected void stop(){
        lector.stopCapture();
    }
 protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose){
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblPasos = new javax.swing.JLabel();
        lblHuella = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(400, 300));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(128, 128, 128));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(250, 234, 128));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPasos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/paso0.png"))); // NOI18N
        jPanel2.add(lblPasos, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        lblHuella.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lector_conectado.png"))); // NOI18N
        lblHuella.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.add(lblHuella, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 260));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 360, 260));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
cargar();        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CapturarHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturarHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturarHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturarHuella.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                CapturarHuella dialog = new CapturarHuella(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblHuella;
    public javax.swing.JLabel lblPasos;
    // End of variables declaration//GEN-END:variables
}
