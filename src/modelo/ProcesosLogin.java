/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author casid
 */
public class ProcesosLogin {
    Conexion con;
    private static ProcesosLogin estanciaProcesosLogin;
    
    public static ProcesosLogin getProcesosInstancia(){
        if(estanciaProcesosLogin == null){
            estanciaProcesosLogin = new ProcesosLogin();
        }
        return estanciaProcesosLogin;
    }
    
    private ProcesosLogin(){
        con = new Conexion();
        con.conectar();
    }
    
    public boolean login(String name, String password){
        password = transformpasswordlog(password);
        System.out.println(password);
        con.updateuser(name);
        boolean logged = false;
        try {
            if (con.checkname(name) == false) {
                JOptionPane.showMessageDialog(null, "No existe el usuario");
            } else if (con.checkpass(name, password) == false) {
                JOptionPane.showMessageDialog(null, "Contraseña erronea");
            } else {
                JOptionPane.showMessageDialog(null, "Login Correcto");
                logged = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al interactuar con la base de datos");
        
        }
        return logged;
    }
    
    private String transformpasswordlog(String passwords) {
        String passwd = passwords;
        /*Transforma las contraseñas en md5*/
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(passwd.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            passwd = sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }

        return passwd;
    }
    
    public boolean registro(String name, String password, String comfirmpassword){
    String[] passwords = new String[]{password, comfirmpassword};
        ArrayList<String> passwordsmd5 = transformpassword(passwords);
        con.updateuser(name);
        boolean register =  false;
        if ( passwords[0].isEmpty() || passwords[1].isEmpty()) {
            JOptionPane.showMessageDialog(null, "La contraseña no puede estar vacia");
        } else {
            try {
                if (con.checkname(name) == false) {
                    if (passwordsmd5.get(1).equals(passwordsmd5.get(0))) {
                        /*Inserta el usuario en la base de datos*/
                        con.Insert(name, passwordsmd5.get(1));

                        JOptionPane.showMessageDialog(null, "Se registro correctamente");
                        register = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "La contraseña no coincide");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ya existe el usuario");
                }
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error al interactuar con la base de datos");
            } catch (ClassNotFoundException cnfe) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error. No se encuentra la clase especificada");
            }
        }
        return register;
    }
    
    private ArrayList<String> transformpassword(String[] passwords) {
        String[] passwd = passwords;
        ArrayList<String> passwordsmd5 = new ArrayList<>();
        for (String password : passwd) {

            /*Transforma las contraseñas en md5*/
            try {
                java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
                byte[] array = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < array.length; ++i) {
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
                }
 
                passwordsmd5.add(sb.toString());
            } catch (java.security.NoSuchAlgorithmException e) {
            }

        }
        return passwordsmd5;
    }
}
