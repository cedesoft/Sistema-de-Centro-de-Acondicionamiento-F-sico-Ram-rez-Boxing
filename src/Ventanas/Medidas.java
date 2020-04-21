/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import static Ventanas.Membresia.decodeToImage;
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
public class Medidas extends javax.swing.JFrame {
 File fichero = null;
 private byte[] huella = null; 
 private JPanel contentPane;
 int ID =0;
 int ID_Cliente=0;
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
    public Medidas() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/back.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);
cargar();
id();


         
         mostrar();
         
        
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
    public void desactivar(){
    if(jTextField14.getText().length()==0||jTextField15.getText().length()==0||jTextField16.getText().length()==0||jTextField17.getText().length()==0||jTextField18.getText().length()==0){
    jButton2.setEnabled(false);
    }else{
    jButton2.setEnabled(true);
    }
    }
     public void limpiar(){
      
                 try {
                     String sql1= " update rutinas set Habilitado= '' where Habilitado='v'";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                    
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
    }
     public void mostrar(){
     try {
        
         
         
         
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                   "Med_Biceps", "Med_Pecho", "Med_Pierna", "Med_Pantorrila", "Med_Abdomen", "Fecha" 

                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                java.lang.String.class, java.lang.String.class,  java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });
         modelo=(DefaultTableModel) jTable1.getModel();
         String sql= "select Med_Biceps, Med_Pecho, Med_Pierna, Med_Pantorrila, Med_Abdomen, Fecha from historialmedidas where Id_Cliente="+ID_Cliente+" ";
        Object [] datos = new Object[6];
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
              datos[0]=rs.getString(1);
             datos[1]=rs.getString(2);
             datos[2]=rs.getString(3);
          
             datos[3]=rs.getString(4);
              datos[4]=rs.getString(5);
              datos[5]=rs.getString(6);
           
             
             
             
             
             modelo.addRow(datos);
         }
         
         
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(ejercicio.class.getName()).log(Level.SEVERE, null, ex);
     }
            
         
        
       
    }
     
     void cargar1() {
       
   
     String imagen_string = "";
     
         try {
             String sql = "SELECT imagen,Disciplina FROM cliente where Id_Cliente ="+ID_Cliente+"";
             System.out.print(ID_Cliente);
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                 imagen_string = rs.getString(1);
                
             }
             
             BufferedImage img =  decodeToImage(imagen_string);
            ImageIcon icon  = new ImageIcon(img);
             Icon icono  = new ImageIcon(icon.getImage().getScaledInstance(jLabel4.getWidth(), jLabel4.getHeight(), Image.SCALE_DEFAULT));
             jLabel4.setText(null);
             jLabel4.setIcon(icono);
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
            

        
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
                
        suma=0;
        
      for (int i = 0; i < jTable1.getRowCount(); i++) {
         
    suma=suma+(Integer)jTable1.getValueAt(i, 2);
          }
          
    }
        
   
    
     public void id()
    {
    String sql= "select Nombre_Producto from producto where Habilitado is null";
        try {
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
          
            while(rs.next()){
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
     public void visualizar(){
     jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                   "Clave_Ejercicio","Nombre_Ejercicio", "Musculo", "Dia", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                java.lang.String.class, java.lang.String.class,  java.lang.String.class,java.lang.String.class,java.lang.Boolean.class
             };
             
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });modelo=(DefaultTableModel) jTable1.getModel();
        
     }
    public void agregar(){
    
     try {
     
       
         
         
         
         
        
        
        Object [] datos = new Object[5];
         
       
         
         Statement s = conexion.createStatement();
        
         
     
             
              datos[4]=true;
           
             
             
             
             
             modelo.addRow(datos);
         
         
         
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
     }                
        
        
    }
   public void  cambio(){
    
    
        try {
            Statement s = conexion.createStatement();
            
           
          
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
                JOptionPane.showMessageDialog(null, "Registro guardado");
               
               
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
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        label_imagen = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

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
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, -1, -1));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 40, 10));
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 40, 10));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/avatar.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 130));

        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField14KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 140, -1));

        jLabel18.setText("Med_Biceps");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, -1, -1));

        jLabel14.setText("Med_Pecho");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));

        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField15KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 140, -1));

        jLabel15.setText("Med_Pierna");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, -1, -1));

        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField16KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField16KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField16KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 140, -1));

        jLabel16.setText("Med_Pantorrila");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, -1, -1));

        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField17KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField17KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 140, -1));

        jTextField18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField18KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField18KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 140, -1));

        jLabel17.setText("Med_Abdomen");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

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

        jScrollPane2.setViewportView(jScrollPane1);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 510, 300));

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

        jLabel19.setForeground(new java.awt.Color(250, 234, 128));
        jLabel19.setText("Medidas");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

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
int n=0;
        String Med_Biceps =jTextField14.getText();
                        
                        String Med_Pecho =jTextField15.getText();
                        String Med_Pierna =jTextField16.getText();
                        String Med_Pantorrila =jTextField17.getText();
                        String Med_Abdomen =jTextField18.getText();
        try {
                     String sql1= "update cliente set Med_Biceps=?, Med_Pecho=?, Med_Pierna=?, Med_Pantorrila=?, Med_Abdomen=? where Id_Cliente="+ID_Cliente+"";
                    
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setString(1, Med_Biceps);
                     pst.setString(2, Med_Pecho);
                     pst.setString(3,  Med_Pierna);
                       pst.setString(4, Med_Pantorrila);
                         pst.setString(5, Med_Abdomen);
                          System.out.print(sql1);
                  n= pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
   if(n>0){
     JOptionPane.showMessageDialog(null, "Registro guardado");
     String sql = "";
       sql = "INSERT INTO historialmedidas (Id_Cliente, Med_Biceps, Med_Pecho, Med_Pierna, Med_Pantorrila, Med_Abdomen,Fecha) values (?,?,?,?,?,?,CURDATE()) ";
        try {

            PreparedStatement pst = conexion.prepareStatement(sql);
             pst.setInt(1, ID_Cliente);
           pst.setString(2, Med_Biceps);
                     pst.setString(3, Med_Pecho);
                     pst.setString(4,  Med_Pierna);
                       pst.setString(5, Med_Pantorrila);
                         pst.setString(6, Med_Abdomen);
             
            pst.executeUpdate();
           
            
        } catch (SQLException | HeadlessException e) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, e);
        }
   }else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
 mostrar();
     
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        this.ID_Cliente = Integer.valueOf(jLabel7.getText());
        this.ID = Integer.valueOf(jLabel2.getText());
        System.out.print(ID);     
