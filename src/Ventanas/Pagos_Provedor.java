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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Lenovo
 */
public class Pagos_Provedor extends javax.swing.JFrame {
 File fichero = null;
 private byte[] huella = null; 
 private JPanel contentPane;
 int ID =0;
 int suma=0;
 boolean check1 = false;
 
    /** File fichero = null;
     * Creates new form Menu_1
     */ conectar cc = new conectar();
    Connection conexion = cc.conexion();
    Statement statement= null;
    Statement pst= null;
    DefaultTableModel modelo= new DefaultTableModel();
    int cantidad =0;
    public Pagos_Provedor() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/back.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);
jLabel2.setVisible(false);
cargar();
id();
id1();
jComboBox3.removeAllItems();
          jComboBox3.addItem ("Nombre_Producto");
          jComboBox3.addItem ("Nombre_Proveedor");
         
         
         
         
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                     "Nombre_Compañia","Nombre_Proveedor","Cantidad_Dinero", "Fecha_Pago","Cantidad_producto", "Nombre_Producto", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });modelo=(DefaultTableModel) jTable1.getModel();
        
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
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_DEFAULT));
                jLabel3.setText(null);
                jLabel3.setIcon(icono);
            

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
    
     public void eliminar(){
        DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
        int a = jTable1.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
        tb.removeRow(tb.getRowCount()-1);
        } 
        //cargaTicket();
    }
    
    public void desactivar2(){
    
    check1 = false;
        for (int i = 0; i < jTable1.getRowCount(); i++) {

 
 if((Boolean) jTable1.getValueAt(i, 6))
{
   
 check1 = true;
           
}

        // TODO add your handling code here:
    }                                     

    }
  
    public void desactivar1(){
    if(jTextField2.getText().length()==0){
    jButton1.setEnabled(false);
    }else{
    jButton1.setEnabled(true);
    }
    }
    
    public void desactivar(){
    if(jTextField1.getText().length()==0||jTextField3.getText().length()==0){
    jButton2.setEnabled(false);
    }else{
    jButton2.setEnabled(true);
    }
    }
    public void id1()
    {
    String sql= "select Nombre_Proveedor from proveedor";
        try {
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            jComboBox2.removeAllItems();
            while(rs.next()){
                jComboBox2.addItem (rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
     public void id()
    {
    String sql= "select Nombre_Producto from producto where Habilitado is null";
        try {
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            jComboBox1.removeAllItems();
            while(rs.next()){
                jComboBox1.addItem (rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public void agregar(){
    
     try {
         
                  String valor = (String) jComboBox1.getSelectedItem();

         
         String sql= "select proveedor.Nombre_Compañia, proveedor.Nombre_Proveedor, CURDATE(), Nombre_Producto  from producto INNER JOIN proveedor ON producto.Id_provedor = proveedor.Id_Provedor where producto.Nombre_Producto='"+valor+"'";
        Object [] datos = new Object[7];
         
        cantidad = Integer.parseInt(jTextField1.getText());
         int cantidad1 = Integer.parseInt(jTextField3.getText());
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
              datos[0]=rs.getString(1);
             datos[1]=rs.getString(2);
              datos[2]=""+cantidad1;
             datos[3]=rs.getString(3);
                datos[4]=""+cantidad+"";
                   
                   datos[5]=rs.getString(4);
                     datos[6]=true;
                
       
             
        
           
             
             
             
             
             modelo.addRow(datos);
         }
         
         
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
     }                
        
        
    }
    public void mostrar(){
     try {
         DefaultTableModel modelo= new DefaultTableModel();
         
         
         
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                     "Nombre_Compañia","Nombre_Proveedor","Cantidad_Dinero", "Fecha_Pago","Cantidad_producto", "Nombre_Producto", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class , java.lang.Boolean.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });
         modelo=(DefaultTableModel) jTable1.getModel();
         
        String sql1= "select proveedor.Nombre_Compañia, proveedor.Nombre_Proveedor, Cantidad_Dinero, Fecha_Pago, pagos_provedor.Cantidad_producto, producto.Nombre_Producto  from pagos_provedor INNER JOIN proveedor ON Proveedor_Id_Provedor = proveedor.Id_Provedor INNER JOIN producto ON producto.Id_Producto = pagos_provedor.Id_producto  ";
         
       Object [] datos = new Object[7];
         
        

         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql1);
         
       
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                 datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=false;   
                modelo.addRow(datos);
            }   
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(Administradores.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
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

        jPanel1.setBackground(new java.awt.Color(128, 128, 128));
        jPanel1.setMaximumSize(new java.awt.Dimension(900, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(250, 234, 128));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 40, 10));

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 80, -1));

        jButton1.setText("Buscar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, -1, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, -1, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Pago");

        jLabel4.setText("Cantidad");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jLabel5.setText("Producto");

        jLabel6.setText("Proveedor");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 410, 80));

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
        jScrollPane2.setViewportView(jTable1);

        jScrollPane1.setViewportView(jScrollPane2);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 640, 350));

        jButton2.setText("Agregar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, -1, -1));

        jButton3.setText("Pagar");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, -1, -1));

        jLabel3.setText("jLabel3");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 550));

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
        jLabel15.setText("Pagos");
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    
        
      
  
            
             
             
// TODO add your handling code here:
         
          
          
              
              agregar();
              
          
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
this.ID = Integer.valueOf(jLabel2.getText());
        
        mostrar();
      // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   String valor1 = (String) jComboBox3.getSelectedItem();
   if ("Nombre_Producto".equals(valor1) ){
       String valor = (String)jTextField2.getText();
       try {
         DefaultTableModel modelo= new DefaultTableModel();
         
         String sql1= "select proveedor.Nombre_Compañia, proveedor.Nombre_Proveedor, Cantidad_Dinero, Fecha_Pago, pagos_provedor.Cantidad_producto, producto.Nombre_Producto from pagos_provedor INNER JOIN proveedor ON Proveedor_Id_Provedor = proveedor.Id_Provedor INNER JOIN producto ON producto.Id_Producto = pagos_provedor.Id_producto WHere  producto.Nombre_Producto LIKE '"+valor+"%' and producto.Habilitado is null";
         
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                     "Nombre_Compañia","Nombre_Proveedor","Cantidad_Dinero", "Fecha_Pago","Cantidad_producto", "Nombre_Producto", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class , java.lang.Boolean.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });
         modelo=(DefaultTableModel) jTable1.getModel();
         
        
         
       Object [] datos = new Object[7];
         
        

         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql1);
         
       
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                 datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=false;   
                modelo.addRow(datos);
            }   
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(Administradores.class.getName()).log(Level.SEVERE, null, ex);
     }  
   }    
   if ("Nombre_Proveedor".equals(valor1) ){
       String valor = (String)jTextField2.getText();
       try {
         DefaultTableModel modelo= new DefaultTableModel();
         
         String sql1= "select proveedor.Nombre_Compañia, proveedor.Nombre_Proveedor, Cantidad_Dinero, Fecha_Pago, pagos_provedor.Cantidad_producto, producto.Nombre_Producto from pagos_provedor INNER JOIN proveedor ON Proveedor_Id_Provedor = proveedor.Id_Provedor INNER JOIN producto ON producto.Id_Producto = pagos_provedor.Id_producto WHere  proveedor.Nombre_Proveedor LIKE '"+valor+"%' and proveedor.Habilitado is null";
         
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                     "Nombre_Compañia","Nombre_Proveedor","Cantidad_Dinero", "Fecha_Pago","Cantidad_producto", "Nombre_Producto", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class , java.lang.Boolean.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });
         modelo=(DefaultTableModel) jTable1.getModel();
         
        
         
       Object [] datos = new Object[7];
         
        

         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql1);
         
       
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                 datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=false;   
                modelo.addRow(datos);
            }   
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(Administradores.class.getName()).log(Level.SEVERE, null, ex);
     } 
   }
