/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import static Ventanas.subir_imagen.decodeToImage;
import clases.LeerHuella;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
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
public class Menu_11 extends javax.swing.JFrame {

    /**
     * Creates new form Menu_1
     */ conectar cc = new conectar();
    Connection conexion = cc.conexion();
     ImageIcon icon = null;
    
   
    int ID;
    
    public Menu_11() {
        
        
        
        initComponents();
        this.setLocationRelativeTo(null);
        
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/cerrar.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);
this.repaint();
fot = new ImageIcon(getClass().getResource("/imagenes1/archivo.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_DEFAULT));
jLabel7.setIcon(icono);

this.repaint();
fot = new ImageIcon(getClass().getResource("/imagenes1/avatar.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel4.getWidth(), jLabel4.getHeight(), Image.SCALE_DEFAULT));
jLabel4.setIcon(icono);

this.repaint();
fot = new ImageIcon(getClass().getResource("/imagenes1/multiple-users-silhouette.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel5.getWidth(), jLabel5.getHeight(), Image.SCALE_DEFAULT));
jLabel5.setIcon(icono);

this.repaint();
fot = new ImageIcon(getClass().getResource("/imagenes1/vision.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel6.getWidth(), jLabel6.getHeight(), Image.SCALE_DEFAULT));
jLabel6.setIcon(icono);

this.repaint();

fot = new ImageIcon(getClass().getResource("/imagenes1/trabajador-de-la-construccion.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel8.getWidth(), jLabel8.getHeight(), Image.SCALE_DEFAULT));
jLabel8.setIcon(icono);

this.repaint();
fot = new ImageIcon(getClass().getResource("/imagenes1/carro-de-reparto-silueta.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel5.getWidth(), jLabel5.getHeight(), Image.SCALE_DEFAULT));
jLabel5.setIcon(icono);

this.repaint();
fot = new ImageIcon(getClass().getResource("/imagenes1/botella-de-plastico.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel9.getWidth(), jLabel9.getHeight(), Image.SCALE_DEFAULT));
jLabel9.setIcon(icono);

this.repaint();
fot = new ImageIcon(getClass().getResource("/imagenes1/gimnasio.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel10.getWidth(), jLabel10.getHeight(), Image.SCALE_DEFAULT));
jLabel10.setIcon(icono);

this.repaint();
fot = new ImageIcon(getClass().getResource("/imagenes1/tarjeta-de-credito-y-billete.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel11.getWidth(), jLabel11.getHeight(), Image.SCALE_DEFAULT));
jLabel11.setIcon(icono);

this.repaint();
fot = new ImageIcon(getClass().getResource("/imagenes1/analitica.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel12.getWidth(), jLabel12.getHeight(), Image.SCALE_DEFAULT));
jLabel12.setIcon(icono);

this.repaint();
        cargar();
        fecha();
    }
    
    public void fecha() {
        Double result=0.0;
        String diferencia="";
        File miDir = new File (".");
      Paragraph parrafo = null;
      String sql ="";
    String fecha_Actual="";
     String fecha="";
     int numero=0;
    
      String sq = "SELECT fecha FROM fecha ";
    try {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sq);
            while (rs.next()) {
               fecha_Actual=rs.getString(1);
               
            }
            
               
                

        } catch (SQLException ex) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
        }
     String sql1 = "SELECT CURDATE()";
    try {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            while (rs.next()) {
               fecha=rs.getString(1);
               
            }
            
               
                

        } catch (SQLException ex) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    if(!fecha.equals(fecha_Actual)){
        
        
     String sql2 = "SELECT DAY(CURDATE())";
    try {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            while (rs.next()) {
               numero=rs.getInt(1);
               
            }
            
               
                

        } catch (SQLException ex) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    if(numero==30){
          try {
    sql = "SELECT DATE_SUB(CURDATE(), INTERVAL 31 DAY)";
             
             
             
             
             
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
             
      parrafo = new Paragraph("Ramirez Boxing club");       // Este codigo genera una tabla de 3 columnas
          
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
      String sql11= "select empleado.Nombre, empleado.Apellidos, Producto_Id_Producto, producto.Nombre_Producto, sum(Cantidad) as cantidad,producto.Precio_Producto from ventas INNER JOIN producto ON Producto_Id_Producto = producto.Id_Producto INNER JOIN empleado ON Empleado_Id_Empleado = empleado.Id_Usuario where  ventas.Fecha_Venta >'"+diferencia+"' group by empleado.Nombre, empleado.Apellidos, Producto_Id_Producto, producto.Nombre_Producto ";
         try {
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql11);
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
          
    }   catch (SQLException ex) {
        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        
        
    }
          document.add(parrafo);
          document.add( Chunk.NEWLINE );
          PdfPCell celdaFinal = new PdfPCell(new Paragraph("Total: "+result+""));
             
            // Indicamos cuantas columnas ocupa la celda
            celdaFinal.setColspan(9);
            table.addCell(celdaFinal);
         document.add(table);
         
            
        
            
               
            document.close(); 
        
        }catch(DocumentException e)
        {
           JOptionPane.showMessageDialog(null, "Ocurrio un error al crear el archivo","Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        } catch (IOException ex) {
             Logger.getLogger(Menu_11.class.getName()).log(Level.SEVERE, null, ex);
         }
        try {
            Desktop.getDesktop().open(new File(miDir.getCanonicalPath()+"\\ventas_"+diferencia+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
                     String s= " update fecha set fecha = CURDATE() ";
                     
                     PreparedStatement pst = conexion.prepareStatement(s);
                   
                   pst.executeUpdate();
                 } catch (SQLException ex) {
                     Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        
        
    }
    
    
    }
    
    
    
    }
    
    
    
    
    
    
     void cargar1() {
        BufferedImage img = null;
        String sql = "SELECT imagen FROM administradores where id ="+ID+"";
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        label_imagen = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

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

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/avatar.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 130));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/cliente.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 130, 110));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/carrito-de-tienda.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 130, 110));

        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 130, 110));

        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 140, 40, 30));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/carrito-de-tienda.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 130, 110));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/cliente.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 210, 130, 110));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/carrito-de-tienda.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, 130, 110));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/carrito-de-tienda.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 210, 130, 110));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/cliente.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, 130, 110));

        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 220, 160));

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
        jLabel15.setText("RFC: RUPG9101103U0");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel16.setForeground(new java.awt.Color(250, 234, 128));
        jLabel16.setText("Menu");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

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
 int dialog=JOptionPane.YES_NO_OPTION;
        int result=JOptionPane.showConfirmDialog(null, "¿Desea Salir?","Exit",dialog);
        if (result==0) {
            System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_label_imagenMouseClicked
    }
    private void label_imagenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_imagenMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_imagenMouseEntered

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
   
        this.ID = Integer.valueOf(jLabel2.getText());
        System.out.print(ID);
