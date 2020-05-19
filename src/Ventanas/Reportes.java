/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import static Ventanas.subir_imagen.decodeToImage;
import static Ventanas.subir_imagen.encodeToString;
import clases.LeerHuella;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
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
import javax.swing.text.Document;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Lenovo
 */
public class Reportes extends javax.swing.JFrame {
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
    int contador=0;
            Statement pst= null;
    public Reportes() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/back.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);
cargar();
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
    
    
    
    public void historialcliente_documento(){
          Double result=0.0;
      String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
        File miDir = new File (".");
      Paragraph parrafo = null;
      String sql ="";
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
          com.itextpdf.text.Document document = new com.itextpdf.text.Document();
         
        try{
            PdfWriter.getInstance(document, new FileOutputStream( miDir.getCanonicalPath()+"\\Historial_Cliente_"+diferencia+".pdf"));
            document.open();
             
      parrafo = new Paragraph("Historial Cliente fecha: "+diferencia);       // Este codigo genera una tabla de 3 columnas
          
    // Este codigo genera una tabla de 3 columnas
    PdfPTable table = new PdfPTable(5);
    table.addCell("Nombre");
    table.addCell("Apellidos");
    table.addCell("Fecha");
    table.addCell("Hora");
     table.addCell("Accion");
   
   
    // addCell() agrega una celda a la tabla, el cambio de fila
    // ocurre automaticamente al llenar la fila
     String sql1= "select cliente.Nombre, cliente.Apellidos, Fecha, Hora, Accion from historialcliente INNER JOIN cliente ON Cliente_Id_Cliente = cliente.Id_Cliente  where  Fecha >'"+diferencia+"'";
         try {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            while(rs.next()){
               
                table.addCell(rs.getString(1));
                
                table.addCell(rs.getString(2));
                
                table.addCell(rs.getString(3));
                table.addCell(rs.getString(4));
                 table.addCell(rs.getString(5));
                
                
                                
            }
            
            String sql3= "select COUNT(*) from historialcliente INNER JOIN cliente ON Cliente_Id_Cliente = cliente.Id_Cliente  where  Fecha >'"+diferencia+"' AND Accion= 'Entrada'";
            
            
            
            
            s = conexion.createStatement();
          rs = s.executeQuery(sql3);
            
            while(rs.next()){
       contador=rs.getInt(1);   
       
            }
          
    }   catch (SQLException ex) {
        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
        
    }
          document.add(parrafo);
          document.add( Chunk.NEWLINE );
          PdfPCell celdaFinal = new PdfPCell(new Paragraph("Total: "+result+""));
             
            // Indicamos cuantas columnas ocupa la celda
            celdaFinal.setColspan(5);
            table.addCell(celdaFinal);
             PdfPCell celdaFinal1 = new PdfPCell(new Paragraph("Total de clientes: "+contador+""));
             
            // Indicamos cuantas columnas ocupa la celda
            celdaFinal1.setColspan(5);
            table.addCell(celdaFinal1);
         document.add(table);
         
            
        
            
               
            document.close(); 
        
        }catch(DocumentException | FileNotFoundException e)
        {
            

             JOptionPane.showMessageDialog(null, "Ocurrio un error al crear el archivo","Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        } catch (IOException ex) {   
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Desktop.getDesktop().open(new File(miDir.getCanonicalPath()+"\\Historial_Cliente_"+diferencia+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
     
  
   public void historialcliente(){
    String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
      String sql ="";
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
       
       
    DefaultTableModel modelo= new DefaultTableModel();
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellidos");
    modelo.addColumn("Fecha");
    modelo.addColumn("Hora");
      modelo.addColumn("Accion");
    
    
    jTable1.setModel(modelo);
    
   
    String sql1= "select cliente.Nombre, cliente.Apellidos, Fecha, Hora, Accion from historialcliente INNER JOIN cliente ON Cliente_Id_Cliente = cliente.Id_Cliente  where  Fecha >'"+diferencia+"'";
        
       Object [] datos = new Object[5];
         
        
        try{ 
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql1);
         
       
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                  datos[4]=rs.getString(5);
               
                

                
                modelo.addRow(datos);
            }
            
            jTable1.setModel(modelo);
         String sql3= "select COUNT(*) from historialcliente INNER JOIN cliente ON Cliente_Id_Cliente = cliente.Id_Cliente  where  Fecha >'"+diferencia+"' AND Accion= 'Entrada'";
            
            
            
            
            s = conexion.createStatement();
          rs = s.executeQuery(sql3);
            
            while(rs.next()){
       contador=rs.getInt(1);   
       
            }  
             jLabel10.setText(String.valueOf("Total de clientes: "+contador));
         
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
            
        }
        
        
        double result=0.0;
        
    
        
    
   }
    
    
    public void historialempleado_documento(){
          Double result=0.0;
      String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
        File miDir = new File (".");
      Paragraph parrafo = null;
      String sql ="";
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
          com.itextpdf.text.Document document = new com.itextpdf.text.Document();
         
        try{
            PdfWriter.getInstance(document, new FileOutputStream( miDir.getCanonicalPath()+"\\Historial_Empleado_"+diferencia+".pdf"));
            document.open();
             
      parrafo = new Paragraph("Historial Empleado fecha: "+diferencia);       // Este codigo genera una tabla de 3 columnas
          
    // Este codigo genera una tabla de 3 columnas
    PdfPTable table = new PdfPTable(5);
    table.addCell("Nombre");
    table.addCell("Apellidos");
    table.addCell("Fecha");
    table.addCell("Hora");
       table.addCell("Accion");
   
   
    // addCell() agrega una celda a la tabla, el cambio de fila
    // ocurre automaticamente al llenar la fila
     String sql1= "select empleado.Nombre, empleado.Apellidos, Fecha, Hora, Accion from historialempleado INNER JOIN empleado ON Empleado_Id_Usuario = empleado.Id_Usuario  where  Fecha >'"+diferencia+"'";
         try {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            while(rs.next()){
               
                table.addCell(rs.getString(1));
                
                table.addCell(rs.getString(2));
                
                table.addCell(rs.getString(3));
                table.addCell(rs.getString(4));
                table.addCell(rs.getString(5));
                
                
                                
            }
            
            
          
    }   catch (SQLException ex) {
        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
        
    }
          document.add(parrafo);
          document.add( Chunk.NEWLINE );
          PdfPCell celdaFinal = new PdfPCell(new Paragraph("Total: "+result+""));
             
            // Indicamos cuantas columnas ocupa la celda
            celdaFinal.setColspan(5);
            table.addCell(celdaFinal);
         document.add(table);
         
            
        
            
               
            document.close(); 
        
        }catch(DocumentException | FileNotFoundException e)
        {
            

             JOptionPane.showMessageDialog(null, "Ocurrio un error al crear el archivo","Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        } catch (IOException ex) {   
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Desktop.getDesktop().open(new File(miDir.getCanonicalPath()+"\\Historial_Empleado_"+diferencia+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
     
  
   public void historialempleado(){
    String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
      String sql ="";
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
       
       
    DefaultTableModel modelo= new DefaultTableModel();
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellidos");
    modelo.addColumn("Fecha");
    modelo.addColumn("Hora");
        modelo.addColumn("Accion");
     
    
    
    jTable1.setModel(modelo);
    
   
     String sql1= "select empleado.Nombre, empleado.Apellidos, Fecha, Hora, Accion from historialempleado INNER JOIN empleado ON Empleado_Id_Usuario = empleado.Id_Usuario  where  Fecha >'"+diferencia+"'";
        
       Object [] datos = new Object[5];
         
        
        try{ 
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql1);
         
       
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                 datos[4]=rs.getString(5);
               
                

                
                modelo.addRow(datos);
            }
            
            jTable1.setModel(modelo);
           
         
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
            
        }
        
        
        double result=0.0;
        
    
        
    
   }
    
    
    
    
    
    
    
    
     public void Pagos_Proveedor_documento(){
          Double result=0.0;
      String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
        File miDir = new File (".");
      Paragraph parrafo = null;
      String sql ="";
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
          com.itextpdf.text.Document document = new com.itextpdf.text.Document();
         
        try{
            PdfWriter.getInstance(document, new FileOutputStream( miDir.getCanonicalPath()+"\\Pagos_Proveedor_"+diferencia+".pdf"));
            document.open();
             
      parrafo = new Paragraph("Pagos Proveedor fecha: "+diferencia);       // Este codigo genera una tabla de 3 columnas
          
    // Este codigo genera una tabla de 3 columnas
    PdfPTable table = new PdfPTable(6);
    table.addCell("Nombre_Compañia");
    table.addCell("Nombre_Proveedor");
    table.addCell("Cantidad_Dinero");
    table.addCell("Fecha_Pago");
    table.addCell("Cantidad_producto");
     table.addCell("Nombre_Producto");
    
   
    // addCell() agrega una celda a la tabla, el cambio de fila
    // ocurre automaticamente al llenar la fila
    String sql1= "select proveedor.Nombre_Compañia, proveedor.Nombre_Proveedor, Cantidad_Dinero, Fecha_Pago, pagos_provedor.Cantidad_producto, producto.Nombre_Producto  from pagos_provedor INNER JOIN proveedor ON Proveedor_Id_Provedor = proveedor.Id_Provedor INNER JOIN producto ON producto.Id_Producto = pagos_provedor.Id_producto where Fecha_Pago >'"+diferencia+"'";
         try {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            while(rs.next()){
               
                table.addCell(rs.getString(1));
                
                table.addCell(rs.getString(2));
                
                table.addCell(rs.getString(3));
                table.addCell(rs.getString(4));
                 table.addCell(rs.getString(5));
                table.addCell(rs.getString(6));
                
                                
            }
            
            String sql3= "select sum( Cantidad_Dinero) from pagos_provedor INNER JOIN proveedor ON Proveedor_Id_Provedor = proveedor.Id_Provedor  where  Fecha_Pago >'"+diferencia+"'";
            
            
            
            
            s = conexion.createStatement();
          rs = s.executeQuery(sql3);
            
            while(rs.next()){
       result=rs.getDouble(1);   
       
            }
          
    }   catch (SQLException ex) {
        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
        
    }
          document.add(parrafo);
          document.add( Chunk.NEWLINE );
          PdfPCell celdaFinal = new PdfPCell(new Paragraph("Total: "+result+""));
             
            // Indicamos cuantas columnas ocupa la celda
            celdaFinal.setColspan(6);
            table.addCell(celdaFinal);
         document.add(table);
         
            
        
            
               
            document.close(); 
        
        }catch(DocumentException | FileNotFoundException e)
        {
           JOptionPane.showMessageDialog(null, "Ocurrio un error al crear el archivo","Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        } catch (IOException ex) {   
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Desktop.getDesktop().open(new File(miDir.getCanonicalPath()+"\\Pagos_Proveedor_"+diferencia+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
     
  
   public void Pagos_Proveedor(){
    String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
      String sql ="";
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
       
       
    DefaultTableModel modelo= new DefaultTableModel();
    modelo.addColumn("Nombre_Compañia");
    modelo.addColumn("Nombre_Proveedor");
    modelo.addColumn("Cantidad_Dinero");
    modelo.addColumn("Fecha_Pago");
    modelo.addColumn("Cantidad_producto");
    modelo.addColumn("Nombre_Producto");
     
     
    
    jTable1.setModel(modelo);
    
   
       String sql1= "select proveedor.Nombre_Compañia, proveedor.Nombre_Proveedor, Cantidad_Dinero, Fecha_Pago, pagos_provedor.Cantidad_producto, producto.Nombre_Producto  from pagos_provedor INNER JOIN proveedor ON Proveedor_Id_Provedor = proveedor.Id_Provedor INNER JOIN producto ON producto.Id_Producto = pagos_provedor.Id_producto where Fecha_Pago >'"+diferencia+"'";
         
       Object [] datos = new Object[6];
         
        
        try{ 
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql1);
         
       
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
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
            
        }
        
        
        double result=0.0;
        try {
           String sql3= "select sum( Cantidad_Dinero) from pagos_provedor INNER JOIN proveedor ON Proveedor_Id_Provedor = proveedor.Id_Provedor  where  Fecha_Pago >'"+diferencia+"'";
                
            
            
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql3);
            
            while(rs.next()){
                 result=rs.getDouble(1);
            }
            jLabel10.setText(String.valueOf("Total: "+result));
        } catch (SQLException ex) {
            Logger.getLogger(Facturas_Masivas.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    
   }
    
    
    
    
    
    
    
    
     
      public void Pagos_Empleado_documento(){
          Double result=0.0;
      String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
        File miDir = new File (".");
      Paragraph parrafo = null;
      String sql ="";
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
          com.itextpdf.text.Document document = new com.itextpdf.text.Document();
         
        try{
            PdfWriter.getInstance(document, new FileOutputStream( miDir.getCanonicalPath()+"\\Pagos_Empleado_"+diferencia+".pdf"));
            document.open();
             
      parrafo = new Paragraph("Pagos Empleado fecha: "+diferencia);       // Este codigo genera una tabla de 3 columnas
          
    // Este codigo genera una tabla de 3 columnas
    PdfPTable table = new PdfPTable(4);
    table.addCell("Nombre");
    table.addCell("Apellidos");
    table.addCell("Cantidad_Dinero");
    table.addCell("Fecha_Pago");
   
   
    // addCell() agrega una celda a la tabla, el cambio de fila
    // ocurre automaticamente al llenar la fila
     String sql1= "select empleado.Nombre, empleado.Apellidos, Cantidad_Dinero, Fecha_Pago from pagos_empleado INNER JOIN empleado ON Usuario_Id_Usuario = empleado.Id_Usuario  where  Fecha_Pago >'"+diferencia+"'";
         try {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            while(rs.next()){
               
                table.addCell(rs.getString(1));
                
                table.addCell(rs.getString(2));
                
                table.addCell(rs.getString(3));
                table.addCell(rs.getString(4));
                
                
                                
            }
            
            String sql3= "select sum( Cantidad_Dinero) from pagos_empleado INNER JOIN empleado ON Usuario_Id_Usuario = empleado.Id_Usuario  where  Fecha_Pago >'"+diferencia+"'";
            
            
            
            
            s = conexion.createStatement();
          rs = s.executeQuery(sql3);
            
            while(rs.next()){
       result=rs.getDouble(1);   
       
            }
          
    }   catch (SQLException ex) {
        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
        
    }
          document.add(parrafo);
          document.add( Chunk.NEWLINE );
          PdfPCell celdaFinal = new PdfPCell(new Paragraph("Total: "+result+""));
             
            // Indicamos cuantas columnas ocupa la celda
            celdaFinal.setColspan(4);
            table.addCell(celdaFinal);
         document.add(table);
         
            
        
            
               
            document.close(); 
        
        }catch(DocumentException | FileNotFoundException e)
        {
           JOptionPane.showMessageDialog(null, "Ocurrio un error al crear el archivo","Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        } catch (IOException ex) {   
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Desktop.getDesktop().open(new File(miDir.getCanonicalPath()+"\\Pagos_Empleado_"+diferencia+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
     
  
   public void Pagos_Empleado(){
    String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
      String sql ="";
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
       
       
    DefaultTableModel modelo= new DefaultTableModel();
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellidos");
    modelo.addColumn("Cantidad_Dinero");
    modelo.addColumn("Fecha_Pago");
     
    
    
    jTable1.setModel(modelo);
    
   
     String sql1= "select empleado.Nombre, empleado.Apellidos, Cantidad_Dinero, Fecha_Pago from pagos_empleado INNER JOIN empleado ON Usuario_Id_Usuario = empleado.Id_Usuario  where  Fecha_Pago >'"+diferencia+"'";
         
       Object [] datos = new Object[4];
         
        
        try{ 
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql1);
         
       
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
               
                

                
                modelo.addRow(datos);
            }
            
            jTable1.setModel(modelo);
           
         
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
            
        }
        
        
        double result=0.0;
        try {
           String sql3= "select sum( Cantidad_Dinero) from pagos_empleado INNER JOIN empleado ON Usuario_Id_Usuario = empleado.Id_Usuario  where  Fecha_Pago >'"+diferencia+"'";
               
            
            
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql3);
            
            while(rs.next()){
                 result=rs.getDouble(1);
            }
            jLabel10.setText(String.valueOf("Total: "+result));
        } catch (SQLException ex) {
            Logger.getLogger(Facturas_Masivas.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    
   }
   
   
   
                   
    
       
   
   
   
   
   
   
   
   
   
   
   public void ventas_documento(){
          Double result=0.0;
           int result1=0;
      String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
        File miDir = new File (".");
      Paragraph parrafo = null;
      String sql ="";
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
          com.itextpdf.text.Document document = new com.itextpdf.text.Document();
         
        try{
            PdfWriter.getInstance(document, new FileOutputStream( miDir.getCanonicalPath()+"\\ventas_"+diferencia+".pdf"));
            document.open();
             
      parrafo = new Paragraph("Ventas fecha: "+diferencia);       // Este codigo genera una tabla de 3 columnas
          
    // Este codigo genera una tabla de 3 columnas
    PdfPTable table = new PdfPTable(6);
    table.addCell("Nombre");
    table.addCell("Apellidos");
    table.addCell("Id_Producto");
    table.addCell("Nombre_Producto");
    table.addCell("Cantidad");
    table.addCell("Precio_Producto");
   
    // addCell() agrega una celda a la tabla, el cambio de fila
    // ocurre automaticamente al llenar la fila
      String sql1= "select empleado.Nombre, empleado.Apellidos, Producto_Id_Producto, producto.Nombre_Producto, sum(Cantidad) as cantidad,producto.Precio_Producto from ventas INNER JOIN producto ON Producto_Id_Producto = producto.Id_Producto INNER JOIN empleado ON Empleado_Id_Empleado = empleado.Id_Usuario where  ventas.Fecha_Venta >'"+diferencia+"' group by empleado.Nombre, empleado.Apellidos, Producto_Id_Producto, producto.Nombre_Producto ";
         try {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            while(rs.next()){
               
                table.addCell(rs.getString(1));
                
                table.addCell(rs.getString(2));
                
                table.addCell(rs.getString(3));
                table.addCell(rs.getString(4));
                table.addCell(rs.getString(5));
                table.addCell(rs.getString(6));
                
                                
            }
            
            String sql3= "select sum( Cantidad*producto.Precio_Producto) from ventas INNER JOIN producto ON Producto_Id_Producto = producto.Id_Producto where  ventas.Fecha_Venta >'"+diferencia+"'";
            
            
            
            
            s = conexion.createStatement();
          rs = s.executeQuery(sql3);
            
            while(rs.next()){
       result=rs.getDouble(1);   
       
            }
            String sql31= "select sum( Cantidad) from ventas INNER JOIN producto ON Producto_Id_Producto = producto.Id_Producto where producto.categoria= '"+(String) jComboBox3.getSelectedItem()+"' AND ventas.Fecha_Venta >'"+diferencia+"'";
            
            
            
            
            s = conexion.createStatement();
          rs = s.executeQuery(sql31);
            
            while(rs.next()){
       result1=rs.getInt(1);   
       
            }
          
    }   catch (SQLException ex) {
        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
        
    }
          document.add(parrafo);
          document.add( Chunk.NEWLINE );
          PdfPCell celdaFinal = new PdfPCell(new Paragraph("Total: "+result+""));
             
            // Indicamos cuantas columnas ocupa la celda
            celdaFinal.setColspan(9);
            table.addCell(celdaFinal);
           
             PdfPCell celdaFinal1 = new PdfPCell(new Paragraph("Total de "+(String) jComboBox3.getSelectedItem()+": "+result1+""));
             
            // Indicamos cuantas columnas ocupa la celda
            celdaFinal1.setColspan(9);
            table.addCell(celdaFinal1);
         document.add(table);
         
            
        
            
               
            document.close(); 
        
        }catch(DocumentException | FileNotFoundException e)
        {
           JOptionPane.showMessageDialog(null, "Ocurrio un error al crear el archivo","Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        } catch (IOException ex) {   
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Desktop.getDesktop().open(new File(miDir.getCanonicalPath()+"\\ventas_"+diferencia+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
     
  
   public void ventas(){
    String valor1 = (String) jComboBox1.getSelectedItem();
     String diferencia="";
      String sql ="";
      int result1 =0;
        try {
             if ("Dia".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
             }
             if ("Semana".equals(valor1) ){
             
           
             
             sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
             }
              if ("Mes".equals(valor1) ){
             
           
             
              sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             }
             
             
             
             if(!"".equals(sql)){
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
                diferencia  = rs.getString(1);
                 
             }}
             
             System.out.print(diferencia);
             
         } catch (SQLException ex) {
             Logger.getLogger(Membresia.class.getName()).log(Level.SEVERE, null, ex);
         }
       
       
    DefaultTableModel modelo= new DefaultTableModel();
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellidos");
    modelo.addColumn("Id_Producto");
    modelo.addColumn("Nombre_Producto");
     modelo.addColumn("Cantidad");
      modelo.addColumn("Precio");
    
    
    jTable1.setModel(modelo);
    
   
    String sql1= "select empleado.Nombre, empleado.Apellidos, Producto_Id_Producto, producto.Nombre_Producto, sum(Cantidad) as cantidad,producto.Precio_Producto from ventas INNER JOIN producto ON Producto_Id_Producto = producto.Id_Producto INNER JOIN empleado ON Empleado_Id_Empleado = empleado.Id_Usuario where  ventas.Fecha_Venta >'"+diferencia+"' group by empleado.Nombre, empleado.Apellidos, Producto_Id_Producto, producto.Nombre_Producto ";
         
       Object [] datos = new Object[6];
         
        
        try{ 
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql1);
         
       
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
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
            
        }
        
        
        double result=0.0;
        try {
            String sql3= "select sum( Cantidad*producto.Precio_Producto) from ventas INNER JOIN producto ON Producto_Id_Producto = producto.Id_Producto where  ventas.Fecha_Venta >'"+diferencia+"'";
            
            
            
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql3);
            
            while(rs.next()){
                 result=rs.getDouble(1);
            }
             String sql31= "select sum( Cantidad) from ventas INNER JOIN producto ON Producto_Id_Producto = producto.Id_Producto where producto.categoria= '"+(String) jComboBox3.getSelectedItem()+"' AND ventas.Fecha_Venta >'"+diferencia+"'";
            
            
            
            
            s = conexion.createStatement();
          rs = s.executeQuery(sql31);
            
            while(rs.next()){
       result1=rs.getInt(1);   
       
            }
            jLabel10.setText(String.valueOf("Total: "+result));
             jLabel11.setText(String.valueOf("Total de "+(String) jComboBox3.getSelectedItem()+" :"+result1));
        } catch (SQLException ex) {
            Logger.getLogger(Facturas_Masivas.class.getName()).log(Level.SEVERE, null, ex);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        label_imagen = new javax.swing.JLabel();

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

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 550, 320));

        jButton2.setText("Imprimir");
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

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Refresco", "Agua", "Proteina" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Tabla");

        jLabel4.setText("Categoria");

        jLabel5.setText("Dia");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(29, 29, 29)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(30, 30, 30)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(75, 75, 75))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jButton2))
                .addGap(25, 25, 25))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 111, 610, 50));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 320, 40));

        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 550));

        jLabel15.setForeground(new java.awt.Color(250, 234, 128));
        jLabel15.setText("Reportes");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
jComboBox2.removeAllItems();
          jComboBox2.addItem ("ventas");
          jComboBox2.addItem ("pagos_provedor");
          jComboBox2.addItem ("pagos_empleado");
          jComboBox2.addItem ("historialempleado");
           jComboBox2.addItem ("historialcliente");
           jComboBox1.removeAllItems();
          jComboBox1.addItem ("Dia");
           jComboBox1.addItem ("Semana");
            jComboBox1.addItem ("Mes");
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      String valor1 = (String) jComboBox2.getSelectedItem();
      if ("ventas".equals(valor1) ){
       ventas_documento();
           
      }
       if ("pagos_provedor".equals(valor1) ){
       Pagos_Proveedor_documento();
       }
        if ("pagos_empleado".equals(valor1) ){
       Pagos_Empleado_documento();
       }
        if ("historialempleado".equals(valor1) ){
       historialempleado_documento();
       }
         if ("historialcliente".
                 equals(valor1) ){
      historialcliente_documento();
       }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
             // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseEntered

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
      String valor1 = (String) jComboBox2.getSelectedItem();
      if ("ventas".equals(valor1) ){
       ventas();
           
      }
       if ("pagos_provedor".equals(valor1) ){
       Pagos_Proveedor();
       }
        if ("pagos_empleado".equals(valor1) ){
       Pagos_Empleado();
       }
        if ("historialempleado".equals(valor1) ){
       historialempleado();
       }
         if ("historialcliente".equals(valor1) ){
      historialcliente();
       }
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
String valor1 = (String) jComboBox2.getSelectedItem();
      if ("ventas".equals(valor1) ){
       ventas();
           
      }
       if ("pagos_provedor".equals(valor1) ){
       Pagos_Proveedor();
       }
        if ("pagos_empleado".equals(valor1) ){
       Pagos_Empleado();
       }
        if ("historialempleado".equals(valor1) ){
       historialempleado();
       }
         if ("historialcliente".equals(valor1) ){
      historialcliente();
       }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
 ventas();        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ItemStateChanged
    
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
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Reportes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel label_imagen;
    // End of variables declaration//GEN-END:variables
}