//      if ("Nombre_Producto".equals(valor1) ){
//        String valor = (String)jTextField2.getText();
//        String sql= "select Nombre_Producto from producto  WHERE Nombre_Producto LIKE '"+valor+"%' and Habilitado is  null";
//        try {
//            Statement s = conexion.createStatement();
//            ResultSet rs = s.executeQuery(sql);
//            jComboBox1.removeAllItems();
//            while(rs.next()){
//                jComboBox1.addItem (rs.getString(1));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
//        }}        // TODO add your handling code here:
//      if ("Nombre_Proveedor".equals(valor1) ){
//        String valor = (String)jTextField2.getText();
//        String sql= "select Nombre_Proveedor from proveedor  WHERE Nombre_Proveedor LIKE '"+valor+"%' and Habilitado is  null";
//        try {
//            Statement s = conexion.createStatement();
//            ResultSet rs = s.executeQuery(sql);
//            jComboBox2.removeAllItems();
//            while(rs.next()){
//                jComboBox2.addItem (rs.getString(1));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
//        }}  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered

boolean check = false;
        for (int i = 0; i < jTable1.getRowCount(); i++) {

 
 if((Boolean) jTable1.getValueAt(i, 6))
{
   
 check = true;
           
}

        // TODO add your handling code here:
    }                                     
