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
public class Conectar {
       private String driver="com.mysql.jdbc.Driver";
       String connect="jdbc:mysql://127.0.0.1/empresa";
       private String user="root";
       private String contra="";
       public Connection con;
       
       public Conectar(){
           try{
               Class.forName(driver);
               con=DriverManager.getConnection(connect, user, contra);
               JOptionPane.showMessageDialog(null, "Se pudo conectar con la base de datos");
           }catch(HeadlessException | ClassNotFoundException | SQLException e){
               JOptionPane.showMessageDialog(null, "No se pudo conectar con la base de datos");
           }
       }
       public void Insert(String name, String password) throws ClassNotFoundException, SQLException{
           try{
           Statement stmt;
           stmt = (Statement) con.createStatement();
           String query1 = "INSERT INTO users (Name, Pass) VALUES ('"+name+"','"+password+"')";
           stmt.executeUpdate(query1);
           
           }catch(HeadlessException | SQLException e){
           
           }
       }
       
       public boolean checkname(String name) throws SQLException{
            Statement stmt;
            String query = "Select Name from users";
            stmt = (Statement) con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            boolean existe = false;

            while (result.next() && existe == false) {
                String nombre = result.getString("Name");
                if (nombre.equals(name)) {
                    existe = true;
                }
            }
            return existe;
        }
}
