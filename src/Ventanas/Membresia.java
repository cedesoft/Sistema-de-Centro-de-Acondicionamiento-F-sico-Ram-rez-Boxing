/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import static Ventanas.subir_imagen.decodeToImage;
import clases.LeerHuella;
import clases.LeerHuella_Administradores;
import clases.LeerHuella_Clientes;

import java.awt.Desktop;
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
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Lenovo
 */
public class Membresia extends javax.swing.JFrame {

    /**
     * Creates new form Menu_1
     */ conectar cc = new conectar();
    Connection conexion = cc.conexion();
   
    int ID=0;
    int ID_Cliente=0;
  
  
 int diferencia=0;
   boolean ver= false;
    public Membresia() {
        
        
        
        initComponents();
        this.setLocationRelativeTo(null);
        
         ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/back.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);
cargar();
jLabel2.setVisible(false);
jLabel7.setVisible(false);
jComboBox1.removeAllItems();
 jComboBox1.addItem ("Mensual");
          jComboBox1.addItem ("Bimestral");
          jComboBox1.addItem ("Trimestral");
          jComboBox1.addItem ("Mensual Niños");
        
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
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabel4.getWidth(), jLabel4.getHeight(), Image.SCALE_DEFAULT));
                jLabel4.setText(null);
                jLabel4.setIcon(icono);
            

        } catch (SQLException ex) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

      public void mostrar(){
     try {
         DefaultTableModel modelo= new DefaultTableModel();
         
         
         
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                     "Id_Cliente", "Nombre", "Apellidos", "Telefono", "Enfermedad", "Disciplina", "Med_Biceps", "Med_Pecho", "Med_Pierna", "Med_Pantorrila", "Med_Abdomen"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
 java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, Double.class, Double.class, Double.class, Double.class, Double.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });
         modelo=(DefaultTableModel) jTable1.getModel();
         String sql= "select Id_Cliente, Nombre, Apellidos, Telefono, Enfermedad, Disciplina, Med_Biceps, Med_Pecho, Med_Pierna, Med_Pantorrila, Med_Abdomen from cliente where Id_Cliente ="+ID_Cliente+"";
         DecimalFormat formatea = new DecimalFormat("###,###.##");
       Object [] datos = new Object[11];
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
             datos[0]=rs.getString(1);
             datos[1]=rs.getString(2);
             datos[2]=rs.getString(3);
             datos[3]=rs.getString(4);
             datos[4]=rs.getString(5);
             datos[5]=rs.getString(6);
 datos[6]=rs.getDouble("Med_Biceps");
datos[7]=rs.getDouble("Med_Pecho");
datos[8]=rs.getDouble(9);
datos[9]=rs.getDouble(10);
datos[10]=rs.getDouble(11);


             
             
             
             modelo.addRow(datos);
         }
         
         
         jTable1.setModel(modelo);
     }   catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
            
         
    
    }
     void cargar1() {
       
   
     String imagen_string = "";
     
         try {
             String sql = "SELECT imagen FROM cliente where Id_Cliente ="+ID_Cliente+"";
             System.out.print(ID_Cliente);
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                 imagen_string = rs.getString(1);
                 
             }
             
             BufferedImage img =  decodeToImage(imagen_string);
            ImageIcon icon  = new ImageIcon(img);
             Icon icono  = new ImageIcon(icon.getImage().getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_DEFAULT));
             jLabel3.setText(null);
             jLabel3.setIcon(icono);
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
            

        
    }
    
     void diferencia() {
       
  
     
     
         try {
             String sql = "SELECT DATEDIFF(Membresia, CURDATE() ) FROM cliente where Id_Cliente ="+ID_Cliente+"";
             System.out.print(ID_Cliente);
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getInt(1);
                 
             }
             if(diferencia>0){
                 ver=true;
jLabel1.setText("Te quedan "+diferencia+" dias de tu membresia");
}else{
ver=false;
jLabel1.setText("Tu membresia ha terminado");
}
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
            

        
    }
    
     
     public static BufferedImage decodeToImage(String imageString) {
   byte[] imageByte=null;
          
     BufferedImage image = null;
     BASE64Decoder decoder= null;
               ByteArrayInputStream bis = null;
               String imagen_string = null;

        
        try {
             decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
             bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (IOException e) {
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
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        label_imagen = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

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
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 40, 10));
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 40, 10));

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
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 220, 840, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Su membresia es de :");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));

        jButton1.setText("Renovar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/avatar.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 130));

        jLabel13.setText("Membresia");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        jLabel4.setText("jLabel4");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 550));

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
        jLabel15.setText("Membresia");
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
            Menu_1 obj = new Menu_1();
            obj.jLabel2.setText(Integer.toString(ID));
obj.setVisible(true);
this.setVisible(false);          // TODO add your handling code here:
    }//GEN-LAST:event_label_imagenMouseClicked
    
    private void label_imagenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_imagenMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_imagenMouseEntered

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
     this.ID_Cliente = Integer.valueOf(jLabel7.getText());
        this.ID = Integer.valueOf(jLabel2.getText());
        System.out.print(ID);
        mostrar();
cargar1();
diferencia();

// TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowIconified

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       String Membresia = (String) jComboBox1.getSelectedItem();
                     int id=0;
                     double precio=0;
         try {
             String sql = "";
             
             sql = " call ActualizarClientes('"+Membresia+"',"+ID_Cliente+","+ver+")";
             
             
             PreparedStatement pst = conexion.prepareStatement(sql);
             
             
             
             
             int n = pst.executeUpdate();
             
             if (n > 0) {
                 try {
                     
                     
                     
                     
                     
                     
                     String sql2= "select Id_Producto, Precio_Producto from producto WHERE Nombre_Producto = '"+Membresia+"'";
                     
                     
                     
                     
                     Statement s = conexion.createStatement();
                     ResultSet rs = s.executeQuery(sql2);
                     while(rs.next()){
                         id=rs.getInt(1);
                         precio=rs.getDouble(2);}
                     
                     
                     
                     String sql3 = " call insertarVentas(?,?)";
                     
                     
                     PreparedStatement pst1 = conexion.prepareStatement(sql3);
                     pst1.setInt(1,ID);
                     pst1.setInt(2,id);
                     
                     pst1.executeUpdate();
                     
                     
                     
                     
                     
                     
                     
// TODO add your han
Facturas obj=new  Facturas() ;
obj.jLabel2.setText(Integer.toString(ID));
obj.jLabel4.setText(Integer.toString((int) precio));
obj.jLabel7.setText(Membresia);
obj.jLabel10.setText(Integer.toString((int) precio));
obj.jLabel11.setText((String)jTable1.getValueAt(0, 1)+" "+(String)jTable1.getValueAt(0, 2));
obj.setVisible(true);
diferencia();
// TODO add your handling code here:
                 } catch (SQLException ex) {
                     Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
                 }}
             else{
             
             JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
          
             }
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
subir_imagen_clentes obj=new  subir_imagen_clentes() ;
                      obj.jLabel3.setText(Integer.toString(ID));
 
        obj.setVisible(true); 
        this.setVisible(false);      
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseClicked
    
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
            java.util.logging.Logger.getLogger(Membresia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Membresia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Membresia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Membresia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Membresia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel label_imagen;
    // End of variables declaration//GEN-END:variables
}
