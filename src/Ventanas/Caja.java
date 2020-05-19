/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import static Ventanas.subir_imagen.decodeToImage;
import static Ventanas.subir_imagen.encodeToString;
import clases.LeerHuella;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Lenovo
 */
public class Caja extends javax.swing.JFrame {

    int ID = 0;

    private byte[] huella = null;
    private JPanel contentPane;
    
    
    private Double total_ventas;
    private Double total_proveedores;
    private Double total_empleados;
    private Double Total_total;
    private Double totalcaja;
    /**
     * File fichero = null; Creates new form Menu_1
     */
    conectar cc = new conectar();
    Connection conexion = cc.conexion();
    Statement statement = null;

    Statement pst = null;

    public Caja() {

        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/back.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
        label_imagen.setIcon(icono);
cargar();
        desactivar();
        mostrar_cash();
        mostrar_Ventas();
        Pago_Proovedor();
        Pago_Empleados();
        Ganancias();
        jLabel2.setVisible(false);

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
    public void mostrar_cash() {
        try {
            statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT dinero from caja WHERE id=1 ");
            while (rs.next()) {
                selec_dinero.setText("La caja tiene " + rs.getString(1));
                totalcaja=rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(tareas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrar_Ventas() {
        try {
            statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT sum(Precio_Producto*ventas.cantidad) as Total_Ventas from producto INNER JOIN  ventas ON  producto.Id_Producto=ventas.Producto_Id_Producto Where ventas.Fecha_Venta=CURDATE()");
            while (rs.next()) {
                total_ventas=rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(tareas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Pago_Proovedor() {
        try {
            statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select sum(Cantidad_Dinero) from pagos_provedor  where Fecha_Pago=CURDATE()");
            while (rs.next()) {
                total_proveedores=rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(tareas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Pago_Empleados() {
        try {
            statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select sum(Cantidad_Dinero) from pagos_empleado where Fecha_Pago=CURDATE()");
            while (rs.next()) {
                total_empleados=rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(tareas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Ganancias() {
            Total_total=(totalcaja+total_ventas)-(total_empleados+total_proveedores);
    }
    
    

    public void desactivar() {
        if (txtnombre.getText().length() == 0) {
            btnguardar.setEnabled(false);
        } else {
            btnguardar.setEnabled(true);
        }
    }


    public void mostrar_ventas() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();

            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    null,
                    new String[]{
                        //Defines TODOS los nombres de las columnas que tendrá la tabla
                        "Nombre Producto", "Precio Producto", "Cantidad", "Fehca de Venta", "Total"
                    }
            ));
            modelo = (DefaultTableModel) jTable1.getModel();
            String sql = "SELECT \n"
                    + "Nombre_Producto,\n"
                    + "Precio_Producto,\n"
                    + "ventas.Cantidad,\n"
                    + "ventas.Fecha_Venta,\n"
                    + "(Precio_Producto*ventas.cantidad) as Total\n"
                    + "from producto INNER JOIN  ventas ON  producto.Id_Producto=ventas.Producto_Id_Producto \n"
                    + "Where ventas.Fecha_Venta=CURDATE()";
            Object[] datos = new Object[5];

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);

                modelo.addRow(datos);
            }

            jTable1.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(Caja.class.getName()).log(Level.SEVERE, null, ex);
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
        btnGanancias = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnreset = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        mostrar_ventas = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtnombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnguardar = new javax.swing.JButton();
        selec_dinero = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        label_imagen = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(900, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(128, 128, 128));
        jPanel1.setMaximumSize(new java.awt.Dimension(900, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(250, 234, 128));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGanancias.setText("Corte de Caja");
        btnGanancias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGananciasMouseEntered(evt);
            }
        });
        btnGanancias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGananciasActionPerformed(evt);
            }
        });
        jPanel2.add(btnGanancias, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, -1, -1));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 40, 10));

        btnreset.setText("Consultar Ganancias");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });
        jPanel2.add(btnreset, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, -1, -1));

        mostrar_ventas.setText("Total Ganancias:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mostrar_ventas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(mostrar_ventas)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 460, 130, 20));
        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jScrollPane3.setViewportView(jScrollPane1);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 470, 320));

        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnombreKeyReleased(evt);
            }
        });

        jLabel6.setText("Iniciar Caja $");

        btnguardar.setText("Guardar");
        btnguardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnguardarMouseEntered(evt);
            }
        });
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        selec_dinero.setText("Equipo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(17, 17, 17)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnguardar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(selec_dinero)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnguardar))
                .addGap(17, 17, 17)
                .addComponent(selec_dinero)
                .addContainerGap())
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 320, 80));

        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 550));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 840, 550));

        label_imagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_imagenMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_imagenMouseEntered(evt);
            }
        });
        jPanel1.add(label_imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 30));

        jLabel15.setForeground(new java.awt.Color(250, 234, 128));
        jLabel15.setText("Caja");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

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

    private void label_imagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_imagenMouseClicked
        Menu_11 obj = new Menu_11();
        obj.jLabel2.setText(Integer.toString(ID));
        obj.setVisible(true);
        this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_label_imagenMouseClicked

    private void label_imagenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_imagenMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_imagenMouseEntered

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        int id = 0;
        String Nombre = txtnombre.getText();

        String sql = "";
        sql = " update caja set dinero = ?,dia=CURDATE() where Id=1";
        try {

            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, Nombre);

            int n = pst.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Caja actualizada");

            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | HeadlessException e) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, e);
        }

        
        mostrar_cash();

        txtnombre.setText("");

