/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import static Ventanas.subir_imagen.decodeToImage;
import clases.LeerHuella;
import clases.LeerHuella_Administradores;
import clases.LeerHuella_Administradores_verificar;
import clases.LeerHuella_Clientes;
import clases.LeerHuella_Clientes_salir;
import clases.LeerHuella_Clientes_verificar;
import clases.LeerHuella_Medidas;
import clases.LeerHuella_Rutinas;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Lenovo
 */
public class Menu_1 extends javax.swing.JFrame {

    /**
     * Creates new form Menu_1
     */
    conectar cc = new conectar();
    Connection conexion = cc.conexion();
    ImageIcon icon = null;
    boolean verificar_fecha = false;

    int ID;

    public Menu_1() {

        initComponents();
        this.setLocationRelativeTo(null);

        
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/cerrar.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen1.getWidth(), label_imagen1.getHeight(), Image.SCALE_DEFAULT));
        label_imagen1.setIcon(icono);
        this.repaint();
        fot = new ImageIcon(getClass().getResource("/imagenes1/cliente.png"));
        icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel4.getWidth(), jLabel4.getHeight(), Image.SCALE_DEFAULT));
        jLabel4.setIcon(icono);

        this.repaint();
        fot = new ImageIcon(getClass().getResource("/imagenes1/carrito-de-tienda.png"));
        icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel5.getWidth(), jLabel5.getHeight(), Image.SCALE_DEFAULT));
        jLabel5.setIcon(icono);

        

        this.repaint();

        fot = new ImageIcon(getClass().getResource("/imagenes1/forma-de-pago.png"));
        icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel8.getWidth(), jLabel8.getHeight(), Image.SCALE_DEFAULT));
        jLabel8.setIcon(icono);

        this.repaint();

        fot = new ImageIcon(getClass().getResource("/imagenes1/gimnasio.png"));
        icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_DEFAULT));
        jLabel7.setIcon(icono);

        this.repaint();
        fot = new ImageIcon(getClass().getResource("/imagenes1/regla-plegable.png"));
        icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel9.getWidth(), jLabel9.getHeight(), Image.SCALE_DEFAULT));
        jLabel9.setIcon(icono);

        this.repaint();
        fot = new ImageIcon(getClass().getResource("/imagenes1/exit.png"));
        icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel12.getWidth(), jLabel12.getHeight(), Image.SCALE_DEFAULT));
        jLabel12.setIcon(icono);

        

        this.repaint();

        cargar();
        cargarf();
        jLabel2.setVisible(false);
    }

    void cargarf() {
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
            Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabel11.getWidth(), jLabel11.getHeight(), Image.SCALE_DEFAULT));
            jLabel11.setText(null);
            jLabel11.setIcon(icono);

        } catch (SQLException ex) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void cargar1() {
        BufferedImage img = null;
        String sql = "SELECT imagen FROM empleado where Id_Usuario =" + ID + "";
        String imagen_string = null;

        try {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                imagen_string = rs.getString("imagen");

            }

            img = decodeToImage(imagen_string);
            ImageIcon icon = new ImageIcon(img);
            Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_DEFAULT));
            jLabel3.setText(null);
            jLabel3.setIcon(icono);

        } catch (SQLException ex) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void cargar() {
        BufferedImage img = null;
        String sql = "SELECT * FROM logo ";
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
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        label_imagen1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(900, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(128, 128, 128));
        jPanel1.setMaximumSize(new java.awt.Dimension(900, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(250, 234, 128));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 40, 10));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/cliente.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 130, 110));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/carrito-de-tienda.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 130, 110));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/carrito-de-tienda.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 130, 110));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/carrito-de-tienda.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 200, 130, 110));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/avatar.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 130));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/carrito-de-tienda.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 200, 130, 110));

        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, 220, 160));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/cliente.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 320, 130, 110));

        jLabel11.setText("jLabel11");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 550));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 840, 550));

        jLabel15.setForeground(new java.awt.Color(250, 234, 128));
        jLabel15.setText("RFC: RUPG9101103U0");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel16.setForeground(new java.awt.Color(250, 234, 128));
        jLabel16.setText("Menu");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

        label_imagen1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_imagen1MouseClicked(evt);
            }
        });
        jPanel1.add(label_imagen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, 30, 20));

        jMenu1.setText("Tareas");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu3.setText("Salida");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu2.setText("Mision ");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        this.ID = Integer.valueOf(jLabel2.getText());
        System.out.print(ID);
        cargar1();// TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowIconified

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        LeerHuella_Clientes_verificar lh = new LeerHuella_Clientes_verificar(null, true, ID);
        lh.setBUSCAR(true);
        lh.setVisible(true);
        this.setVisible(false);
        lh.stop();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        Ventas obj = new Ventas();
        obj.jLabel2.setText(Integer.toString(ID));

        obj.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked

        LeerHuella_Clientes lh = new LeerHuella_Clientes(null, true);
        lh.id2(ID);
        lh.setBUSCAR(true);
        lh.setVisible(true);
        this.setVisible(false);
        lh.stop();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        LeerHuella_Rutinas lh = new LeerHuella_Rutinas(null, true);
        lh.id2(ID);
        lh.setBUSCAR(true);
        lh.setVisible(true);
        this.setVisible(false);
        lh.stop();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        subir_imagen_empleados obj = new subir_imagen_empleados();
        obj.jLabel3.setText(Integer.toString(ID));

        obj.setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

        LeerHuella_Medidas lh = new LeerHuella_Medidas(null, true);
        lh.id2(ID);
        lh.setBUSCAR(true);
        lh.setVisible(true);
        this.setVisible(false);
        lh.stop();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        subir_imagen obj = new subir_imagen();
        obj.jLabel3.setText(Integer.toString(ID));
        obj.setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        LeerHuella_Clientes_salir lh = new LeerHuella_Clientes_salir(null, true);
        lh.id2(ID);
        lh.setBUSCAR(true);
        lh.setVisible(true);
        this.setVisible(false);
        lh.stop();

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseClicked

    private void label_imagen1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_imagen1MouseClicked
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea Salir?", "Exit", dialog);
        if (result == 0) {
            System.exit(0);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_label_imagen1MouseClicked

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        String sql = "SELECT archivo, nomre FROM pdf where id = 1";

        byte[] b = null;
        String nombre = "";
        try {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                b = rs.getBytes(1);
                nombre = rs.getString(2);
            }

            InputStream bos = new ByteArrayInputStream(b);

            int tamanoInput = bos.available();
            byte[] datosPDF = new byte[tamanoInput];
            bos.read(datosPDF, 0, tamanoInput);

            OutputStream out = new FileOutputStream(nombre + ".pdf");
            out.write(datosPDF);

            //abrir archivo
            out.close();
            bos.close();
            s.close();
            rs.close();

        } catch (IOException | NumberFormatException | SQLException ex) {
            System.out.println("Error al abrir archivo PDF " + ex.getMessage());
        }
        try {
            Desktop.getDesktop().open(new File(nombre + ".pdf"));
            // TODO add your handling code here:
        } catch (IOException ex) {
            Logger.getLogger(Menu_1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        ver_tareas obj = new ver_tareas();
        obj.jLabel2.setText(Integer.toString(ID));
        obj.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Quieres registrar tu salida?", "Exit", dialog);
        if (result == 0) {
            String sqlr = "select Empleado_Id_Usuario FROM historialempleado WHERE Fecha = CURDATE() AND Accion = 'Salida'";
            conectar conr = new conectar();
            Connection conexionr = conr.conexion();

            Statement sr;
            try {
                sr = conexion.createStatement();
                ResultSet rsr = sr.executeQuery(sqlr);
                while (rsr.next()) {

                    if (rsr.getInt(1) == ID) {
                        verificar_fecha = true;
                    }

                }
            } catch (SQLException ex) {
                Logger.getLogger(Salida.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (verificar_fecha == false) {
                String sql = "";
                sql = "INSERT INTO historialempleado (Fecha, Hora, Empleado_Id_Usuario, Accion) VALUES (CURDATE(),CURTIME(),?,'Salida')";
                try {

                    PreparedStatement pst = conexion.prepareStatement(sql);
                    ;
                    pst.setInt(1, ID);

                    int n = pst.executeUpdate();
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "La salida se ha registrado");

                    } else {
                        JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException | HeadlessException e) {
                    Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            System.exit(0);        // TODO add your handling code here:
            }
    }//GEN-LAST:event_jMenu3MouseClicked

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
            java.util.logging.Logger.getLogger(Menu_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_imagen1;
    // End of variables declaration//GEN-END:variables
}
