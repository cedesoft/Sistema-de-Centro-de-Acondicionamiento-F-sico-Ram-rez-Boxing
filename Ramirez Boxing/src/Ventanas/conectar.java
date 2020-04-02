/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author gumaro
 */
public class conectar {
    
    Connection conect = null;
    public Connection conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
             conect =DriverManager.getConnection("jdbc:mysql://localhost/ramirezboxingclub", "root", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error"+e);
        }
        return conect;
    }
    
}
