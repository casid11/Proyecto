/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.AlmacenHerramientas;
import modelo.Almacenes;
import modelo.Herramientas;
import modelo.Pedido;
import modelo.Pedidos;
import modelo.Procesos;
import vista.Alerta;

/**
 * FXML Controller class
 *
 * @author casid
 */
public class MainUserController implements Initializable {
    private Procesos procesos;
    private ObservableList<Pedidos> datapedidos;
    private ObservableList<Almacenes> dataalmacenes;
    private ObservableList<Pedido> datapedido;
    private ObservableList<Herramientas> dataherramienta;
    private ObservableList<String> opccombopedidos;
    private ObservableList<String> opccomboalmacen;
    private ObservableList<String> opcañadir;
    private ObservableList<Herramientas> dataherramientas;
    private ObservableList<AlmacenHerramientas> dataalmacenherramientas;
    private Pedidos pedidos;
    private Almacenes almacenes;
    private Herramientas herramientas;
    
    @FXML
    private Button AdminMenuBT;

    @FXML
    private Button AdminMenuBT1;

    @FXML
    private Button AdminMenuBT2;
    
    @FXML
    private TableColumn<?, ?> IdFechaR;

    @FXML
    private ComboBox<String> buscaralmacenCB;

    @FXML
    private TextField buscaralmacenTF;

    @FXML
    private ComboBox<String> buscarpedidoCB;

    @FXML
    private TextField buscarpedidoTF;

    @FXML
    private TableColumn<?, ?> idAlmacen;

    @FXML
    private TableColumn<?, ?> idCantidad;
    
    @FXML
    private TableColumn<?, ?> idCantidades;

    @FXML
    private TableColumn<?, ?> idCliente;

    @FXML
    private TableColumn<?, ?> idDistribuidora;

    @FXML
    private TableColumn<?, ?> idHeramienta;

    @FXML
    private TableColumn<?, ?> idHerramientas;

    @FXML
    private TableColumn<?, ?> idOficina;

    @FXML
    private TableColumn<?, ?> idPedido;

    @FXML
    private TableColumn<?, ?> ifFechaE;

    @FXML
    private TableView<Pedido> tablapedido;

    @FXML
    private TableView<Pedidos> tablapedidos;
    
    @FXML
    private TableView<Herramientas> tablaalmacen;
    
    @FXML
    private TableView<Almacenes> tablaalmacenes;
    
    @FXML
    private DatePicker datepicker;
    
    @FXML
    private TextField CantidadTF;
    
    @FXML
    private ComboBox<String> AñadirCB;
    
    @FXML
    private TableColumn<?, ?> idHerramientaalmacenes;
    
    @FXML
    private TableColumn<?, ?> idCantidadH;

    @FXML
    private TableColumn<?, ?> idCantidadHs;
    
     @FXML
    private TableColumn<?, ?> idAlmacenes;
    
    @FXML
    private TextField buscarherramientaTF;
    
    @FXML
    private TableView<Herramientas> tablaherramientas;
    
    @FXML
    private TableView<AlmacenHerramientas> tablaherramienta;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        procesos = Procesos.getProcesosInstancia();
        if(!procesos.getuser().equals("admin")){
            AdminMenuBT.setVisible(false);
            AdminMenuBT1.setVisible(false);
            AdminMenuBT2.setVisible(false);
        }
        