cargar1();// TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowIconified

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked

 
      String sql = "SELECT archivo, nomre FROM pdf where id = 1";
        
 byte[] b = null;
 String nombre = "";
        try {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                 b = rs.getBytes(1);
                 nombre= rs.getString(2);
            }
         
            InputStream bos = new ByteArrayInputStream(b);

            int tamanoInput = bos.available();
            byte[] datosPDF = new byte[tamanoInput];
            bos.read(datosPDF, 0, tamanoInput);

            OutputStream out = new FileOutputStream(nombre+".pdf");
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
             Desktop.getDesktop().open(new File(nombre+".pdf"));
             // TODO add your handling code here:
         } catch (IOException ex) {
             Logger.getLogger(Menu_11.class.getName()).log(Level.SEVERE, null, ex);
         }
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
 Administradores obj=new  Administradores() ;
 
                      obj.jLabel2.setText(Integer.toString(ID));
 
        obj.setVisible(true); 
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        Provedor obj=new  Provedor() ;
                      obj.jLabel2.setText(Integer.toString(ID));
 
        obj.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
subir_imagen_empleados obj=new  subir_imagen_empleados() ;
                      obj.jLabel3.setText(Integer.toString(ID));
 
        obj.setVisible(true); 
        this.setVisible(false);
                // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

subir_pdf obj=new  subir_pdf();
        obj.setVisible(true);    
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked

Empleados obj=new  Empleados() ;
 
                      obj.jLabel2.setText(Integer.toString(ID));
 
        obj.setVisible(true); 
        this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
Productos obj=new  Productos() ;
                      obj.jLabel2.setText(Integer.toString(ID));
 
        obj.setVisible(true);
        this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked

        ejercicio obj=new  ejercicio() ;
                      obj.jLabel2.setText(Integer.toString(ID));
 
        obj.setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
  menu1_Pagos obj=new  menu1_Pagos() ;
                      obj.jLabel3.setText(Integer.toString(ID));
 
        obj.setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked

        Reportes obj=new  Reportes() ;
                      obj.jLabel2.setText(Integer.toString(ID));
 
        obj.setVisible(true);
        this.setVisible(false);        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        subir_imagen obj=new  subir_imagen();
        obj.jLabel3.setText(Integer.toString(ID));
        obj.setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseClicked
    
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
            java.util.logging.Logger.getLogger(Menu_11.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_11.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_11.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_11.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_11().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_imagen;
    // End of variables declaration//GEN-END:variables
}
