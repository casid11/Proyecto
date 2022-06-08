/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import vista.Alerta;

/**
 *
 * @author casid
 */
public class Procesos {
    Conexion con;
    private final String TRABAJANDO="Esta Trabajando";
    private final String BAJA="Esta de baja";
    
    private static Procesos estanciaProcesosLogin;
    
    public static Procesos getProcesosInstancia(){
        if(estanciaProcesosLogin == null){
            estanciaProcesosLogin = new Procesos();
        }
        return estanciaProcesosLogin;
    }
    
    private Procesos(){
        con = new Conexion();
        con.conectar();
    }
    
    public boolean login(String name, String password){
        password = transformpasswordlog(password);
        boolean logged = false;
        try {
            if (con.checkname(name) == false) {
                 Alerta.mostrarAlertWarning("No existe el usuario");
            } else if (con.checkpass(name, password) == false) {
                 Alerta.mostrarAlertWarning("Contraseña erronea");
            } else {
                con.setuser(name);
                Alerta.mostrarAlertWarning("Login Correcto");
                logged = true;
            }
        } catch (SQLException ex) {
             Alerta.mostrarAlertWarning("Se ha producido un error al interactuar con la base de datos");
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
    
    public boolean registro(String name, String password, String comfirmpassword, String tipo, String nombre, String apellidos, String DNI) {
        String[] passwords = new String[]{password, comfirmpassword};
        ArrayList<String> passwordsmd5 = transformpassword(passwords);
        boolean register = false;
        try {
            if (passwords[0].isEmpty() || passwords[1].isEmpty()) {
                JOptionPane.showMessageDialog(null, "La contraseña no puede estar vacia");
            } else {
                if (con.checkname(name) == false) {
                    if (passwordsmd5.get(1).equals(passwordsmd5.get(0))) {
                        con.Insert(name, passwordsmd5.get(1), tipo);
                        con.setuser(name);

                        JOptionPane.showMessageDialog(null, "Se registro correctamente");
                        register = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "La contraseña no coincide");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ya existe el usuario");
                }
            }
            
            if (tipo.equals("cliente")) {

                if (!con.checkDNI(DNI)) {
                    con.addcliente(nombre, apellidos, DNI, name);
                } else {
                    Alerta.mostrarAlertWarning("El DNI ya esta en uso");
                }
            }
        } catch (SQLException sqle) {
            Alerta.mostrarAlertWarning("Se ha producido un error al interactuar con la base de datos");
        } catch (ClassNotFoundException cnfe) {
            Alerta.mostrarAlertWarning("Se ha producido un error. No se encuentra la clase especificada");
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

    public ObservableList<Empleado> obtenerempleados() {
        ObservableList<Empleado> data = null;
        try {
            data = FXCollections.observableArrayList();
            String estado;
            ResultSet result = con.showtable("empleado");
            while (result.next()) {
                String oficina = con.OficinaFromId(result.getString("IDoficina"));
                if (result.getInt("IsBaja") == 0) {
                    estado = BAJA;
                } else {
                    estado = TRABAJANDO;
                }
                data.add(
                        new Empleado(
                                result.getString("DNIempleado"),
                                result.getString("ApellidosEmpleado"),
                                result.getString("Nombreempleado"),
                                oficina,
                                result.getString("Telefono"),
                                result.getString("Correo"),
                                result.getString("Sueldo"),
                                result.getString("FechaIngreso"),
                                result.getString("Direccion"),
                                estado)
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public void borrarempleado(String DNI){
        try {
            con.BorrarEmpleado(DNI);
        } catch (SQLException ex) {
            Alerta.mostrarAlertWarning("Error al borrar");
        }
    }
    
    public Empleado crearempleado(String[] datos){
        Empleado empleado = new Empleado(
                datos[0],
                datos[1],
                datos[2],
                datos[3],
                datos[4],
                datos[5],
                datos[6],
                datos[7],
                datos[8],
                datos[9]
        );
        try {
            con.AñadirEmpleado(datos);
        } catch (SQLException ex) {
            Alerta.mostrarAlertWarning("Error al añadir el usuario a la base de datos");
            empleado= null;
        }
        return empleado;
    }
    
    public void modificarempleado(Empleado empleado){
        try {
            con.ModificarEmpleado(empleado);
        } catch (SQLException ex) {
            //Alerta.mostrarAlertWarning("Error al al modificar");
            ex.printStackTrace();
        }
    }
    
    public ObservableList<Usuario> obtenerusuarios() {
        ObservableList<Usuario> data = null;
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = con.showtable("users");
            while (result.next()) {
                data.add(
                        new Usuario(result.getString("Name"), result.getString("Tipo"))
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public void borrarusuario(String nombre){
        try {
            con.BorrarUsuario(nombre);
        } catch (SQLException ex) {
            Alerta.mostrarAlertWarning("Error al borrar");
        }
    }
    
    public ObservableList<Empleado> bucarempleado(String buscar, String opc) {
        ObservableList<Empleado> data = null;
        ResultSet result;
        
        switch (opc) {
            case "DNI":
                opc = "DNIempleado";
                break;
            case "Apellidos":
                opc = "ApellidosEmpleado";
                break;
            case "Nombre":
                opc = "Nombreempleado";
                break;
            case "Oficina":
                opc = "IDoficina";
                break;
            case "Estado":
                opc = "IsBaja";
                break;
            default:
                Alerta.mostrarAlertWarning("Selecione el tipo de busqueda");
        }
        
        try {
            data = FXCollections.observableArrayList();
            result = con.buscar(buscar, opc, "empleado");
            String estado;
            String oficina;
            while (result.next()) {
                oficina = con.OficinaFromId(result.getString("IDoficina"));
                if (result.getInt("IsBaja") == 0) {
                    estado = BAJA;
                } else {
                    estado = TRABAJANDO;
                }
                data.add(
                        new Empleado(
                                result.getString("DNIempleado"),
                                result.getString("ApellidosEmpleado"),
                                result.getString("Nombreempleado"),
                                oficina,
                                result.getString("Telefono"),
                                result.getString("Correo"),
                                result.getString("Sueldo"),
                                result.getString("FechaIngreso"),
                                result.getString("Direccion"),
                                estado)
                );
            }
            
        } catch (SQLException ex) {
            Alerta.mostrarAlertWarning("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public ObservableList<Usuario> bucarusuario(String buscar) {
        ObservableList<Usuario> data = null;
        ResultSet result;
        
        try {
            data = FXCollections.observableArrayList();
            result = con.buscar(buscar, "Name", "users");
            
            while (result.next()) {
                data.add(
                        new Usuario(result.getString("Name"), result.getString("Tipo"))
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public void modificarusuario(Usuario usuario, String user, String tipo){
        try {
            con.ModificarUsuario(usuario.getNombreUsuario(), user, tipo);
        } catch (SQLException ex) {
            //Alerta.mostrarAlertWarning("Error al al modificar");
            ex.printStackTrace();
        }
    }
    
    public ObservableList<Pedidos> obtenerpedidos() {
        ObservableList<Pedidos> data = null;
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = con.showtable("pedidos");
            String cliente;
            String distribuidora;
            while (result.next()) {
                cliente = con.clientefromid(result.getString("IDcliente"));
                distribuidora = con.distribuidorafromid(result.getString("IDdistribuidora"));
                data.add(
                        new Pedidos(
                                result.getString("IDpedido"),
                                cliente,
                                result.getString("FechaRealizado"),
                                result.getString("FechaEntrega"),
                                distribuidora
                        )
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public ObservableList<Pedido> obtenerpedido(String Npedido) {
        ObservableList<Pedido> data = null;
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = con.showpedido(Npedido);
            String herramienta;
            while (result.next()) {
                herramienta = con.herramientafromid(result.getString("IDherramienta"));
                data.add(
                        new Pedido(
                                herramienta,
                                result.getString("Cantidad")
                        )
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public ObservableList<Almacenes> obteneralmacenes() {
        ObservableList<Almacenes> data = null;
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = con.showtable("almacen");
            String oficina;
            while (result.next()) {
                oficina = con.oficinafromid(result.getString("IDoficina"));
                data.add(
                        new Almacenes(
                                result.getString("Nombrealmacen"),
                                oficina
                        )
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }

     public ObservableList<Herramientas> obtenerherramienta(String almacen) {
        ObservableList<Herramientas> data = null;
        try {
            almacen = con.idfromalmacen(almacen);
            data = FXCollections.observableArrayList();
            ResultSet result = con.showalmacen(almacen);
            String herramienta;
            while (result.next()) {
                herramienta = con.herramientafromid(result.getString("IDherramienta"));
                data.add(
                        new Herramientas(
                                herramienta,
                                result.getString("Cantidad")
                        )
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    public ObservableList<Pedidos> bucarpedido(String opc, String fecha, String buscar){
        ObservableList<Pedidos> data = null;
        ResultSet result;
        try {
            data = FXCollections.observableArrayList();
            result = con.buscarpedidos(opc, fecha, buscar);
            String cliente;
            String distribuidora;
            if(result != null){
                while (result.next()) {
                    cliente = con.clientefromid(result.getString("IDcliente"));
                    distribuidora = con.distribuidorafromid(result.getString("IDdistribuidora"));
                    data.add(
                            new Pedidos(
                                    result.getString("IDpedido"),
                                    cliente,
                                    result.getString("FechaRealizado"),
                                    result.getString("FechaEntrega"),
                                    distribuidora
                            )
                    );
                }
            }
        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public ObservableList<Almacenes> buscaralmacenes(String opc, String buscar){
        ObservableList<Almacenes> data = null;
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = con.buscaralmacenes(opc, buscar);
            String oficina;
            if(result != null){
                while (result.next()) {
                    oficina = con.oficinafromid(result.getString("IDoficina"));
                    data.add(
                            new Almacenes(
                                    result.getString("Nombrealmacen"),
                                    oficina
                            )
                    );
                }
            }
            
        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public void modificarcantidad(String cantidad, String idh, String ida) throws SQLException {
        idh = con.idfromherramienta(idh);
        ida = con.idfromalmacen(ida);
        con.modificarcantidad(cantidad, idh, ida);
    }

    public ObservableList<Herramientas> obtenerherramientas() {
        ObservableList<Herramientas> data = null;
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = con.showherramientas();
            while (result.next()) {
                data.add(
                        new Herramientas(
                                result.getString("Nombreherramienta"),
                                result.getString("sum(alamacenherramienta.Cantidad)")
                        )
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public ObservableList<Herramientas> obtenerherramientasstock() {
        ObservableList<Herramientas> data = null;
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = con.showherramientas();
            String herramienta;
            while (result.next()) {
                if(result.getString("sum(alamacenherramienta.Cantidad)").equals("0")){
                    herramienta = (result.getString("Nombreherramienta")+" - "+"Fuera de stock");
                }else{
                    herramienta = result.getString("Nombreherramienta");
                }
                data.add(
                        new Herramientas(
                                herramienta,
                                result.getString("sum(alamacenherramienta.Cantidad)")
                        )
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public ObservableList<Herramientas> bucarherramienta(String buscar) {
        ObservableList<Herramientas> data = null;
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = con.buscarherramientas(buscar);
            while (result.next()) {
                data.add(
                        new Herramientas(
                                result.getString("Nombreherramienta"),
                                result.getString("sum(alamacenherramienta.Cantidad)")
                        )
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public ObservableList<AlmacenHerramientas> obteneralmacenesherramientas(String herramienta) {
        ObservableList<AlmacenHerramientas> data = null;
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = con.buscaralmacen(herramienta);
            while (result.next()) {
                data.add(
                        new AlmacenHerramientas(
                                result.getString("Nombrealmacen"),
                                result.getString("Cantidad")
                        )
                );
            }

        } catch (SQLException e) {
            Alerta.mostrarAlertError("Error al interactuar con la base de datos");
        }
        return data;
    }
    
    public boolean checkempleado(String name) {
        boolean empleado = false;
        try {
            empleado = con.checkempleado(name);
        } catch (SQLException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleado;
    }
    
    public boolean comprar(String herramienta, int cantidades) {
        boolean realizado = false;
        try {
            
            String idherramienta = con.idfromherramienta(herramienta);
            ResultSet result =  con.showtable("alamacenherramienta");
            while(cantidades > 0 && result.next()){
                if(result.getString("IDherramienta").equals(idherramienta)){
                    Integer tiene = Integer.parseInt(result.getString("Cantidad"));
                    if(tiene >= cantidades){
                        Integer queda = tiene - cantidades;
                        con.updatecantidad(idherramienta, result.getString("IDalmacen"), queda);
                        cantidades = 0;
                        realizado = true;
                    }else{
                        cantidades = cantidades - tiene;
                        tiene = 0;
                        con.updatecantidad(idherramienta, result.getString("IDalmacen"), tiene);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return realizado;
    }
    
    public String getcliente() throws SQLException{
        return con.getcliente(getuser());
    }
    
    public void añadirpedido(ObservableList<Herramientas> datacesta) throws SQLException{
        String DNIcliente = con.getidcliente(getuser());
        ResultSet result = con.showtable("distribuidora");
        ArrayList<String> distribuidoras = new ArrayList<>();
        int i = 0;
        while(result.next()){
            distribuidoras.add(result.getString("IDdistribuidora"));
            i++;
        }
        i--;
        int numero = (int)(Math.random()*(i-0+1)+0);  
        String iddistribuidora = distribuidoras.get(numero);
        con.añadirpedido(DNIcliente, iddistribuidora);
        String idpedido = con.getlastpedido();
        i = 0;
        while (i < datacesta.size()) {
                con.añadirdatopedido(idpedido, con.idfromherramienta(datacesta.get(i).getHerramienta()), datacesta.get(i).getCantidades());
                i++;
        }
    }
    
    public void pedidoentregado(String npedido) {
        try {
            con.pedidoentregado(npedido);
        } catch (SQLException ex) {
            Alerta.mostrarAlertWarning("Error al interactuar con la base de datos");
        }
    }
    
    public String getuser(){
        return con.getuser();
    }
    
    public Conexion getCon() {
        return con;
    }
}
