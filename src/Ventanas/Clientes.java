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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
public class Clientes extends javax.swing.JFrame {
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
    int id=0;
                double precio=0.0;
     int n=0;
    
            Statement pst= null;
    public Clientes() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        
         ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/back.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);
fot = new ImageIcon(getClass().getResource("/imagenes1/avatar.png"));
        icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_DEFAULT));
jLabel1.setIcon(icono);
this.repaint();
 jTextField6.setEnabled(false);
cargar();
jComboBox2.removeAllItems();
          jComboBox2.addItem ("Nombre");
          jComboBox2.addItem ("Id_Usuario");
          jComboBox2.addItem ("Apellido");
          jComboBox1.removeAllItems();
          jComboBox1.addItem ("Mensual");
          jComboBox1.addItem ("Bimestral");
          jComboBox1.addItem ("Trimestral");
          jComboBox1.addItem ("Mensual Niños");

        desactivar();
        desactivar1();
        mostrar();
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
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabel20.getWidth(), jLabel20.getHeight(), Image.SCALE_DEFAULT));
                jLabel20.setText(null);
                jLabel20.setIcon(icono);
            

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
                     String sql1= " update cliente set Nombre= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setString(1,(String) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                     JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres");
                                mostrar();
                 }
             }
             
             
             if(columna==2){
                if (jTable1.getValueAt(fila, columna).toString().matches("^[a-zA-Z ]{1,45}?$")) { 
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update cliente set Apellidos= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setString(1,(String) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                    JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres");
                                mostrar();
                }}
             if(columna==3){
                 if (jTable1.getValueAt(fila, columna).toString().matches("^[0-9 ]{1,15}?$")) { 
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update cliente set Telefono= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     String valor = (String) jTable1.getValueAt(fila,columna);
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setString(1,(String) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                     JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
                                mostrar();
                 }}
             if(columna==4){
                 if (jTable1.getValueAt(fila, columna).toString().matches("^[a-zA-Z ]{1,45}?$")) { 
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update cliente set Enfermedad= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setString(1,(String) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                    JOptionPane.showMessageDialog(null, "Solo se aceptan letras");
                                mostrar(); 
                 }}
             
             if(columna==5){
                 if (jTable1.getValueAt(fila, columna).toString().matches("^[a-zA-Z ]{1,45}?$")) { 
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update cliente set Disciplina= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setString(1,(String) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                     JOptionPane.showMessageDialog(null, "Solo se aceptan letras");
                                mostrar(); 
                 }}
             
             
             if(columna==6){
                 if (jTable1.getValueAt(fila, columna).toString().matches("^[0-9]+([.][0-9]+)?$")) { 
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update cliente set Med_Biceps= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setDouble(1,(Double) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                     JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
                                mostrar(); 
                 }}
             if(columna==7){
                if (jTable1.getValueAt(fila, columna).toString().matches("^[0-9]+([.][0-9]+)?$")) {  
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update cliente set Med_Pecho= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setDouble(1,(Double) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                 JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
                                mostrar();    
                }}
             if(columna==8){
                 if (jTable1.getValueAt(fila, columna).toString().matches("^[0-9]+([.][0-9]+)?$")) {  
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update cliente set Med_Pierna= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setDouble(1,(Double) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                     JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
                                mostrar();   
                 }}
              if(columna==9){
                  if (jTable1.getValueAt(fila, columna).toString().matches("^[0-9]+([.][0-9]+)?$")) {  
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update cliente set Med_Pantorrila= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setDouble(1,(Double) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                    JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
                                mostrar();     
                  }}
               if(columna==10){
                   if (jTable1.getValueAt(fila, columna).toString().matches("^[0-9]+([.][0-9]+)?$")) {  
             int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
if(resp==JOptionPane.YES_OPTION){
                  
                 try {
                     String sql1= " update cliente set Med_Abdomen= ? where Id_Cliente="+jTable1.getValueAt(fila,0)+"";
                     
                     PreparedStatement pst = conexion.prepareStatement(sql1);
                     pst.setDouble(1,(Double) jTable1.getValueAt(fila,columna) );
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
             }else{
                    JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
                                mostrar();        
                   }}
            
              
         
             
             }
             
            }
        });
    }
    public void desactivar(){
    if(jTextField3.getText().length()==0||jTextField4.getText().length()==0||jTextField5.getText().length()==0||jTextField8.getText().length()==0||jTextField14.getText().length()==0||jTextField15.getText().length()==0||jTextField16.getText().length()==0||jTextField17.getText().length()==0||jTextField18.getText().length()==0){
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
 String sql ="delete from cliente where Id_Cliente= "+(String)jTable1.getValueAt(i, 0)+";";
 

if((Boolean) jTable1.getValueAt(i, 11)){
   
 
            pst.executeUpdate(sql);
}
 }
            
            
            
            
             } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
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
                     "Id_Cliente", "Nombre", "Apellidos", "Telefono", "Enfermedad", "Disciplina", "Med_Biceps", "Med_Pecho", "Med_Pierna", "Med_Pantorrila", "Med_Abdomen", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
 java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, Double.class, Double.class, Double.class, Double.class, Double.class, java.lang.Boolean.class
             };
             
             //Función que asignará el tipo de campo que asignaste previamente
             public Class getColumnClass(int columnIndex) {
                 return types [columnIndex];
             }
         });
         modelo=(DefaultTableModel) jTable1.getModel();
         String sql= "select Id_Cliente, Nombre, Apellidos, Telefono, Enfermedad, Disciplina, Med_Biceps, Med_Pecho, Med_Pierna, Med_Pantorrila, Med_Abdomen from cliente ";
         DecimalFormat formatea = new DecimalFormat("###,###.##");
       Object [] datos = new Object[12];
         
        
         
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
datos[11]=false;

             
             
             
             modelo.addRow(datos);
         }
         
         
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
     }
            
         
    
    }
    
  
  
    
    
    
    
    
    
    
    void guardar_imagen(String imagen, byte[] huella, String nombre, String apellidos, String Telefono, String enfermedad, String Disciplina, String Med_Biceps, String Med_Pecho,String Med_Pierna,String Med_Pantorrila,String Med_Abdomen, String Membresia ) throws SQLException {
       
           
                String sql = "";
                
                sql = " call InsertarClientes(?,?,?,?,?,?,?,?,?,?,?,?,?)";
               
                    
                    PreparedStatement pst = conexion.prepareStatement(sql);
                    
                    pst.setString(1, imagen);
                    pst.setBytes(2, huella);
                    
                    pst.setString(3, nombre);
                    pst.setString(4, apellidos);
                    pst.setString(5, Telefono);
                     if(jRadioButton1.isSelected()){
                    pst.setString(6, enfermedad);}else{
                      pst.setString(6, "N/A");
                     }
                    pst.setString(7, Disciplina);
                    pst.setString(8, ""+Med_Biceps+"");
                    pst.setString(9, ""+Med_Pecho+"");
                    pst.setString(10, ""+Med_Pierna+"");
                    pst.setString(11, ""+Med_Pantorrila+"");
                    pst.setString(12, ""+Med_Abdomen+"");
                    pst.setString(13, Membresia);
                    
                    
                    
                    int n = pst.executeUpdate();
                   
                    if (n > 0) {
                        
                        
                            
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
                        
                        
                        
                        
                        
                        int n1 =pst1.executeUpdate();
                        mostrar();
                        
                        
                        jTextField3.setText("");
                        jTextField4.setText("");
                        jTextField5.setText("");
                        jTextField6.setText("");
                        jTextField8.setText("");
                        jTextField14.setText("");
                        jTextField15.setText("");
                         jTextField16.setText("");
                          jTextField17.setText("");
                           jTextField18.setText("");
                        
                       
// TODO add your han
                         Facturas obj=new  Facturas() ;
                      obj.jLabel2.setText(Integer.toString(ID));
                      obj.jLabel4.setText(Integer.toString((int) precio));
                      obj.jLabel7.setText(Membresia);
                      obj.jLabel10.setText(Integer.toString((int) precio));
                      obj.jLabel11.setText(nombre+" "+apellidos);
 
                                    obj.setVisible(true); 
                        System.out.println("Registro guardado");
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                
                
                
            
            
            
           
        
         mostrar();
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
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextField15 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField7 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
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
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, -1, -1));

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
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, -1, -1));

        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField15KeyTyped(evt);
            }
        });

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField8KeyReleased(evt);
            }
        });

        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField17KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField17KeyTyped(evt);
            }
        });

        jTextField18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField18KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField18KeyTyped(evt);
            }
        });

        jLabel8.setText("Nombre");

        jLabel18.setText("Med Biceps");

        jLabel16.setText("MedPantorrila");

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

        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField14KeyTyped(evt);
            }
        });

        jLabel13.setText("Membresia");

        jLabel14.setText("Med Pecho");

        jLabel17.setText("Med Abdomen");

        jLabel15.setText("Med Pierna");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jRadioButton1.setText("Enfermredad");
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

        jLabel9.setText("Apellidos");

        jLabel10.setText("Telefono");

        jLabel12.setText("Disciplina");

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });

        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(58, 58, 58)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jLabel14)
                            .addGap(25, 25, 25)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(58, 58, 58)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jLabel15)
                            .addGap(24, 24, 24)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(108, 108, 108))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jLabel8)
                    .addGap(63, 63, 63)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(jLabel18)
                    .addGap(24, 24, 24)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(jLabel13)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jLabel12))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel17))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 670, -1));
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 123, 122));

        jButton1.setText("Abrir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, -1, -1));

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
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, -1, -1));

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

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 840, 320));

        jLabel20.setText("jLabel20");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 550));

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
        jLabel19.setText("Clientes");
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
JFileChooser file = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.jpg", "jpg");
        file.setFileFilter(filtro);

        int seleccion = file.showOpenDialog(contentPane);
        //Si el usuario, pincha en aceptar
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            //Seleccionamos el fichero
            fichero = file.getSelectedFile();
            //Ecribe la ruta del fichero seleccionado en el campo de texto
            jTextField1.setText(fichero.getAbsolutePath());
            ImageIcon icon = new ImageIcon(fichero.toString());
            System.out.println(fichero.getName());
            Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_DEFAULT));
            jLabel1.setText(null);
            jLabel1.setIcon(icono);      
        }
