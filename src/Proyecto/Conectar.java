/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author casid
 */
/*Conecta el objeto  con la base de datos*/
public class Conectar {

    private String driver = "com.mysql.jdbc.Driver";
    String connect = "jdbc:mysql://127.0.0.1/empresa";
    private String user = "root";
    private String contra = "";
    private String current_user = "";
    public Connection con;

    public Conectar(String usuario) {
        this.current_user = usuario;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(connect, user, contra);
            JOptionPane.showMessageDialog(null, "Se pudo conectar con la base de datos");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la base de datos");
        }
    }
    
    public String getuser(){
        return current_user;
    }
    
    public void Insert(String name, String password) throws ClassNotFoundException, SQLException {
        Statement stmt;
        stmt = (Statement) con.createStatement();
        String query1 = "INSERT INTO users (Name, Pass) VALUES ('" + name + "','" + password + "')";
        stmt.executeUpdate(query1);
    }

    /*Busca el nombre de usuario en la BD*/
    public boolean checkname(String name) throws SQLException {
        boolean existe = false;
        Statement stmt;
        String query = "SELECT * FROM `users` WHERE `Name`='" + name + "'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        if (result.next()) {
            existe = true;
        }
        return existe;
    }

    public boolean checkpass(String name, String pass) throws SQLException {
        boolean iguales = false;
        Statement stmt;
        String query = "Select * from users where Name = '" + name + "'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        String password = (result.getString("Pass"));
        if (pass.equals(password)) {
            iguales = true;
        }
        return iguales;
    }
}
