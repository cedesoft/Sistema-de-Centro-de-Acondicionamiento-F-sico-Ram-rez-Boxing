/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;


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
public class maquinaria extends javax.swing.JFrame {

    int ID = 0;

    private byte[] huella = null;
    private JPanel contentPane;
    /**
     * File fichero = null; Creates new form Menu_1
     */
    conectar cc = new conectar();
    Connection conexion = cc.conexion();
    Statement statement = null;

    Statement pst = null;

    public maquinaria() {

        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon fot = new ImageIcon(getClass().getResource("/imagenes1/back.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(label_imagen.getWidth(), label_imagen.getHeight(), Image.SCALE_DEFAULT));
        label_imagen.setIcon(icono);

        jComboBox2.removeAllItems();
        jComboBox2.addItem("Id");
        jComboBox2.addItem("Nombre");
cargarf();
        desactivar();
        desactivar1();
        mostrar();
        actualizar();
    }
 void cargarf() {
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
    public void actualizar() {
        TableModel modelo = jTable1.getModel();
        modelo.addTableModelListener(new TableModelListener() {
            @Override

            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int columna = e.getColumn();
                    int fila = e.getFirstRow();
                    System.out.print(columna + "" + fila);

                    if (columna == 1) {
                        if (jTable1.getValueAt(fila, columna).toString().matches("^[a-zA-Z ]{1,45}?$")) {
                            int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
                            if (resp == JOptionPane.YES_OPTION) {

                                try {
                                    String sql1 = " update maquinaria set Nombre= ? where Id=" + jTable1.getValueAt(fila, 0) + "";

                                    PreparedStatement pst = conexion.prepareStatement(sql1);
                                    pst.setString(1, (String) jTable1.getValueAt(fila, columna));
                                    pst.executeUpdate();
                                } catch (SQLException ex) {
                                    Logger.getLogger(maquinaria.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres");
                            mostrar();
                        }

                    }

                    if (columna == 2) {

                        if (jTable1.getValueAt(fila, columna).toString().matches("^[a-zA-Z ]{1,45}?$")) {
                            int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
                            if (resp == JOptionPane.YES_OPTION) {

                                try {
                                    String sql1 = " update maquinaria set Equipo= ? where Id=" + jTable1.getValueAt(fila, 0) + "";

                                    PreparedStatement pst = conexion.prepareStatement(sql1);
                                    pst.setString(1, (String) jTable1.getValueAt(fila, columna));
                                    pst.executeUpdate();
                                } catch (SQLException ex) {
                                    Logger.getLogger(maquinaria.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres");
                            mostrar();
                        }
                    }

                    if (columna == 3) {
                        if (jTable1.getValueAt(fila, columna).toString().matches("^[a-zA-Z ]{1,45}?$")) {
                            int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
                            if (resp == JOptionPane.YES_OPTION) {

                                try {
                                    String sql1 = " update maquinaria set Descripcion= ? where Id=" + jTable1.getValueAt(fila, 0) + "";

                                    PreparedStatement pst = conexion.prepareStatement(sql1);
                                    pst.setString(1, (String) jTable1.getValueAt(fila, columna));
                                    pst.executeUpdate();
                                } catch (SQLException ex) {
                                    Logger.getLogger(maquinaria.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres");
                            mostrar();
                        }
                    }
                    if (columna == 4) {
                        if (jTable1.getValueAt(fila, columna).toString().matches("^[0-9.]{1,7}?$")) {
                            int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
                            if (resp == JOptionPane.YES_OPTION) {

                                try {
                                    String sql1 = " update maquinaria set Costo= ? where Id=" + jTable1.getValueAt(fila, 0) + "";

                                    PreparedStatement pst = conexion.prepareStatement(sql1);
                                    pst.setDouble(1, (Double) jTable1.getValueAt(fila, columna));
                                    pst.executeUpdate();
                                } catch (SQLException ex) {
                                    Logger.getLogger(maquinaria.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
                            mostrar();
                        }
                    }

                }

            }
        });

    }

    public void desactivar() {
        if (txtnombre.getText().length() == 0 || txtequipo.getText().length() == 0 || txtcosto.getText().length() == 0 || txtDescripcion.getText().length() == 0) {
            btnguardar.setEnabled(false);
        } else {
            btnguardar.setEnabled(true);
        }
    }

    public void desactivar1() {
        if (txtbuscar.getText().length() == 0) {
            btnbuscar.setEnabled(false);
        } else {
            btnbuscar.setEnabled(true);
        }
    }

    public void eliminar() {
        try {
            int fila = jTable1.getSelectedRow();
            pst = conexion.createStatement();
            statement = conexion.createStatement();
            String valor = jTable1.getValueAt(fila, 0).toString();

            String sql = "delete from maquinaria where Id = " + valor + "";
            pst.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Se borro la maquina");

        } catch (SQLException ex) {
            Logger.getLogger(Provedor.class.getName()).log(Level.SEVERE, null, ex);

        }
        mostrar();
    }

    public void mostrar() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();

            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    null,
                    new String[]{
                        //Defines TODOS los nombres de las columnas que tendrá la tabla
                        "Id", "Nombre", "Equipo", "Descripcion", "Costo"
                    }
            ) {
                Class[] types = new Class[]{
                    //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                    //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class,};

                //Función que asignará el tipo de campo que asignaste previamente
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }
            });
            modelo = (DefaultTableModel) jTable1.getModel();
            String sql = "select Id, Nombre, Equipo,Descripcion, Costo from maquinaria";
            Object[] datos = new Object[5];

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getInt(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getDouble(5);

                modelo.addRow(datos);
            }

            jTable1.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(maquinaria.class.getName()).log(Level.SEVERE, null, ex);
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
        btnguardar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtequipo = new javax.swing.JTextField();
        btnborrrar = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        txtbuscar = new javax.swing.JTextField();
        btnbuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnreset = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtcosto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
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

        btnguardar.setText("Guardar");
        btnguardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnguardarMouseEntered(evt);
            }
        });
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, -1, -1));

        jLabel3.setText("Descripcion");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, -1, -1));

        jLabel4.setText("Equipo");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, -1, -1));

        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnombreKeyReleased(evt);
            }
        });
        jPanel2.add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 140, -1));

        txtequipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtequipoKeyReleased(evt);
            }
        });
        jPanel2.add(txtequipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 140, -1));

        btnborrrar.setText("Eliminar");
        btnborrrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnborrrarMouseEntered(evt);
            }
        });
        btnborrrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnborrrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnborrrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, -1, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, -1, -1));

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txtbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 70, -1));

        btnbuscar.setText("Buscar");
        btnbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnbuscarMouseEntered(evt);
            }
        });
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });
        jPanel2.add(btnbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, -1, -1));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 40, 10));

        btnreset.setText("Quitar filtro");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });
        jPanel2.add(btnreset, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, -1, -1));

        jLabel5.setText("Costo");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, -1, -1));

        txtcosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcostoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcostoKeyTyped(evt);
            }
        });
        jPanel2.add(txtcosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 140, -1));

        jLabel6.setText("Nombre");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, -1));

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txtDescripcion);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 280, 80));

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

        jScrollPane3.setViewportView(jScrollPane1);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 510, 320));

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
        jLabel15.setText("Maquinaria");
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

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        int id = 0;
        String Nombre = txtnombre.getText();
        String Equipo = txtequipo.getText();
        String Descripcion = txtDescripcion.getText();
        double Costo = Double.parseDouble(txtcosto.getText().toString());

        String sql = "";
        sql = "INSERT INTO maquinaria (Nombre, Equipo, Descripcion, Costo) VALUES (?, ?, ?, ?)";
        try {

            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, Nombre);
            pst.setString(2, Equipo);
            pst.setString(3, Descripcion);
            pst.setDouble(4, Costo);

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

        txtnombre.setText("");
        txtequipo.setText("");
        txtDescripcion.setText("");
        txtcosto.setText("");

