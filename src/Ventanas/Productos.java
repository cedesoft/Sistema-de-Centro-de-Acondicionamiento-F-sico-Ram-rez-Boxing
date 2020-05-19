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
public class Productos extends javax.swing.JFrame {
    int ID = 0;
 File fichero = null;
 private byte[] huella = null; 
 private JPanel contentPane;
    /** File fichero = null;
     * Creates new form Menu_1
     */ conectar cc = new conectar();
    Connection conexion = cc.conexion();
    Statement statement= null;
    boolean valor= false;
    
            Statement pst= null;
    public Productos() {
        
        initComponents();
        this.setLocationRelativeTo(null);
         ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/back.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);
cargar();
jComboBox2.removeAllItems();
          jComboBox2.addItem ("Nombre_Producto");
          jComboBox2.addItem ("Id_Producto");
         

          
        desactivar();
        desactivar1();
        mostrar();
      id();
        actualizar();
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
     
    public void id()
    {
    String sql= "select Nombre_Proveedor from proveedor";
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
    
    
    
     public void actualizar(){
    TableModel modelo=jTable1.getModel();
    modelo.addTableModelListener(new TableModelListener(){
        @Override
        
         public void tableChanged(TableModelEvent e){
             if(e.getType()==TableModelEvent.UPDATE){
             int columna=e.getColumn();
             int fila= e.getFirstRow();
             System.out.print(columna+""+fila);
             
             if(columna==1){
                 if (jTable1.getValueAt(fila, columna).toString().matches("^[a-zA-Z ]{1,45}?$")) {
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update producto set Nombre_Producto= ? where Id_Producto="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setString(1,(String) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }}else{
                     JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres");
                                mostrar();
                 }

             }
             
             if(columna==2){
                 
             if (jTable1.getValueAt(fila, columna).toString().matches("^[0-9]{1,3}?$")) {
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update producto set Cantidad_Producto= ? where Id_Producto="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setInt(1,(int) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }}else{
                 JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numerico");
                                mostrar();
             }
             }          
             
             
             
             if(columna==3){
              if (jTable1.getValueAt(fila, columna).toString().matches("^[0-9.]{1,7}?$")) {
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update producto set Precio_Producto= ? where Id_Producto="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setDouble(1,(double) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                  JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numerico");
                                mostrar();
              }}
             
             
             
             
             }
              
         
             
             
             
            }
        });
    
    
    
    
    }
     
    public void desactivar(){
    if(jTextField3.getText().length()==0||jTextField4.getText().length()==0||jTextField5.getText().length()==0){
    jButton2.setEnabled(false);
    }else{
    jButton2.setEnabled(true);
    }
    }
     public void desactivar1(){
    if(jTextField7.getText().length()==0){
    jButton4.setEnabled(false);
    }else{
    jButton4.setEnabled(true);
    }
    }
   
        
       public void eliminar(){
        
       

    


        try {
             
            
            pst = conexion.createStatement();
            statement = conexion.createStatement();
           
            
            
            for (int i = 0; i < jTable1.getRowCount(); i++) {
 String sql ="delete from producto where Id_Producto = "+jTable1.getValueAt(i, 0)+"";
 
 valor = (Boolean) jTable1.getValueAt(i, 5);
if(valor){
   
 
            pst.executeUpdate(sql);
}
 }
            
            
            
            
             } catch (SQLException ex) {
            Logger.getLogger(Provedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    mostrar();
             
    

    
    }
   
    
    public void mostrar(){
     try {
         DefaultTableModel modelo= new DefaultTableModel();
         
         
         
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                     "Id_Producto","Nombre_Producto", "Cantidad_Producto", "Precio_Producto", "Id_provedor", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                 java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class,java.lang.Integer.class,java.lang.Boolean.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });
         modelo=(DefaultTableModel) jTable1.getModel();
         String sql= "select Id_Producto, Nombre_Producto, Cantidad_Producto,Precio_Producto, Id_provedor from producto";
        Object [] datos = new Object[6];
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
              datos[0]=rs.getInt(1);
             datos[1]=rs.getString(2);
             datos[2]=rs.getInt(3);
          datos[3]=rs.getDouble(4);
          datos[4]=rs.getInt(5);
             
              datos[5]=false;
           
             
             
             
             
             modelo.addRow(datos);
         }
         
         
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField7 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
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

        jButton2.setText("Guardar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, -1, -1));

        jLabel3.setText("Categoria");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, -1));