// TODO add your handling code here:
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnGananciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGananciasActionPerformed
        mostrar_ventas();
        Ganancias();
        try {
            statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT \n"
                    + "sum(Precio_Producto*ventas.cantidad) as Total_Ventas \n"
                    + "from producto INNER JOIN  ventas ON  producto.Id_Producto=ventas.Producto_Id_Producto \n"
                    + "Where ventas.Fecha_Venta=CURDATE()");
            while (rs.next()) {
                mostrar_ventas.setText("Total Ventas " + rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(tareas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGananciasActionPerformed

    private void btnguardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnguardarMouseEntered
        desactivar();          // TODO add your handling code here:
    }//GEN-LAST:event_btnguardarMouseEntered

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        this.ID = Integer.valueOf(jLabel2.getText());
        System.out.print(ID);        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened
//
    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped

//Da click al boton elegido
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyTyped

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped

        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyTyped

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered

        // TODO add your handling code here:
// TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseEntered

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreActionPerformed

    private void btnGananciasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGananciasMouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_btnGananciasMouseEntered

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
//        JOptionPane.showMessageDialog(null,"Total caja  ----------------- $"+totalcaja+" \n"
//                                         + "Total Ventas ---------------- $"+total_ventas+"\n"
//                                         + "Suma Venta y caja ----------- $"+(total_ventas+totalcaja)+"\n"
//                                         + "----------------------------------- \n"
//                                         + "Total pago a empleados ------ $"+total_empleados+"\n"
//                                         + "Total pago a proveedores ---- $"+total_proveedores+"\n"
//                                         + "Total Pagos ----------------- $"+(total_empleados+total_proveedores)+"\n"
//                                         + "----------------------------------- \n"
//                                         + "Total ganancias ------------- $"+Total_total);
        FacturasCaja obj=new  FacturasCaja();
                      obj.lbl1.setText(totalcaja.toString());
                      obj.lbl2.setText(total_ventas.toString());
                      obj.lbl3.setText(Double.toString(totalcaja+total_ventas));
                      obj.lbl4.setText(total_empleados.toString());
                      obj.lbl5.setText(total_proveedores.toString());
                      obj.lbl6.setText(Double.toString(total_empleados+total_proveedores));
                      obj.lbl7.setText(Total_total.toString());
 
                                    obj.setVisible(true); 

    }//GEN-LAST:event_btnresetActionPerformed

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        if (!txtnombre.getText().matches("^[0-9. ]{1,45}?$")) {
            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
            txtnombre.setText("");
        }
    }//GEN-LAST:event_txtnombreKeyReleased

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
            java.util.logging.Logger.getLogger(Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Caja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGanancias;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnreset;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel label_imagen;
    private javax.swing.JLabel mostrar_ventas;
    private javax.swing.JLabel selec_dinero;
    private javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables
}