        opccombopedidos = FXCollections.observableArrayList("Cliente", "Fecha realizado", "Fecha entregado", "Distribuidora");
        opccomboalmacen = FXCollections.observableArrayList("Almacen", "Oficina");
        opcañadir = FXCollections.observableArrayList("-250", "-100", "-50", "-25", "-10", "-5", "-1", "0" , "+1", "+5", "+10", "+25", "+50", "+100", "+250");
        datapedidos = procesos.obtenerpedidos();
        dataalmacenes =  procesos.obteneralmacenes();
        tablapedidos.setItems(datapedidos);
        tablaalmacenes.setItems(dataalmacenes);
        buscarpedidoCB.setItems(opccombopedidos);
        buscaralmacenCB.setItems(opccomboalmacen);
        AñadirCB.setItems(opcañadir);
        AñadirCB.setValue("0");
        dataherramientas = procesos.obtenerherramientas();
        tablaherramientas.setItems(dataherramientas);
        
        
        this.idPedido.setCellValueFactory(new PropertyValueFactory("Npedido"));
        this.idCliente.setCellValueFactory(new PropertyValueFactory("Cliente"));
        this.IdFechaR.setCellValueFactory(new PropertyValueFactory("FechaR"));
        this.ifFechaE.setCellValueFactory(new PropertyValueFactory("FechaE"));
        this.idDistribuidora.setCellValueFactory(new PropertyValueFactory("Distribuidora"));
        this.idAlmacen.setCellValueFactory(new PropertyValueFactory("NombreAlmacen"));
        this.idOficina.setCellValueFactory(new PropertyValueFactory("NombreOficina"));
        this.idHeramienta.setCellValueFactory(new PropertyValueFactory("Herramienta"));
        this.idCantidad.setCellValueFactory(new PropertyValueFactory("Cantidad"));
        this.idHerramientas.setCellValueFactory(new PropertyValueFactory("Herramienta"));
        this.idCantidades.setCellValueFactory(new PropertyValueFactory("Cantidades"));
        this.idHerramientaalmacenes.setCellValueFactory(new PropertyValueFactory("Herramienta"));
        this.idCantidadHs.setCellValueFactory(new PropertyValueFactory("Cantidades"));
        this.idAlmacenes.setCellValueFactory(new PropertyValueFactory("Almacen"));
        this.idCantidadH.setCellValueFactory(new PropertyValueFactory("Cantidades"));
    }    
    
    @FXML
    void AdminMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Alerta.mostrarAlertWarning("Error al cambiar de Menu");
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
    
    @FXML
    void selectedpedido(MouseEvent event) {
        pedidos = this.tablapedidos.getSelectionModel().getSelectedItem(); 
        if(pedidos != null){
            datapedido = procesos.obtenerpedido(pedidos.getNpedido());
            tablapedido.setItems(datapedido);
        }
    }
    
    @FXML
    void selectedalmacen(MouseEvent event) {
        almacenes = this.tablaalmacenes.getSelectionModel().getSelectedItem(); 
        if(almacenes != null){
            dataherramienta = procesos.obtenerherramienta(almacenes.getNombreAlmacen());
            tablaalmacen.setItems(dataherramienta);
        }
    }

    @FXML
    void buscaralmacen(ActionEvent event) {
        dataalmacenes = procesos.buscaralmacenes(buscaralmacenCB.getSelectionModel().getSelectedItem(), buscaralmacenTF.getText());
        tablaalmacenes.setItems(dataalmacenes);
    }

    @FXML
    void buscarpedido(ActionEvent event) {
        String opc = buscarpedidoCB.getSelectionModel().getSelectedItem();
        String fechastr = null;
        if(opc.equals("Fecha realizado") || opc.equals("Fecha entregado")){
            if(datepicker.getValue() != null){
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
                fechastr = dateFormatter.format(datepicker.getValue());
                System.out.println(fechastr);
                datapedidos = procesos.bucarpedido(opc, fechastr, buscarpedidoTF.getText());
                tablapedidos.setItems(datapedidos);
            }else{
                Alerta.mostrarAlertWarning("Selecione la fecha");
            }     
        }else{
            datapedidos = procesos.bucarpedido(opc, fechastr, buscarpedidoTF.getText());
            tablapedidos.setItems(datapedidos);
        }
    }
    
    @FXML
    void selectedherramienta(MouseEvent event) {
        herramientas = this.tablaalmacen.getSelectionModel().getSelectedItem(); 
        if(herramientas != null){
            CantidadTF.setText(herramientas.getCantidades());
        }
    }
    
    @FXML
    void modificarcantidad(ActionEvent event) {
        if(herramientas != null){
            Integer TFcantidad = Integer.parseInt(CantidadTF.getText());
            Integer suma = Integer.parseInt(AñadirCB.getValue());
            TFcantidad = TFcantidad + suma;
            if(TFcantidad < 0){
                Alerta.mostrarAlertWarning("No se pudo realizar la acción. Resultado menor de 0");
            }else{
                try {
                    procesos.modificarcantidad(TFcantidad.toString(), herramientas.getHerramienta(), almacenes.getNombreAlmacen());
                    herramientas.setCantidades(TFcantidad.toString());
                    this.tablaalmacen.getSelectionModel().select(herramientas);
                    tablaalmacen.refresh();
                    CantidadTF.setText(herramientas.getCantidades());
                } catch (SQLException ex) {
                    Alerta.mostrarAlertWarning("Error al interactuar con la base de datos");
                }
            }
        }
    }
    
    @FXML
    void buscarherramienta(ActionEvent event) {
        dataherramientas = procesos.bucarherramienta(buscarherramientaTF.getText());
        tablaherramientas.setItems(dataherramientas);
    }
    
    @FXML
    void selectedherramientaalmacen(MouseEvent event) {
        herramientas = this.tablaherramientas.getSelectionModel().getSelectedItem(); 
        if(herramientas != null){
            dataalmacenherramientas = procesos.obteneralmacenesherramientas(herramientas.getHerramienta());
            tablaherramienta.setItems(dataalmacenherramientas);
        }
    }
    
    @FXML
    void pedidoentregado(ActionEvent event) {
        if(pedidos.getFechaE().equals("0000-00-00")){
            procesos.pedidoentregado(pedidos.getNpedido());
            boolean encontrado = false;
            int i = 0;
            while(i < datapedidos.size() && encontrado == false){
                if(datapedidos.get(i).getNpedido().equals(pedidos.getNpedido())){
                    encontrado = true;
                }else{
                    i++;
                }
            }
            DateFormat formato =  new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = new Date();
            String fechastr = formato.format(fecha);
            pedidos.setFechaE(fechastr);
            datapedidos.set(i, pedidos);
            tablapedidos.setItems(datapedidos);
        }
    }
}