        jLabel4.setText("Cantidad Producto");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, -1, -1));

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 140, -1));

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 140, -1));

        jButton3.setText("Eliminnar");
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
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, -1, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, -1, -1));

        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 70, -1));

        jButton4.setText("Buscar");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4MouseEntered(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, -1, -1));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 40, 10));

        jButton5.setText("Quitar filtro");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, -1, -1));

        jLabel5.setText("Precio Producto");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, -1));

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 140, -1));

        jLabel6.setText("Nombre Producto");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, -1, -1));

        jRadioButton1.setText("servicio");
        jRadioButton1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton1StateChanged(evt);
            }
        });
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, -1, -1));

        jLabel7.setText("Provedor");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, -1, -1));

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

        jScrollPane2.setViewportView(jScrollPane1);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 540, 320));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Refresco", "Agua", "Proteina" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(370, Short.MAX_VALUE)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 480, 90));

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
        jLabel15.setText("Productos");
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
 if(jRadioButton1.isSelected()){
        int id= 0;
        
 
 String Nombre_Producto = jTextField3.getText();
        
         String Precio_Producto = jTextField5.getText();
         



        
 String sql = "";
       sql = "INSERT INTO producto (Nombre_Producto, Cantidad_Producto, Precio_Producto,  Habilitado) VALUES (?, 0.0, ?,'s')";
        try {

            PreparedStatement pst = conexion.prepareStatement(sql);
        ;
            pst.setString(1, Nombre_Producto);
         
            pst.setString(2, Precio_Producto);
          
           
            
            int n = pst.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Registro guardado");
               
               
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | HeadlessException e) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, e);
        }
         
 mostrar();
         
 actualizar();
         
         jTextField3.setText("");
         jTextField4.setText("");
         jTextField3.setText("");
         jTextField5.setText("");

}else{
        int id= 0;
 
 String Nombre_Producto = jTextField3.getText();
         String Cantidad_Producto = jTextField4.getText();
         String Precio_Producto = jTextField5.getText();
         

 String sql1= "select Id_provedor from proveedor where Nombre_Proveedor= '"+(String) jComboBox1.getSelectedItem()+"'";
        try {
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            
            while(rs.next()){
              id=(  rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }

        
 String sql = "";
       sql = "INSERT INTO producto (Nombre_Producto, Cantidad_Producto, Precio_Producto, Id_provedor, categoria) VALUES (?, ?, ?, ?, ?)";
        try {

            PreparedStatement pst = conexion.prepareStatement(sql);
        ;
            pst.setString(1, Nombre_Producto);
            pst.setString(2, Cantidad_Producto);
            pst.setString(3, Precio_Producto);
            pst.setInt(4, id);
             pst.setString(5, (String) jComboBox3.getSelectedItem());
           
            
            int n = pst.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Registro guardado");
               
               
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | HeadlessException e) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, e);
        }
         
 mostrar();
         
 actualizar();
         
         jTextField3.setText("");
         jTextField4.setText("");
         jTextField3.setText("");
         jTextField5.setText("");
 }    
         
        
// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
     try {
         String sql="";
         
        
         String valor = (String) jComboBox2.getSelectedItem();
         
         
         DefaultTableModel modelo= new DefaultTableModel();
         
         
         
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
                 null,
                 new String [] {
                     //Defines TODOS los nombres de las columnas que tendrá la tabla
                     "Id_Producto","Nombre_Producto", "Cantidad_Producto", "Precio_Producto", "Id_provedor", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                 java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class,java.lang.Integer.class,java.lang.Boolean.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });
         modelo=(DefaultTableModel) jTable1.getModel();
         boolean verf = true;
         
          Pattern pat = Pattern.compile("^[0-9]$*");
        Matcher mat = pat.matcher(jTextField7.getText());
        if(mat.find()){
         if ("Id_Producto".equals(valor) ){
             verf=true;
             int id = Integer.parseInt(jTextField7.getText());
             sql= "select Id_Producto, Nombre_Producto, Cantidad_Producto,Precio_Producto, Id_provedor from producto WHERE Id_Producto LIKE '"+id+"%'";
         
         
         }
         else{
         verf=false;
         }
        
        }else{
        verf=false;
        
         if ("Nombre_Producto".equals(valor) ){
            verf=true;
              String nombre = jTextField7.getText();
             sql= "select Id_Producto, Nombre_Producto, Cantidad_Producto,Precio_Producto, Id_provedor from producto WHERE Nombre_Producto LIKE '"+nombre+"%'";
         } 
        

        
        
        }
          
         if(verf){
         
        Object [] datos = new Object[6];
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
             datos[0]=rs.getInt(1);
             datos[1]=rs.getString(2);
             datos[2]=rs.getInt(3);
          datos[3]=rs.getDouble(4);
          datos[4]=rs.getInt(5);
             
              datos[5]=false;
           
             
             
             
             modelo.addRow(datos);
         }
         
         jTable1.setModel(modelo);
         
         
         
       
         jButton4.setEnabled(false);
         jTextField7.setText(null);
         desactivar();
         
         }
         
         // TODO add your handling code here:
     } catch (SQLException ex) {
         Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
     }
     actualizar();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
int dialog=JOptionPane.YES_NO_OPTION;
        int result=JOptionPane.showConfirmDialog(null, "¿Desea eliminar el producto?","Si",dialog);
        if (result==0) {
        eliminar();
        }
        actualizar();
      // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
desactivar();          // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseEntered
desactivar1();         // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseEntered

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

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
 
        boolean check = false;
        for (int i = 0; i < jTable1.getRowCount(); i++) {

 
 if((Boolean) jTable1.getValueAt(i, 5))
{
   
 check = true;
           
}
 }

if(check){
jButton3.setEnabled(true);
}else{

jButton3.setEnabled(false);
}
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseEntered

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
mostrar();
actualizar();// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
desactivar();  
char c = evt.getKeyChar();
if((c<'0'||c>'9')&&c>'.')evt.consume();        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        if (!jTextField3.getText().matches("^[a-zA-Z ]{1,45}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres de la a-z");
            jTextField3.setText("");
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        if (!jTextField4.getText().matches("^[0-9]{1,3}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField4.setText("");
        }
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        if (!jTextField5.getText().matches("^[0-9.]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField5.setText("");
        }
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased
        if (jTextField7.getText().matches("^[a-zA-Z ]{1,45}?$") || jTextField7.getText().matches("^[0-9]{1,4}?$")) {
        } else {
            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres A-Z o numero");
            jTextField7.setText("");
        }
    }//GEN-LAST:event_jTextField7KeyReleased

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1StateChanged

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed
    
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
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Productos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel label_imagen;
    // End of variables declaration//GEN-END:variables
}