mostrar();  
cargar1();
// TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
desactivar();       // TODO add your handling code here:


    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
      // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jTextField14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyTyped
        // TODO add your handling code here:

        desactivar();
        char c = evt.getKeyChar();
        if((c<'0'||c>'9')&&c>'.')evt.consume();
    }//GEN-LAST:event_jTextField14KeyTyped

    private void jTextField15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyTyped

        desactivar();
        char c = evt.getKeyChar();
        if((c<'0'||c>'9')&&c>'.')evt.consume();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15KeyTyped

    private void jTextField16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16KeyPressed

    private void jTextField16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyTyped
        desactivar();
        char c = evt.getKeyChar();
        if((c<'0'||c>'9')&&c>'.')evt.consume();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16KeyTyped

    private void jTextField17KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyTyped
        desactivar();
        char c = evt.getKeyChar();
        if((c<'0'||c>'9')&&c>'.')evt.consume();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17KeyTyped

    private void jTextField18KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField18KeyTyped

        desactivar();
        char c = evt.getKeyChar();
        if((c<'0'||c>'9')&&c>'.')evt.consume();        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18KeyTyped

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        if (!jTextField14.getText().matches("^[0-9.]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField14.setText("");
        }
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        if (!jTextField15.getText().matches("^[0-9.]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField15.setText("");
        }
    }//GEN-LAST:event_jTextField15KeyReleased

    private void jTextField16KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyReleased
        if (!jTextField16.getText().matches("^[0-9.]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField16.setText("");
        }
    }//GEN-LAST:event_jTextField16KeyReleased

    private void jTextField17KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyReleased
        if (!jTextField17.getText().matches("^[0-9.]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField17.setText("");
        }
    }//GEN-LAST:event_jTextField17KeyReleased

    private void jTextField18KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField18KeyReleased
        if (!jTextField18.getText().matches("^[0-9.]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField18.setText("");
        }
    }//GEN-LAST:event_jTextField18KeyReleased
    
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
            java.util.logging.Logger.getLogger(Medidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Medidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Medidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Medidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Medidas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JLabel label_imagen;
    // End of variables declaration//GEN-END:variables
}
