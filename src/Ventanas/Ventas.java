/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;


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
public class Ventas extends javax.swing.JFrame {
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
    public Ventas() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/back.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);
cargarf();
id();

         
         
         
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                     "Id_Producto","Nombre_Producto", "Cantidad_Producto", "Precio_Producto", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                 java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class,java.lang.Boolean.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });modelo=(DefaultTableModel) jTable1.getModel();
        
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
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_DEFAULT));
                jLabel7.setText(null);
                jLabel7.setIcon(icono);
            

        } catch (SQLException ex) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
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

 
 if((Boolean) jTable1.getValueAt(i, 4))
{
   
 check1 = true;
           
}

        // TODO add your handling code here:
    }                                     

    }
    public void suma(){
                 String valor = (String) jComboBox1.getSelectedItem();

        suma=0;
        
      for (int i = 0; i < jTable1.getRowCount(); i++) {
          if((valor).equals((String)jTable1.getValueAt(i, 1))&&(Boolean)jTable1.getValueAt(i, 4)==true){
          
    suma=suma+(Integer)jTable1.getValueAt(i, 2);
          }}
          
    }
    public void desactivar1(){
    if(jTextField2.getText().length()==0){
    jButton1.setEnabled(false);
    }else{
    jButton1.setEnabled(true);
    }
    }
    
    public void desactivar(){
    if(jTextField1.getText().length()==0){
    jButton2.setEnabled(false);
    }else{
    jButton2.setEnabled(true);
    }
    }
    
     public void id()
    {
    String sql= "select Nombre_Producto from producto where Habilitado is null or Habilitado = 's'";
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

         
         String sql= "select Id_Producto, Nombre_Producto,Precio_Producto from producto where Nombre_Producto='"+valor+"'";
        Object [] datos = new Object[5];
         
        cantidad = Integer.parseInt(jTextField1.getText());
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
              datos[0]=rs.getInt(1);
             datos[1]=rs.getString(2);
              datos[2]=cantidad;
             datos[3]=rs.getDouble(3);
       
             
              datos[4]=true;
           
             
             
             
             
             modelo.addRow(datos);
         }
         
         
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
     }                
        
        
    }
    void guardar_imagen(String imagen, String nombre, byte[] huella) {
        String sql = "";
       sql = "INSERT INTO empleado (huella, imagen) VALUES (?, ?)";
        try {

            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setBytes(1, huella);
            pst.setString(2, imagen);
             
            
            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Registro guardado");
               
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | HeadlessException e) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, e);
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
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
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
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, -1, -1));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 40, 10));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

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
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 80, -1));

        jLabel1.setText("Se pago con");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, -1, -1));

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

        jLabel3.setText("Nombre");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, -1, -1));

        jButton3.setText("Vender");
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
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, -1, -1));

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 80, -1));

        jLabel4.setText("Cantidad");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, -1, -1));
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 170, 20));
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 170, 20));

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
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jScrollPane1);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 540, 300));

        jLabel7.setText("jLabel7");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 550));

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
        jLabel15.setText("Ventas");
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
this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_label_imagenMouseClicked
    
    private void label_imagenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_imagenMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_imagenMouseEntered

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    int cantidad1=0;
          int cantidad2=0;
          String comprobar="";
          
          
           try {
         String valor = (String) jComboBox1.getSelectedItem();
         
         String sql= "select Habilitado from producto where Nombre_Producto='"+valor+"'";
      
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
         
             comprobar=rs.getString(1);
         }} catch (SQLException ex) {
         Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
     }
      if("s".equals(comprobar)){
          agregar();
      }  
      else{try {
              String valor = (String) jComboBox1.getSelectedItem();
              
              String sql= "select Cantidad_Producto from producto where Nombre_Producto='"+valor+"'";
              
              
              
              
              Statement s = conexion.createStatement();
              ResultSet rs = s.executeQuery(sql);
              
              while(rs.next()){
                  cantidad2=rs.getInt(1);}
              cantidad1 = Integer.parseInt(jTextField1.getText());
              suma();
// TODO add your handling code here:
          } catch (SQLException ex) {
              Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          if(cantidad1<=cantidad2 &&cantidad2>=suma&&(suma+cantidad1)<=cantidad2){
              
              agregar();
              
          }else{
              
              JOptionPane.showMessageDialog(null, "No hay mas de ese producto");
          }}  
       DecimalFormat decf = new DecimalFormat("#,##0");
      Double suma1 = 0.0;
     String valor1 = "";
      String valor2 = "";
        for (int i = 0; i < jTable1.getRowCount(); i++) {
             if((Boolean) jTable1.getValueAt(i, 4)){ 
 valor1=""+jTable1.getValueAt(i, 2)+"";
 valor2=""+jTable1.getValueAt(i, 3)+"";
 
             suma1 +=Double.parseDouble(valor1)* Double.parseDouble(valor2);}}
        jLabel6.setText("El total es: "+(suma1)+"");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
this.ID = Integer.valueOf(jLabel2.getText());
        System.out.print(ID);
      // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  String valor = (String)jTextField2.getText();
        String sql= "select Nombre_Producto from producto  WHERE Nombre_Producto LIKE '"+valor+"%' and Habilitado is  null";
        try {
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            jComboBox1.removeAllItems();
            while(rs.next()){
                jComboBox1.addItem (rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered

boolean check = false;
        for (int i = 0; i < jTable1.getRowCount(); i++) {

 
 if((Boolean) jTable1.getValueAt(i, 4))
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
try {
        int n =0;     
            int cantidad2=0;
               String comprobar="";
            pst = conexion.createStatement();
            statement = conexion.createStatement();
           
            
            
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String sql = "INSERT INTO ventas (Producto_Id_Producto, Cantidad, Fecha_Venta, Empleado_Id_Empleado, habilitado) VALUES (?, ?, CURDATE(),?, 'v')";
       

            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setInt(1, (Integer)jTable1.getValueAt(i, 0));
            pst.setInt(2, (Integer)jTable1.getValueAt(i, 2));
          
            pst.setInt(3, ID);
if((Boolean) jTable1.getValueAt(i, 4)){
   try{
 String sql1= "select Cantidad_Producto from producto where Nombre_Producto='"+(String)jTable1.getValueAt(i, 1)+"'";
      
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql1);
         
         while(rs.next()){
             cantidad2=rs.getInt(1);}
         
// TODO add your handling code here:
     } catch (SQLException ex) {
         Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
     }

          
          
           try {
         String valor = (String) jComboBox1.getSelectedItem();
         
         String sql2= "select Habilitado from producto where Nombre_Producto='"+valor+"'";
      
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql2);
         
         while(rs.next()){
         
             comprobar=rs.getString(1);
         }} catch (SQLException ex) {
         Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
     }
   if("s".equals(comprobar)){
              n = pst.executeUpdate();

      }  
      else{
     if((Integer)jTable1.getValueAt(i, 2)<=cantidad2){
     
     n = pst.executeUpdate();
     }else{
     
     JOptionPane.showMessageDialog(null, "No hay mas de ese producto");  
     }
   }   
           
}
 }
           if (n > 0) {
           eliminar();
           
           Facturas_Masivas obj=new  Facturas_Masivas() ;
obj.jLabel2.setText(Integer.toString(ID));


obj.setVisible(true);
           
           
           
           } else{
           
           JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
   
           
           }
            
            
            
             } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (!jTextField1.getText().matches("^[0-9]+([.][0-9]+)?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        if (!jTextField2.getText().matches("^[a-zA-Z ]{1,45}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
if (!jTextField3.getText().matches("^[0-9]+([.][0-9]+)?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField3.setText("");
        }
        Double suma = 0.0;
         String valor1 = "";
      String valor2 = "";
        for (int i = 0; i < jTable1.getRowCount(); i++) {
             if((Boolean) jTable1.getValueAt(i, 4)){ 
 valor1=""+jTable1.getValueAt(i, 2)+"";
 valor2=""+jTable1.getValueAt(i, 3)+"";
     
 suma +=Double.parseDouble(valor1)* Double.parseDouble(valor2);} } 
       if( jTextField3.getText().length()>0){
        if(Double.parseDouble(jTextField3.getText())>= suma)
        {
jLabel5.setText("El cambio es: "+(Double.parseDouble(jTextField3.getText())-suma)+"");
         jLabel6.setText("El total es: "+(suma)+"");
        }
        
// TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyReleased
    }
    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked


    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyPressed
    
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
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel label_imagen;
    // End of variables declaration//GEN-END:variables
}
