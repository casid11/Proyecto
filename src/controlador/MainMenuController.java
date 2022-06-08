package controlador;

import java.io.IOException;
import java.net.URL;
import java.text.*;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Empleado;
import modelo.Procesos;
import modelo.Usuario;
import vista.Alerta;


public class MainMenuController implements Initializable{
    private Procesos procesos;
    private ObservableList<Empleado> data;
    private ObservableList<Usuario> datausuario;
    private Empleado empleado;
    private Usuario usuario;
    private ObservableList<String> opccombobox;
    private ObservableList<String> opccombobusqueda;
    private ObservableList<String> opctipo;
    
    
    @FXML
    private TextField apellidosTF;

    @FXML
    private TextField buscarempleadoTF;

    @FXML
    private TextField correoTF;

    @FXML
    private TextField direccionTF;

    @FXML
    private TextField dniempleadoTF;

    @FXML
    private TableColumn<?, ?> idApellidos;

    @FXML
    private TableColumn<?, ?> idCorreo;

    @FXML
    private TableColumn<?, ?> idDNI;

    @FXML
    private TableColumn<?, ?> idDireccion;

    @FXML
    private TableColumn<?, ?> idEstado;

    @FXML
    private TableColumn<?, ?> idFechaIngreso;

    @FXML
    private TableColumn<?, ?> idNombre;

    @FXML
    private TableColumn<?, ?> idOficina;

    @FXML
    private TableColumn<?, ?> idSueldo;

    @FXML
    private TableColumn<?, ?> idTelefono;
    
    @FXML
    private TableColumn<?, ?> idUsuario;
    
    @FXML
    private TableColumn<?, ?> idTipo;

    @FXML
    private TextField nombreempleadoTF;

    @FXML
    private TextField oficinaBT;

    @FXML
    private ComboBox<String> opcbaja;

    @FXML
    private ComboBox<String> opcbusqueda;

    @FXML
    private TextField sueldoTF;

    @FXML
    private TableView<Empleado> tablaempleado;

    @FXML
    private TableView<Usuario> tablausuarios;

    @FXML
    private TextField tlfTF;
    
    @FXML
    private ComboBox<String> tipousuarioCB;
    
     
    @FXML
    private TextField buscarusuarioTF;
    
    @FXML
    private TextField usuarioselecionadoTF;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {  
        opccombobox = FXCollections.observableArrayList("Esta de Baja", "Esta Trabajando");
        opccombobusqueda = FXCollections.observableArrayList("DNI", "Apellidos", "Nombre", "Oficina", "Estado");
        opctipo = FXCollections.observableArrayList("cliente", "empleado");
        procesos = Procesos.getProcesosInstancia();
        data = procesos.obtenerempleados();
        datausuario =  procesos.obtenerusuarios();
        tablaempleado.setItems(data);
        tablausuarios.setItems(datausuario);
        opcbaja.setItems(opccombobox);
        opcbusqueda.setItems(opccombobusqueda);
        tipousuarioCB.setItems(opctipo);
        
        
        this.idDNI.setCellValueFactory(new PropertyValueFactory("DNI"));
        this.idApellidos.setCellValueFactory(new PropertyValueFactory("Apellidos"));
        this.idNombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
        this.idOficina.setCellValueFactory(new PropertyValueFactory("Oficina"));
        this.idTelefono.setCellValueFactory(new PropertyValueFactory("Telefono"));
        this.idCorreo.setCellValueFactory(new PropertyValueFactory("Correo"));
        this.idSueldo.setCellValueFactory(new PropertyValueFactory("Sueldo"));
        this.idDireccion.setCellValueFactory(new PropertyValueFactory("Direccion"));
        this.idFechaIngreso.setCellValueFactory(new PropertyValueFactory("FechaIngreso"));
        this.idEstado.setCellValueFactory(new PropertyValueFactory("Estado"));
        this.idUsuario.setCellValueFactory(new PropertyValueFactory("NombreUsuario"));
        this.idTipo.setCellValueFactory(new PropertyValueFactory("Tipo"));
    }
    
    @FXML
    void borrarempleado(ActionEvent event) {
        if(empleado == null){
            Alerta.mostrarAlertWarning("No hay ningun empleado selecionado");
        }else{
            procesos.borrarempleado(empleado.getDNI());
            data.remove(empleado);
            tablaempleado.refresh();
        }
    }

    @FXML
    void buscarempleado(ActionEvent event) {
        
        data = procesos.bucarempleado(buscarempleadoTF.getText(), opcbusqueda.getSelectionModel().getSelectedItem());
        tablaempleado.setItems(data);
    }

    @FXML
    void modificarempleado(ActionEvent event){       
        if(empleado == null){
            Alerta.mostrarAlertWarning("No hay ningun empleado selecionado");
        }else{
            if(empleado.getDNI().equals(dniempleadoTF.getText())){
                boolean encontrado = false;
                Empleado emple = null;
                int i = -1;
                while(i < data.size() && encontrado == false){
                    i++;
                    emple = data.get(i);
                    if(empleado.getDNI() == emple.getDNI()){
                        encontrado = true;
                    }
                }
                
                Empleado empleadomodificado = new Empleado(
                    dniempleadoTF.getText(), 
                    apellidosTF.getText(),
                    nombreempleadoTF.getText(),
                    oficinaBT.getText(), 
                    tlfTF.getText(), 
                    correoTF.getText(), 
                    sueldoTF.getText(), 
                    empleado.getFechaIngreso(),
                    direccionTF.getText(),
                    opcbaja.getSelectionModel().getSelectedItem().toString()
                );
                procesos.modificarempleado(empleadomodificado);
                empleado = empleadomodificado;
                data.set(i, empleadomodificado);
                tablaempleado.setItems(data);
                
            }else{
                Alerta.mostrarAlertWarning("No se puede modificar el DNI");
            }
        }
    }