actualizar();
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                                 DecimalFormat formato = new DecimalFormat("#####.##");                          
                        String nombre = jTextField3.getText();
                        String apellidos = jTextField4.getText();
                        String Telefono = jTextField5.getText();
                        
                        
                        String enfermedad =jTextField6.getText();
                        String Disciplina =jTextField8.getText();
                       String Med_Biceps =jTextField14.getText();
                        
                        String Med_Pecho =jTextField15.getText();
                        String Med_Pierna =jTextField16.getText();
                        String Med_Pantorrila =jTextField17.getText();
                        String Med_Abdomen =jTextField18.getText();
                        String Membresia = (String) jComboBox1.getSelectedItem();
                        
                        System.out.print(Med_Biceps);
                        
                        if (!jTextField1.getText().equals("")) {
                            try {
                                // TODO add your handling code here:
                                BufferedImage img = ImageIO.read(new File(fichero.toString()));
                                String image_string = encodeToString(img);
                                LeerHuella lh = new LeerHuella(null, true);
                                lh.setVisible(true);
                                huella=lh.getPlantillaHuella().serialize();
                                guardar_imagen(image_string, huella, nombre, apellidos, Telefono, enfermedad, Disciplina, Med_Biceps, Med_Pecho, Med_Pierna, Med_Pantorrila, Med_Abdomen, Membresia);
                            lh.stop();
                            } catch (IOException ex) {
                                Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
                            }        catch (SQLException ex) {
                                         Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                                     }
                            
                           
                           

                        }
                        
                         
                         
       
        actualizar();
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
                      "Id_Cliente", "Nombre", "Apellidos", "Telefono", "Enfermedad", "Disciplina", "Med_Biceps", "Med_Pecho", "Med_Pierna", "Med_Pantorrila", "Med_Abdomen", "Eliminar"
                 }
         ) {
             Class[] types = new Class [] {
                 //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                 //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                  java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, Double.class, Double.class, Double.class, Double.class, Double.class, java.lang.Boolean.class
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
         if ("Id_Usuario".equals(valor) ){
             verf=true;
             int id = Integer.parseInt(jTextField7.getText());
             sql= "select Id_Cliente, Nombre, Apellidos, Telefono, Enfermedad, Disciplina, Med_Biceps, Med_Pecho, Med_Pierna, Med_Pantorrila, Med_Abdomen from cliente WHERE Id_Cliente LIKE '"+id+"%'";
         
         
         }
         else{
         verf=false;
         }
        
        }else{
        verf=false;
        if ("Apellido".equals(valor) ){
            verf=true;
               String nombre = jTextField7.getText();
             sql= "select Id_Cliente, Nombre, Apellidos, Telefono, Enfermedad, Disciplina, Med_Biceps, Med_Pecho, Med_Pierna, Med_Pantorrila, Med_Abdomen from cliente WHERE Apellidos LIKE '"+nombre+"%'";
         }
        
        
        if ("Nombre".equals(valor) ){
            verf=true;
              String nombre = jTextField7.getText();
             sql= "select Id_Cliente, Nombre, Apellidos, Telefono, Enfermedad, Disciplina, Med_Biceps, Med_Pecho, Med_Pierna, Med_Pantorrila, Med_Abdomen from cliente WHERE Nombre LIKE '"+nombre+"%'";
         } 
        
        
        }
          
         if(verf){
         
         Object [] datos = new Object[12];
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
             datos[0]=rs.getString(1);
             datos[1]=rs.getString(2);
             datos[2]=rs.getString(3);
             datos[3]=rs.getString(4);
             datos[4]=rs.getString(5);
             datos[5]=rs.getString(6);
 datos[6]=rs.getFloat("Med_Biceps");
datos[7]=rs.getFloat("Med_Pecho");
datos[8]=rs.getFloat(9);
datos[9]=rs.getFloat(10);
datos[10]=rs.getFloat(11);
datos[11]=false;
           
             
             
             
             modelo.addRow(datos);
         }
         
         jTable1.setModel(modelo);
         
         
         
       
         jButton4.setEnabled(false);
         jTextField7.setText(null);
         desactivar();
         
         }
         
         // TODO add your handling code here:
     } catch (SQLException ex) {
         Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
     }
     actualizar();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
