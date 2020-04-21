/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;


import Ventanas.Administradores;
import Ventanas.CapturarHuella;
import Ventanas.Clientes;
import Ventanas.Membresia;
import Ventanas.Menu_1;
import Ventanas.Menu_11;
import Ventanas.conectar;
import Ventanas.menu1;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class LeerHuella_Clientes_verificar extends CapturarHuella{
     conectar cc = new conectar();
    Connection conexion = cc.conexion();
    Statement statement= null;
    private DPFPEnrollment enrollador;
    private DPFPVerification verificador = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate plantillaHuella;
    private DPFPVerificationResult resultado = null;
    private DPFPFeatureSet caracteristicas = null;
     private boolean BUSCAR = false;
     public int ID=0;
     int ID_v = 0;
     boolean verificar_fecha = false;
      menu1 menu = new menu1();
      public int id2=0;
    public LeerHuella_Clientes_verificar(java.awt.Dialog parent, boolean modal, int id) {
    
        super(parent, modal);
        try {
            ID_v=id;
            enrollador = DPFPGlobal.getEnrollmentFactory().createEnrollment();            
        } catch (java.lang.UnsatisfiedLinkError | java.lang.NoClassDefFoundError e) {
            setVisible(false);
        }
          
    }
    @Override
    protected void procesarHuella(DPFPSample sample) {
        super.procesarHuella(sample);
    caracteristicas = extractFeatures(sample, isBUSCAR()?DPFPDataPurpose.DATA_PURPOSE_VERIFICATION:DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
    
    
        if(isBUSCAR()){
           
                try {
                    boolean encontrado = false;
                    String sql = "select Id_Cliente, huella FROM cliente WHERE huella is not null";
                    conectar con = new conectar();
                    Connection conexion = con.conexion();
                    
                    Statement s = conexion.createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    
                    while(rs.next()){
                        resultado = verificador.verify(caracteristicas, DPFPGlobal.getTemplateFactory().createTemplate(rs.getBytes("huella")));
                        if(resultado.isVerified()){
                            
                            encontrado = true;
                            break;
                        }else{
                        
                        
                        }}
                           if(encontrado){
                                
                                  JOptionPane.showMessageDialog(null, "El cleinte ya existe");  
                            }else {
                           encontrado = false;
                                setBUSCAR(false);
                              Clientes obj = new Clientes();

        obj.jLabel2.setText(Integer.toString(ID_v));

        obj.setVisible(true);
        this.setVisible(false);
                            }
                } catch (SQLException ex) {
                    Logger.getLogger(LeerHuella_Clientes_verificar.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
              
            
            
            
        }
    
    if(caracteristicas != null){
            try {
                enrollador.addFeatures(caracteristicas);
            } catch (DPFPImageQualityException e) {
            }finally{
                switch(enrollador.getFeaturesNeeded()){
                    case 4:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/Imagenes/paso0.png")));
                        break;
                    case 3:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/Imagenes/paso1.png")));                               
                        break;
                    case 2:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/Imagenes/paso2.png")));
                        break;
                    case 1:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/Imagenes/paso3.png")));
                        break;
                    case 0:
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/Imagenes/paso4.png")));
                        break;
                }
                 switch(enrollador.getTemplateStatus()){
                    case TEMPLATE_STATUS_READY:
                        stop();
                        plantillaHuella = enrollador.getTemplate();
                        setVisible(false);
                        break;

                    case TEMPLATE_STATUS_FAILED:
                        enrollador.clear();
                        stop();
                        plantillaHuella = null;
                        lblPasos.setIcon(new ImageIcon(getClass().getResource("/Imagenes/paso0.png")));                        
                        start();
                        break;                                
                    default: break;
                }
    }}}
public void id2(int id){
id2=id;
}
    
    public boolean isBUSCAR() {
        return BUSCAR;
    }
 
    public void setBUSCAR(boolean buscar) {
        this.BUSCAR = buscar;
    }
     public DPFPTemplate getPlantillaHuella() {
        return plantillaHuella;
    }

    public void setPlantillaHuella(DPFPTemplate plantillaHuella) {
        this.plantillaHuella = plantillaHuella;
    }
    
}
