
package  Ventanas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Dvn
 */
public class conectar {

    Connection conect = null;
   public Connection conexion()
     {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conect = DriverManager.getConnection("jdbc:mysql://localhost/ramirezboxingclub", "root","");
          //  JOptionPane.showMessageDialog(null, "Conectado");
       
      } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null,"Error "+e);
      
        }
        return conect;
     
}}