if(check){
jButton3.setEnabled(true);
}else{

jButton3.setEnabled(false);
}
            // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseEntered

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
desactivar1();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
int id_p=0;
 int n =0; 
 int id =0;
 for (int i = 0; i < jTable1.getRowCount(); i++) {
         try {
        
         
         String sql2= "select proveedor.Id_Provedor,  Id_Producto  from producto INNER JOIN proveedor ON producto.Id_provedor = proveedor.Id_Provedor where producto.Nombre_Producto='"+(String)jTable1.getValueAt(i, 5)+"'";
      
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql2);
         
         while(rs.next()){
         
             id_p=rs.getInt(1);
              id=rs.getInt(2);
         }} catch (SQLException ex) {
         Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
     }
        try {
           
            
            pst = conexion.createStatement();
            statement = conexion.createStatement();
           
            
            
           
                String sql = "INSERT INTO pagos_provedor (Proveedor_Id_Provedor, Cantidad_Dinero, Fecha_Pago, Cantidad_producto, Id_producto) VALUES ("+id_p+", "+(String)jTable1.getValueAt(i, 2)+",'"+(String)jTable1.getValueAt(i, 3)+"',"+(String)jTable1.getValueAt(i, 4)+","+id+")";
       

            PreparedStatement pst = conexion.prepareStatement(sql);
 
         
   
              
            
          
            

          if((Boolean) jTable1.getValueAt(i, 6)){
        
     n = pst.executeUpdate();
          }
        
     } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }  
 }
           if (n > 0) {
       JOptionPane.showMessageDialog(null, "Registro guardado");
              for (int i = 0; i < jTable1.getRowCount(); i++) {
                  
               
           try {
               String sql1 = "UPDATE producto SET Cantidad_Producto = Cantidad_Producto + "+jTable1.getValueAt(i, 4)+" where Nombre_Producto='"+jTable1.getValueAt(i, 5)+"'";
               PreparedStatement pst = conexion.prepareStatement(sql1);
               
               pst = conexion.prepareStatement(sql1);
               
               System.out.print(sql1);
               
               if((Boolean) jTable1.getValueAt(i, 6)){
                   
                   pst.executeUpdate();
                   
                    
               }          } catch (SQLException ex) {
               Logger.getLogger(Pagos_Provedor.class.getName()).log(Level.SEVERE, null, ex);
           }
 }      
           
           
           } else{
           
           JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);

           }
            
            
            
                   // TODO add your handling code here:
        
        eliminar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
desactivar();        // TODO add your handling code here:
desactivar2();

    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
   desactivar2();     // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
desactivar();  
char c = evt.getKeyChar();
if((c<'0'||c>'9'))evt.consume();        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        if (!jTextField3.getText().matches("^[0-9.]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField3.setText("");
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (!jTextField1.getText().matches("^[0-9.]{1,8}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        if (jTextField2.getText().matches("^[a-zA-Z ]{1,45}?$")) {
        } else {
            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres A-Z ");
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2KeyReleased
    
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
            java.util.logging.Logger.getLogger(Pagos_Provedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pagos_Provedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pagos_Provedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pagos_Provedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pagos_Provedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel label_imagen;
    // End of variables declaration//GEN-END:variables
}
