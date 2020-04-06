/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import static Ventanas.Menu_11.decodeToImage;
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
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Lenovo
 */
public class Mensajes extends javax.swing.JFrame {
 File fichero = null;
 private byte[] huella = null; 
 private JPanel contentPane;
 int ID =0;
 int suma=0;
 boolean check1 = false;
 int diferencia=0;
    /** File fichero = null;
     * Creates new form Menu_1
     */ conectar cc = new conectar();
    Connection conexion = cc.conexion();
    Statement statement= null;
    Statement pst= null;
    DefaultTableModel modelo= new DefaultTableModel();
    int cantidad =0;
    public Mensajes() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/cerrar.png"));
Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
label_imagen.setIcon(icono);
fot = new ImageIcon(getClass().getResource("/imagenes1/correo-simbolo-de-sobre-negro.png"));
icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel12.getWidth(), jLabel12.getHeight(), Image.SCALE_DEFAULT));
jLabel12.setIcon(icono);

this.repaint();
id();

         
         
         
        
        
    }
    
    public void enviar(){
    String mensaje="";
    //Se fija el tiempo máximo de espera para conectar con el servidor (5000)
//Se fija el tiempo máximo de espera de la respuesta del servidor (60000)
RequestConfig config = RequestConfig.custom()
 .setConnectTimeout(5000)
 .setSocketTimeout(60000)
 .build();
	
//Se inicia el objeto HTTP
HttpClientBuilder builder = HttpClientBuilder.create();
builder.setDefaultRequestConfig(config);
CloseableHttpClient httpClient = builder.build();
	
//Se fija la URL sobre la que enviar la petición POST
HttpPost post = new HttpPost("http://www.altiria.net/api/http");
	
//Se crea la lista de parámetros a enviar en la petición POST
List parametersList = new ArrayList ();
//XX, YY y ZZ se corresponden con los valores de identificación del
//usuario en el sistema.
 String sql = "SELECT telefono, DATEDIFF(Membresia, CURDATE() ) FROM cliente";
        

        try {

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
               diferencia  = rs.getInt(2);
               if(diferencia>0){
                 
mensaje="Te quedan "+diferencia+" dias de tu membresia";
}else{

mensaje="Tu membresia ha terminado";
}
               parametersList.add(new BasicNameValuePair("cmd", "sendsms"));
parametersList.add(new BasicNameValuePair("domainId", "demopr"));
parametersList.add(new BasicNameValuePair("login", "rubenoctavio@itsn.edu.mx"));
parametersList.add(new BasicNameValuePair("passwd", "rjeyxby9"));

parametersList.add(new BasicNameValuePair("dest", rs.getString(1)));
parametersList.add(new BasicNameValuePair("msg", mensaje));
            }
            
                
              
            

        } catch (SQLException ex) {
            Logger.getLogger(subir_imagen.class.getName()).log(Level.SEVERE, null, ex);
        }

//No es posible utilizar el remitente en América pero sí en España y Europa
//Descomentar la línea solo si se cuenta con un remitente autorizado por Altiria
//parametersList.add(new BasicNameValuePair("senderId", "remitente"));
	
try {
 //Se fija la codificacion de caracteres de la peticion POST
 post.setEntity(new UrlEncodedFormEntity(parametersList,"UTF-8"));
}
catch(UnsupportedEncodingException uex) {
 System.out.println("ERROR: codificación de caracteres no soportada");
}
	
CloseableHttpResponse response = null;
	
try {
 System.out.println("Enviando petición");
 //Se envía la petición
 response = httpClient.execute(post);
 //Se consigue la respuesta
 String resp = EntityUtils.toString(response.getEntity());
	    
 //Error en la respuesta del servidor
 if (response.getStatusLine().getStatusCode()!=200){
   System.out.println("ERROR: Código de error HTTP:  " + response.getStatusLine().getStatusCode());
   System.out.println("Compruebe que ha configurado correctamente la direccion/url ");
   System.out.println("suministrada por Altiria");
   return;
 }else {
   //Se procesa la respuesta capturada en la cadena 'response'
   if (resp.startsWith("ERROR")){
    System.out.println(resp);
    System.out.println("Código de error de Altiria. Compruebe las especificaciones");
   } else
    System.out.println(resp);
   }
 }
 catch (Exception e) {
   System.out.println("Excepción");
   e.printStackTrace();
   return;
 }
 finally {
    //En cualquier caso se cierra la conexión
    post.releaseConnection();
    if(response!=null) {
     try {
       response.close();
     }
     catch(IOException ioe) {
       System.out.println("ERROR cerrando recursos");
     }
    }
 }
}

   
    
     public void eliminar(){
        
        //cargaTicket();
    }
    
    public void desactivar2(){
    
    
           


        // TODO add your handling code here:
                                         

    }
    public void suma(){
                 
    }
    
    public void desactivar(){
  
    }
    
     public void id()
    {
    String sql= "select Nombre_Producto from producto where Habilitado is null or Habilitado = 'e'";
        try {
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()){
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public void agregar(){
    
     try {
         
                 
         
         String sql= "select Id_Producto, Nombre_Producto,Precio_Producto from producto where Nombre_Producto='";
        Object [] datos = new Object[5];
         
      
         
         Statement s = conexion.createStatement();
         ResultSet rs = s.executeQuery(sql);
         
         while(rs.next()){
              datos[0]=rs.getInt(1);
             datos[1]=rs.getString(2);
              datos[2]=cantidad;
             datos[3]=rs.getInt(3);
       
             
              datos[4]=true;
           
             
             
             
             
             modelo.addRow(datos);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
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

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes1/cliente.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 220, 130, 110));

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
        jLabel15.setText("Mensajes");
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
this.ID = Integer.valueOf(jLabel2.getText());
        System.out.print(ID);
      // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked

       enviar();      // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseClicked
    
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
            java.util.logging.Logger.getLogger(Mensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Mensajes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_imagen;
    // End of variables declaration//GEN-END:variables
}
