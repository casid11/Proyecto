/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

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
public class Conexion  {

    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String CONNECT = "jdbc:mysql://127.0.0.1/empresa";
    private final String USER = "root";
    private final String CONTRA = "";
    private String current_user = "";
    public Connection con;
    
    public Conexion() {
    }
    
    public void conectar(){
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECT, USER, CONTRA);
            JOptionPane.showMessageDialog(null, "Se pudo conectar con la base de datos");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la base de datos");
        }
    }
    
    public void updateuser(String usuario){
         this.current_user = usuario;
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
        String query = "Select * from users where Name = '" + name + "'";
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
    
    public ResultSet showtable(String table) throws SQLException{
        Statement stmt;
        String query = "Select * from "+table+" ";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        return result;
    }

    public String getuser(){
        return current_user;
    }
    
}