// TODO add your handling code here:
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
        try {
            String sql = "";

            String valor = (String) jComboBox2.getSelectedItem();

            DefaultTableModel modelo = new DefaultTableModel();

            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    null,
                    new String[]{
                        //Defines TODOS los nombres de las columnas que tendrá la tabla
                        "Id", "Nombre", "Equipo", "Descripcion", "Costo"
                    }
            ) {
                Class[] types = new Class[]{
                    //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
                    //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class,};

                //Función que asignará el tipo de campo que asignaste previamente
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }
            });
            modelo = (DefaultTableModel) jTable1.getModel();
            boolean verf = true;

            Pattern pat = Pattern.compile("^[0-9]$*");
            Matcher mat = pat.matcher(txtbuscar.getText());
            if (mat.find()) {
                if ("Id".equals(valor)) {
                    verf = true;
                    int id = Integer.parseInt(txtbuscar.getText());
                    sql = "select Id, Nombre, Equipo,Descripcion, Costo from maquinaria WHERE Id LIKE '" + id + "%'";

                } else {
                    verf = false;
                }

            } else {
                verf = false;

                if ("Nombre".equals(valor)) {
                    verf = true;
                    String nombre = txtbuscar.getText();
                    sql = "select Id, Nombre, Equipo,Descripcion,Costo from maquinaria WHERE Nombre LIKE '" + nombre + "%'";
                }

            }

            if (verf) {

                Object[] datos = new Object[5];

                Statement s = conexion.createStatement();
                ResultSet rs = s.executeQuery(sql);

                while (rs.next()) {
                    datos[0] = rs.getInt(1);
                    datos[1] = rs.getString(2);
                    datos[2] = rs.getString(3);
                    datos[3] = rs.getString(4);
                    datos[4] = rs.getDouble(5);

                    modelo.addRow(datos);
                }

                jTable1.setModel(modelo);

                btnbuscar.setEnabled(false);
                txtbuscar.setText(null);
                desactivar();

            }

            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(maquinaria.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizar();
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void btnborrrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnborrrarActionPerformed
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea Borrarr?", "Exit", dialog);
        if (result == dialog) {
            eliminar();
        }
        actualizar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnborrrarActionPerformed

    private void btnguardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnguardarMouseEntered
        desactivar();          // TODO add your handling code here:
    }//GEN-LAST:event_btnguardarMouseEntered

    private void btnbuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbuscarMouseEntered
        desactivar1();         // TODO add your handling code here:
    }//GEN-LAST:event_btnbuscarMouseEntered

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

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreActionPerformed

    private void btnborrrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnborrrarMouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_btnborrrarMouseEntered

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        mostrar();
        actualizar();// TODO add your handling code here:
    }//GEN-LAST:event_btnresetActionPerformed

    private void txtcostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcostoKeyTyped
        desactivar();
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9') && c > '.') {
           evt.consume();  }      // TODO add your handling code here:
    }//GEN-LAST:event_txtcostoKeyTyped

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        if (!txtnombre.getText().matches("^[a-zA-Z ]{1,45}?$")) {
            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres de la a-z");
            txtnombre.setText("");
        }
    }//GEN-LAST:event_txtnombreKeyReleased

    private void txtequipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtequipoKeyReleased
        if (!txtequipo.getText().matches("^[a-zA-Z ]{1,45}?$")) {
            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
            txtequipo.setText("");
        }
    }//GEN-LAST:event_txtequipoKeyReleased

    private void txtcostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcostoKeyReleased
        if (!txtcosto.getText().matches("^[0-9.]{1,6}?$")) {
            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
            txtcosto.setText("");
        }
    }//GEN-LAST:event_txtcostoKeyReleased

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        if (txtbuscar.getText().matches("^[a-zA-Z ]{1,45}?$") || txtbuscar.getText().matches("^[0-9]{1,4}?$")) {
        } else {
            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres A-Z o numero");
            txtbuscar.setText("");
        }
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyReleased
        if (!txtcosto.getText().matches("^[a-zA-Z , \n 0-9]{1,70}?$")) {
            JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres numericos");
            txtcosto.setText("");
        }
    }//GEN-LAST:event_txtDescripcionKeyReleased

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
            java.util.logging.Logger.getLogger(maquinaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(maquinaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(maquinaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(maquinaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new maquinaria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnborrrar;
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnreset;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel label_imagen;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcosto;
    private javax.swing.JTextField txtequipo;
    private javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables
}
