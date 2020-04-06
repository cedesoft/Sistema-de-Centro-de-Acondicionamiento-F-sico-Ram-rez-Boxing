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
public class Rutinas extends javax.swing.JFrame {
    int cont =0;
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
     
    public Rutinas() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/cerrar.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);

id();
jComboBox1.removeAllItems();
          jComboBox1.addItem ("Lunes");
          jComboBox1.addItem ("Martes");
          jComboBox1.addItem ("Miercoles");
          jComboBox1.addItem ("Jueves");
          jComboBox1.addItem ("Viernes");
          jComboBox1.addItem ("Sabado");
          jComboBox2.removeAllItems();
          jComboBox2.addItem ("Biceps");
         jComboBox2.addItem ("Pecho");
         jComboBox2.addItem ("Pierna");
         jComboBox2.addItem ("Pantorrila");
         jComboBox2.addItem ("Abdomen");
         
         
         mostrar();
         
        
    }
    public void cargarb(){
    
         try {
             String imagen_string = "";
    
             String sql = "SELECT imagen FROM ejercicio where Nombre_Ejercicio ='"+(String) jComboBox3.getSelectedItem()+"'";
              System.out.print(sql);
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                 imagen_string = rs.getString(1);
                 
             }
             
             BufferedImage img =  decodeToImage(imagen_string);
            ImageIcon icon  = new ImageIcon(img);
             Icon icono  = new ImageIcon(icon.getImage().getScaledInstance(lbver1.getWidth(), lbver1.getHeight(), Image.SCALE_DEFAULT));
             lbver1.setText(null);
             lbver1.setIcon(icono);
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
            

    
    }
      void cargar(int limite) {
        
          String imagen_string = "";
     
         
     

        try {
   String sql = "select imagen, Nombre_Ejercicio,Dia from ejercicio INNER JOIN rutinas ON ejercicio.Clave_Ejercicio = rutinas.Clave_Ejercicio where Cliente_Id_Cliente ="+ID_Cliente+" limit " + limite +", 1";
        
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                imagen_string = rs.getString(1);
                lb_nombre.setText(rs.getString(2));
                jTextField1.setText(rs.getString(3));
            }
          
            if (imagen_string == null) {
                cont = cont - 1;
                contar();
            } else {
                
                BufferedImage img = decodeToImage(imagen_string);
                ImageIcon icon = new ImageIcon(img);
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lbver.getWidth(), lbver.getHeight(), Image.SCALE_DEFAULT));
                lbver.setText(null);
                lbver.setIcon(icono);
            }
System.out.print(" "+sql);
        } catch (SQLException ex) {
            Logger.getLogger(Rutinas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
      void contar() {
          
        String sql2 = "SELECT count(*) as cont from ejercicio INNER JOIN rutinas ON ejercicio.Clave_Ejercicio = rutinas.Clave_Ejercicio  where Cliente_Id_Cliente ="+ID_Cliente+"";
        try {
            int con = 0;
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            while (rs.next()) {
                con = rs.getInt("cont");
            }
            if (con == 0) {
                lb_nombre.setText("No se encontraron imagenes");
            } else {
                cont = con - 1;
                cargar(cont);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Rutinas.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(" "+sql2);
    }
       
     public void limpiar(){
      
                 try {
                     String sql1= "delete from rutinas where Habilitado='v' and Cliente_Id_Cliente="+ID_Cliente+"";
                     
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
         });
         modelo=(DefaultTableModel) jTable1.getModel();
         String sql= "select rutinas.Clave_Ejercicio, ejercicio.Nombre_Ejercicio, ejercicio.Musculo, Dia from rutinas INNER JOIN ejercicio ON rutinas.Clave_Ejercicio=ejercicio.Clave_Ejercicio where  rutinas.Habilitado = 'v' and Cliente_Id_Cliente="+ID_Cliente+" order by Dia";
        Object [] datos = new Object[5];
         
        
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
              datos[0]=rs.getString(1);
             datos[1]=rs.getString(2);
             datos[2]=rs.getString(3);
          
             datos[3]=rs.getString(4);
              datos[4]=true;
           
             
             
             
             
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
                 jLabel5.setText(rs.getString(2));
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
          if((valor).equals((String)jTable1.getValueAt(i, 1))){
          
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
     
         String valor1 = (String) jComboBox1.getSelectedItem();
                  String valor = (String) jComboBox2.getSelectedItem();
  String nombre = (String) jComboBox3.getSelectedItem();
         
         
         
         
         
        
         String sql= "select Clave_Ejercicio, Nombre_Ejercicio from ejercicio where  Nombre_Ejercicio ='"+nombre+"'";
        
        Object [] datos = new Object[5];
         
        System.out.print(nombre);
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
              datos[0]=rs.getString(1);
             datos[1]=nombre;
              
             datos[2]=valor;
              datos[3]=valor1;
       
             
              datos[4]=true;
           
             
             
             
             
             modelo.addRow(datos);
         }
         
         
         jTable1.setModel(modelo);
     } catch (SQLException ex) {
         Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
     }                
        
        
    }
   public void  cambio(){
     String valor = (String) jComboBox2.getSelectedItem();
     String sql= "select Clave_Ejercicio, Nombre_Ejercicio from ejercicio where  Musculo ='"+valor+"'";
        try {
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            jComboBox3.removeAllItems();
            while(rs.next()){
                jComboBox3.addItem (rs.getString(2));
            }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbver = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        lb_nombre = new javax.swing.JTextField();
        lbver1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        label_imagen = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(900, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
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
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 120, -1, -1));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 40, 10));

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

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, -1, 100));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, -1, -1));
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

        jButton3.setText("Asignar");
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
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 120, -1, -1));
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 40, 10));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jPanel2.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, -1, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jComboBox3AncestorMoved(evt);
            }
        });
        jComboBox3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox3FocusLost(evt);
            }
        });
        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jComboBox3MouseExited(evt);
            }
        });
        jComboBox3.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jComboBox3ComponentHidden(evt);
            }
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                jComboBox3ComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jComboBox3ComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jComboBox3ComponentShown(evt);
            }
        });
        jComboBox3.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jComboBox3InputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jComboBox3CaretPositionChanged(evt);
            }
        });
        jComboBox3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox3PropertyChange(evt);
            }
        });
        jPanel2.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, -1, -1));

        jButton4.setText("Nuevo");
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
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, -1));

        jLabel1.setText("Disciplina :");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, -1, 30));
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, 70, 30));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/avatar.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 130));
        jPanel2.add(lbver, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, 160, 100));

        jButton5.setText(">>");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 480, -1, -1));

        jButton6.setText("<<");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 480, -1, -1));
        jPanel2.add(lb_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 170, -1));
        jPanel2.add(lbver1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 160, 100));
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, 70, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 840, 550));

        label_imagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_imagenMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_imagenMouseEntered(evt);
            }
        });
        jPanel1.add(label_imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, 30, 20));

        jLabel15.setForeground(new java.awt.Color(250, 234, 128));
        jLabel15.setText("Rutinas");
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

       
     agregar();
 
     
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        this.ID_Cliente = Integer.valueOf(jLabel7.getText());
        this.ID = Integer.valueOf(jLabel2.getText());
        System.out.print(ID);     
