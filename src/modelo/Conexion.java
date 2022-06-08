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
import vista.Alerta;

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
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la base de datos");
        }
    }
        
    public void setuser(String usuario){
         this.current_user = usuario;
    }
    
       public void Insert(String name, String password, String tipo) throws ClassNotFoundException, SQLException {
        Statement stmt;
        stmt = (Statement) con.createStatement();
        String query1 = "INSERT INTO users (Name, Pass, Tipo) VALUES ('" + name + "','" + password + "','"+tipo+"')";
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

    public String OficinaFromId(String ID) throws SQLException{
        Statement stmt;
        String oficina;
        String query = "Select * from oficina where IDoficina = " + ID ;
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        oficina = result.getString("Nombreoficina");
        return oficina;
    }
    
    public void BorrarEmpleado(String ID) throws SQLException{
        Statement stmt;
        String query = "Delete from empleado where DNIempleado = '" + ID +"'";
        stmt = (Statement) con.createStatement();
        stmt.executeUpdate(query);
    }
    
    public void AñadirEmpleado(String[] datos) throws SQLException{
        Statement stmt;
        String oficina = datos[3];
        String query = "Select * from oficina where Nombreoficina = '" + oficina + "'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        oficina = result.getString("IDoficina");
        int estado;
        if(datos[9] == "Esta de Baja"){
            estado = 0;
        }else{
            estado = 1;
        }
        String query2 = "Insert Into empleado (DNIempleado, Nombreempleado, ApellidosEmpleado, IDoficina, Telefono, Correo, Sueldo, Direccion, FechaIngreso, IsBaja) "
                + "values ('"+ datos[0] +"','"+ datos[2] +"','"+ datos[1] +"',"+ oficina + ","+ datos[4]+",'"+ datos[5]+"',"+ datos[6]+",'"+ datos[8]+"','"+ datos[7]+"',"+ estado+")";
        stmt = (Statement) con.createStatement();
        stmt.executeUpdate(query2);
    }
    
    public void ModificarEmpleado(Empleado empleado) throws SQLException{
        Statement stmt;
        String oficina = empleado.getOficina();
        String query = "Select * from oficina where Nombreoficina = '" + oficina + "'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        oficina = result.getString("IDoficina");
        int estado;
        if(empleado.getEstado() == "Esta de Baja"){
            estado = 0;
        }else{
            estado = 1;
        }
        String query2 = "update empleado  "
                + "set Nombreempleado = '"+empleado.getNombre()+"', ApellidosEmpleado = '"+empleado.getApellidos()+"', "
                + "IDoficina = "+oficina+", Telefono = "+empleado.getTelefono()+", Correo = '"+empleado.getCorreo()+"', "
                + "Sueldo ="+empleado.getSueldo()+",Direccion = '"+empleado.getDireccion()+"', IsBaja = "+ estado + ""
                + " where DNIempleado = '"+empleado.getDNI()+"'";
        stmt = (Statement) con.createStatement();
        stmt.executeUpdate(query2);
    }
    
    public void BorrarUsuario(String nombre) throws SQLException{
        Statement stmt;
        String query = "Delete from users where Name = '" + nombre +"'";
        stmt = (Statement) con.createStatement();
        stmt.executeUpdate(query);
    }
    
    public ResultSet buscar(String buscar, String opc, String table) throws SQLException {
        ResultSet result;
        Statement stmt;
        if (opc.equals("IDoficina") || opc.equals("IsBaja")) {
            if (opc.equals("IDoficina")) {
                String oficina = buscar;
                String query = "Select * from oficina where Nombreoficina = '" + oficina + "'";
                stmt = (Statement) con.createStatement();
                result = stmt.executeQuery(query);
                result.next();
                buscar = result.getString("IDoficina");
            } else if (opc.equals("IsBaja")) {
                if (buscar.equals("Esta de baja")) {
                    buscar = "0";
                } else {
                    buscar = "1";
                }
            }
            String query = "Select * from " + table + " where " + opc + " = " + buscar;
            stmt = (Statement) con.createStatement();
            result = stmt.executeQuery(query);
        } else {
            String query = "Select * from " + table + " where " + opc + " like '%" + buscar + "%'";
            stmt = (Statement) con.createStatement();
            result = stmt.executeQuery(query);
        }
        return result;
    }
    
    public void ModificarUsuario(String usuario, String user, String tipo) throws SQLException{
        Statement stmt;
        System.out.println(usuario);
        String query = "update users set Name = '"+usuario+"', Tipo = '"+tipo+"' where Name = '"+user+"'";
        stmt = (Statement) con.createStatement();
        stmt.executeUpdate(query);
    }
    
    public String clientefromid(String id) throws SQLException {
        String cliente;
        Statement stmt;
        String query = "Select * from cliente where DNICliente = '"+id+"'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        cliente = result.getString("NombreCliente") + " " + result.getString("ApellidosClientes");
        return cliente;
    }

    public String distribuidorafromid(String id) throws SQLException {
        String distribuidora;
        Statement stmt;
        String query = "Select * from distribuidora where IDdistribuidora = '"+id+"'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        distribuidora = result.getString("Nombre");
        return distribuidora;
    }
        
    public String oficinafromid(String id) throws SQLException {
        String oficina;
        Statement stmt;
        String query = "Select * from oficina where IDoficina  = '"+id+"'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        oficina = result.getString("Nombreoficina");
        return oficina;
    }
    public String herramientafromid(String id) throws SQLException {
        String herramienta;
        Statement stmt;
        String query = "Select * from herramientas where IDherramientas  = "+id;
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        herramienta = result.getString("Nombreherramienta");
        return herramienta;
    }    
  
    public ResultSet showpedido(String Npedido) throws SQLException{
        Statement stmt;
        String query = "Select * from pedidoherramienta where IDpedido = "+ Npedido;
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        return result;
    }
    
    public ResultSet showalmacen(String almacen) throws SQLException{
        Statement stmt;
        String query = "Select * from alamacenherramienta where IDalmacen = "+ almacen;
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        return result;
    }
    
    public String idfromalmacen(String almacen) throws SQLException {
        String idalmacen;
        Statement stmt;
        String query = "Select * from almacen where Nombrealmacen = '"+almacen+"'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        idalmacen = result.getString("IDalmacen");
        return idalmacen;
    }
    
    public ResultSet buscarpedidos(String opc, String fecha, String buscar) throws SQLException{
        Statement stmt;
        ResultSet result;
        String query;
        switch (opc) {
                case "Cliente":
                    query = "Select pedidos.* from pedidos, cliente where cliente.Nombrecliente like '%"+buscar+"%' and pedidos.IDcliente=cliente.DNICliente";
                    stmt = (Statement) con.createStatement();
                    result = stmt.executeQuery(query);
                    break;
                case "Fecha realizado":
                    query = "Select * from pedidos where FechaRealizado = '"+fecha+"'";
                    stmt = (Statement) con.createStatement();
                    result = stmt.executeQuery(query);
                    break;
                case "Fecha entregado":
                    query = "Select * from pedidos where FechaEntrega = '"+fecha+"'";
                    stmt = (Statement) con.createStatement();
                    result = stmt.executeQuery(query);
                    break;
                case "Distribuidora":
                    query = "Select pedidos.* from pedidos, distribuidora where distribuidora.Nombre like '%"+buscar+"%' and pedidos.IDdistribuidora=distribuidora.IDdistribuidora";
                    stmt = (Statement) con.createStatement();
                    result = stmt.executeQuery(query);
                    break;
                default:
                    result = null;
                    Alerta.mostrarAlertWarning("Selecione el tipo de busqueda");
        }
        return result;
    }
    
    public ResultSet buscaralmacenes(String opc, String buscar) throws SQLException{
        ResultSet result;
        Statement stmt;
        String query;
        if(opc.equals("Almacen")){
            query = "Select * from almacen where Nombrealmacen like '%"+buscar+"%'";
            stmt = (Statement) con.createStatement();
            result = stmt.executeQuery(query);
        } else if(opc.equals("Oficina")){
            query = "Select almacen.* from almacen, oficina where oficina.Nombreoficina like '%"+buscar+"%' and oficina.IDoficina=almacen.IDoficina";
            stmt = (Statement) con.createStatement();
            result = stmt.executeQuery(query);
        } else {
            result = null;
            Alerta.mostrarAlertWarning("Selecione el tipo de busqueda");
        }
        return result;
    }
    
    public void modificarcantidad(String cantidad, String idh, String ida) throws SQLException {
        Statement stmt;
        String query = "update alamacenherramienta set Cantidad = "+ cantidad +" where IDherramienta = "+idh+" and IDalmacen = "+ida;
        stmt = (Statement) con.createStatement();
        stmt.executeUpdate(query);
    }

    public String idfromherramienta(String herramienta) throws SQLException {
        String idherramienta;
        Statement stmt;
        String query = "Select * from herramientas where Nombreherramienta = '"+herramienta+"'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        idherramienta = result.getString("IDherramientas");
        return idherramienta;
    }

    public ResultSet showherramientas() throws SQLException {
        Statement stmt;
        String query = "Select herramientas.Nombreherramienta, sum(alamacenherramienta.Cantidad) from herramientas, alamacenherramienta "
                + "where alamacenherramienta.IDherramienta = herramientas.IDherramientas group by IDherramienta";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        return result;
    }
    
    
    public ResultSet buscarherramientas(String buscar) throws SQLException {
        Statement stmt;
        String query = "Select herramientas.Nombreherramienta, sum(alamacenherramienta.Cantidad) from herramientas, alamacenherramienta "
                + "where alamacenherramienta.IDherramienta = herramientas.IDherramientas and herramientas.Nombreherramienta like '%"+buscar+"%' group by IDherramienta";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        return result;
    }

    public ResultSet buscaralmacen(String herramienta) throws SQLException {
        Statement stmt;
        String query = "Select almacen.Nombrealmacen, alamacenherramienta.Cantidad from herramientas, alamacenherramienta, almacen "
                + "where alamacenherramienta.IDherramienta = herramientas.IDherramientas "
                + "and alamacenherramienta.IDalmacen = almacen.IDalmacen "
                + "and herramientas.Nombreherramienta = '"+herramienta+"'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        return result;
    }
    
    public boolean checkempleado(String name) throws SQLException {
        boolean empleado = false;
        Statement stmt;
        String query = "Select * from users where Name = '"+name+"'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        if(result.getString("Tipo").equals("empleado")){
            empleado = true;
        }
        return empleado;
    }
    
    public boolean checkDNI(String DNI) throws SQLException {
        boolean encontrado = false;
        Statement stmt;
        String query = "Select * from cliente where DNIcliente = '"+DNI+"'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        if(result.next()){
            encontrado = true;
        }
        return encontrado;
    }
    
    public Integer getuserid(String user) throws SQLException{
        Integer iduser;
        Statement stmt;
        String query = "Select * from users where Name = '"+user+"'";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        iduser = result.getInt("ID");
        return iduser;
    }
    
    public void addcliente(String nombre, String apellidos, String DNI, String user) throws SQLException {
        Integer iduser;
        iduser = getuserid(user);
        Statement stmt;
        stmt = (Statement) con.createStatement();
        String query = "INSERT INTO cliente (DNICliente, Nombrecliente, ApellidosClientes, IDusuario) VALUES ('" + DNI + "','" + nombre + "','"+apellidos+"', "+iduser+")";
        stmt.executeUpdate(query);
    }
    
    public String getcliente(String user) throws SQLException {
        String cliente;
        Statement stmt;
        String query = "Select cliente.* from cliente, users where users.Name = '"+user+"' and cliente.IDusuario = users.ID";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        cliente = (result.getString("Nombrecliente")+" "+result.getString("ApellidosClientes"));
        return cliente;
    }
    
    public void updatecantidad(String idherramienta, String idalmacen, Integer queda) throws SQLException {
        Statement stmt;
        stmt = (Statement) con.createStatement();
        String query = "update alamacenherramienta set Cantidad = "+queda+" where IDherramienta = "+idherramienta+" and IDalmacen = "+idalmacen;
        stmt.executeUpdate(query);
    }
    
    public String getidcliente(String user) throws SQLException {
        String idcliente;
        Statement stmt;
        String query = "Select cliente.* from cliente, users where users.Name = '"+user+"' and cliente.IDusuario = users.ID";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        idcliente = result.getString("DNICliente");
        return idcliente;
    }

    public void añadirpedido(String DNIcliente, String iddistribuidora) throws SQLException {
        Statement stmt;
        stmt = (Statement) con.createStatement();
        String query = "INSERT INTO pedidos (IDcliente, FechaRealizado, IDdistribuidora) VALUES ('" + DNIcliente + "', curdate() ,'"+iddistribuidora+"')";
        stmt.executeUpdate(query);
    }

    String getlastpedido() throws SQLException {
        String idpedido;
        Statement stmt;
        String query = "SELECT * FROM pedidos ORDER BY pedidos.IDpedido DESC LIMIT 1";
        stmt = (Statement) con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        idpedido = result.getString("IDpedido");
        return idpedido;
    }
    
    void añadirdatopedido(String idpedido, String idfromherramienta, String cantidades) throws SQLException {
        Statement stmt;
        stmt = (Statement) con.createStatement();
        String query = "INSERT INTO pedidoherramienta (IDpedido, IDherramienta, Cantidad) VALUES (" + idpedido + ","+idfromherramienta+","+cantidades+")";
        stmt.executeUpdate(query);
    }
    
    public void pedidoentregado(String npedido) throws SQLException {
        Statement stmt;
        String query = "update pedidos set FechaEntrega = curdate() where IDpedido = "+npedido;
        stmt = (Statement) con.createStatement();
        stmt.executeUpdate(query);
    }
    
    public String getuser(){
        return current_user;
        
    }
}