    @FXML
    void newempleado(ActionEvent event) {
            DateFormat formato =  new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = new Date();
        String fechastr = formato.format(fecha);
        String[] datos = {
            dniempleadoTF.getText(), 
            apellidosTF.getText(),
            nombreempleadoTF.getText(),
            oficinaBT.getText(), 
            tlfTF.getText(), 
            correoTF.getText(), 
            sueldoTF.getText(), 
            fechastr,
            direccionTF.getText(),
            opcbaja.getSelectionModel().getSelectedItem().toString()
        };
        boolean vacio = false;
        for(int i = 0; i < datos.length; i++){
              if(datos[i] == null || datos[i].length() == 0){
                  vacio = true;
              }
        }
        
        if(vacio == false){
            empleado = procesos.crearempleado(datos);
            System.out.println(empleado.toString());
            if(!data.contains(empleado)){
                data.add(empleado);
                tablaempleado.setItems(data);
            }
        }else{
            Alerta.mostrarAlertWarning("Un campo esta vacio");
        }
    }
    
    @FXML
    void selected(MouseEvent event) {
        empleado = this.tablaempleado.getSelectionModel().getSelectedItem(); 
        if(empleado != null){
            dniempleadoTF.setText(empleado.getDNI());
            nombreempleadoTF.setText(empleado.getNombre());
            apellidosTF.setText(empleado.getApellidos());
            oficinaBT.setText(empleado.getOficina());
            tlfTF.setText(empleado.getTelefono());
            correoTF.setText(empleado.getCorreo());
            sueldoTF.setText(empleado.getSueldo());
            direccionTF.setText(empleado.getDireccion());
            opcbaja.setValue(empleado.getEstado());
        }
    }
    
    @FXML
    void selectedusuario(MouseEvent event) {
        usuario = this.tablausuarios.getSelectionModel().getSelectedItem(); 
        if(usuario != null){
            usuarioselecionadoTF.setText(usuario.getNombreUsuario());
            tipousuarioCB.setValue(usuario.getTipo());
        }
    }
    
    @FXML
    void buscarusuario(ActionEvent event) {
        datausuario = procesos.bucarusuario(buscarusuarioTF.getText());
        tablausuarios.setItems(datausuario);
    }

    @FXML
    void deleteusuarioBT(ActionEvent event) {
        if(usuario == null){
            Alerta.mostrarAlertWarning("No hay ningun usuario selecionado");
        }else{
            if(usuario.getNombreUsuario().equals("admin")){
                Alerta.mostrarAlertWarning("No se puede borrar la administrador");
            }else{
                procesos.borrarusuario(usuario.getNombreUsuario());
                datausuario.remove(usuario);
                tablausuarios.refresh();
            }
        }
    }
    
    @FXML
    void MnUser(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/MainUser.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Alerta.mostrarAlertWarning("Error al cambiar de Menu");
        }
    }
    
    @FXML
    public void modificarusuario(ActionEvent event) {
        if (usuario == null) {
            Alerta.mostrarAlertWarning("No hay ningun usuario selecionado");
        } else {
            if(!usuario.getNombreUsuario().equals("admin")){
                boolean encontrado = false;
                Usuario user = null;
                int i = 0;
                if(!usuario.getNombreUsuario().equals(usuarioselecionadoTF.getText())){
                    while (i < datausuario.size() && encontrado == false) {
                        user = datausuario.get(i);
                        i++;
                        if (usuarioselecionadoTF.getText().equals(user.getNombreUsuario())) {
                            encontrado = true;
                        }
                    }
                }

                if (!encontrado) {
                    encontrado = false;
                    Usuario usu = null;
                    i = -1;
                    while (i < datausuario.size() && encontrado == false) {
                        i++;
                        usu = datausuario.get(i);
                        if (usuario.getNombreUsuario() == usu.getNombreUsuario()) {
                            encontrado = true;
                        }
                    }

                    Usuario usuariomodificado = new Usuario(
                            usuarioselecionadoTF.getText(), 
                            tipousuarioCB.getValue().toString()
                    );
                    System.out.println(usuariomodificado.getNombreUsuario());
                    procesos.modificarusuario(usuariomodificado, usuario.getNombreUsuario(), tipousuarioCB.getValue().toString());
                    usuario = usuariomodificado;
                    datausuario.set(i, usuariomodificado);
                    tablausuarios.setItems(datausuario);
                } else {
                    Alerta.mostrarAlertWarning("Ya existe el usuario");
                }
            }else{
                Alerta.mostrarAlertWarning("No se puede modificar la administrador");
            }
        }
    }
    
    
    
    @FXML
    void Logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/VistaPrincipal.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene); 
            stage.show();
        } catch (IOException ex) {
            Alerta.mostrarAlertError("Error al cambiar menu");
        }
    }
}