mostrar();  
cargar1();
contar();
// TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
String valor = (String)jTextField2.getText();
String valor1 = (String) jComboBox2.getSelectedItem();
        String sql= "select Nombre_Ejercicio from ejercicio  WHERE Nombre_Ejercicio LIKE '"+valor+"%' and Musculo='"+valor1+"'";
        try {
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            jComboBox3.removeAllItems();
            while(rs.next()){
                jComboBox3.addItem (rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
           // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
boolean check = false;
        for (int i = 0; i < jTable1.getRowCount(); i++) {

 
 if((Boolean) jTable1.getValueAt(i,4))
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
limpiar();
int n =0;     
        try {
        
            
            pst = conexion.createStatement();
            statement = conexion.createStatement();
           
            
            
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String sql = "INSERT INTO rutinas (Cliente_Id_Cliente, Dia, Clave_Ejercicio, Habilitado) VALUES (?, ?,?, 'v')";
       

            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setInt(1, ID_Cliente);
            pst.setString(2, (String)jTable1.getValueAt(i, 3));
             pst.setString(3, (String)jTable1.getValueAt(i, 0));
             if((Boolean) jTable1.getValueAt(i, 4)){
           n = pst.executeUpdate();
             }
            } 
     
        } catch (SQLException ex) {
         Logger.getLogger(Rutinas.class.getName()).log(Level.SEVERE, null, ex);
     }
   
     
           if (n > 0) {
           
           
            JOptionPane.showMessageDialog(null, "Registro guardado");
           
           }else{
           JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
   
           
           }
            mostrar();
            
            
                // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
       // TODO add your handling code here:


    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
      // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
visualizar();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged

cambio();

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseEntered
boolean check = false;
        for (int i = 0; i < jTable1.getRowCount(); i++) {

 
 if((Boolean) jTable1.getValueAt(i,4))
{
   
 check = true;
           
}

        // TODO add your handling code here:
    }                                     
if(check){
jButton4.setEnabled(true);
}else{

jButton4.setEnabled(false);
}
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseEntered

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
      
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
cont = cont + 1;
        cargar(cont);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
/* cont = cont + 1;
        cargar(cont);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        private void formWindowActivated(java.awt.event.WindowEvent evt) {   
        */
if (cont == 0) {
            cargar(cont);
        } else if (cont < 0) {
            cargar(cont = 0);
        } else {
            cont = cont - 1;
            cargar(cont);
        }
    }//GEN-LAST:event_formWindowActivated

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged




        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox3PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3PropertyChange

    private void jComboBox3AncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jComboBox3AncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3AncestorMoved

    private void jComboBox3ComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jComboBox3ComponentMoved
     // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ComponentMoved

    private void jComboBox3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox3FocusGained
           // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3FocusGained

    private void jComboBox3ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jComboBox3ComponentShown
         // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ComponentShown

    private void jComboBox3ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jComboBox3ComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ComponentResized

    private void jComboBox3ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jComboBox3ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ComponentHidden

    private void jComboBox3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox3FocusLost
cargarb();             // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3FocusLost

    private void jComboBox3CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jComboBox3CaretPositionChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3CaretPositionChanged

    private void jComboBox3InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jComboBox3InputMethodTextChanged
           // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3InputMethodTextChanged

    private void jComboBox3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseExited
cargarb();  
// TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3MouseExited
     private void formWindowActivated(java.awt.event.WindowEvent evt) {  }
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
            java.util.logging.Logger.getLogger(Rutinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Rutinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Rutinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Rutinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Rutinas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel label_imagen;
    private javax.swing.JTextField lb_nombre;
    private javax.swing.JLabel lbver;
    private javax.swing.JLabel lbver1;
    // End of variables declaration//GEN-END:variables
}