int dialog=JOptionPane.YES_NO_OPTION;
        int result=JOptionPane.showConfirmDialog(null, "¿Desea eliminar al Cliente?","Borrar",dialog);
        if (result==0) {
       eliminar();
        }
        actualizar();
      
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
desactivar();          // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseEntered
desactivar1();         // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseEntered
////
    private void jTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5KeyPressed

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped

desactivar();  
char c = evt.getKeyChar();
if(c<'0'||c>'9')evt.consume();        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5KeyTyped

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

this.ID = Integer.valueOf(jLabel2.getText());
        System.out.print(ID);        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jTextField16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16KeyPressed

    private void jTextField16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyTyped
desactivar();  
char c = evt.getKeyChar();
if((c<'0'||c>'9')&&c>'.')evt.consume();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16KeyTyped

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered

boolean check = false;
        for (int i = 0; i < jTable1.getRowCount(); i++) {

 
 if((Boolean) jTable1.getValueAt(i, 11))
{
   
 check = true;
           
}

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseEntered
if(check){
jButton3.setEnabled(true);
}else{

jButton3.setEnabled(false);
}
    
    } 
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
mostrar();  
actualizar();
// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        if (!jTextField3.getText().matches("^[a-zA-Z ]{1,45}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres de la a-z");
            jTextField3.setText("");
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        if (!jTextField4.getText().matches("^[a-zA-Z ]{1,45}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres de la a-z");
            jTextField4.setText("");
        }
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        if (!jTextField5.getText().matches("^[0-9]{1,15}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField5.setText("");
        }
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        if (!jTextField6.getText().matches("^[a-zA-Z ]{1,45}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres de la a-z");
            jTextField6.setText("");
        }
    }//GEN-LAST:event_jTextField6KeyReleased

    private void jTextField8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyReleased
        if (!jTextField8.getText().matches("^[a-zA-Z ]{1,45}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres de la a-z");
            jTextField8.setText("");
        }
    }//GEN-LAST:event_jTextField8KeyReleased

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        if (!jTextField14.getText().matches("^[0-9. ]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField14.setText("");
        }
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        if (!jTextField15.getText().matches("^[0-9. ]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField15.setText("");
        }
    }//GEN-LAST:event_jTextField15KeyReleased

    private void jTextField16KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyReleased
        if (!jTextField16.getText().matches("^[0-9. ]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField16.setText("");
        }
    }//GEN-LAST:event_jTextField16KeyReleased

    private void jTextField17KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyReleased
        if (!jTextField17.getText().matches("^[0-9. ]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField17.setText("");
        }
    }//GEN-LAST:event_jTextField17KeyReleased

    private void jTextField18KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField18KeyReleased
        if (!jTextField18.getText().matches("^[0-9. ]{1,6}?$")) {
            JOptionPane.showMessageDialog(null,"Solo se aceptan caracteres numericos");
            jTextField18.setText("");
        }
    }//GEN-LAST:event_jTextField18KeyReleased

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased
        if (jTextField7.getText().matches("^[a-zA-Z ]{1,45}?$") || jTextField7.getText().matches("^[0-9]{1,4}?$")) {
        } else {
            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres A-Z o numero");
            jTextField7.setText("");
        }
    }//GEN-LAST:event_jTextField7KeyReleased

    private void jRadioButton1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton1StateChanged
        if(jRadioButton1.isSelected()){
            jTextField6.setEnabled(true);
        }else{
            jTextField6.setEnabled(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1StateChanged

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed
   
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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Clientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel label_imagen;
    // End of variables declaration//GEN-END:variables
}
