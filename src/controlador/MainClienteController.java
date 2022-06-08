/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Herramientas;
import modelo.Procesos;
import vista.Alerta;


/**
 * FXML Controller class
 *
 * @author casid
 */
public class MainClienteController implements Initializable {
    private Procesos procesos;
    private ObservableList<Herramientas> datastock;
    private ObservableList<Herramientas> datacesta = FXCollections.observableArrayList();
    private Herramientas herramientastock;
    private Herramientas herramientacesta;
    
    @FXML
    private Label cantidadseleccionadaLB;

    @FXML
    private Label cantidadseleccionadacestaLB;

    @FXML
    private TableColumn<?, ?> idCantidad;

    @FXML
    private TableColumn<?, ?> idNombre;

    @FXML
    private TableColumn<?, ?> idStock;
    
    @FXML
    private TableColumn<?, ?> idcantidadstock;

    @FXML
    private Label selecionadoLB;

    @FXML
    private Label selecionadocestaLB;

    @FXML
    private TableView<Herramientas> tablacesta;

    @FXML
    private TableView<Herramientas> tablastock;

    @FXML
    private Label usuarioLB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        procesos = Procesos.getProcesosInstancia();
        datastock = procesos.obtenerherramientasstock();
        tablastock.setItems(datastock);
        try {
            usuarioLB.setText(procesos.getcliente());
        } catch (SQLException ex) {
            Alerta.mostrarAlertWarning("Error al interactuar con la base de datos");
        }
        
        this.idStock.setCellValueFactory(new PropertyValueFactory("Herramienta"));
        this.idcantidadstock.setCellValueFactory(new PropertyValueFactory("Cantidades"));
        this.idCantidad.setCellValueFactory(new PropertyValueFactory("Cantidades"));
        this.idNombre.setCellValueFactory(new PropertyValueFactory("Herramienta"));
        
    } 
    
    @FXML
    void selectedcesta(MouseEvent event) {
        herramientacesta = this.tablacesta.getSelectionModel().getSelectedItem(); 
        selecionadocestaLB.setText(herramientacesta.getHerramienta());
        cantidadseleccionadacestaLB.setText(herramientacesta.getCantidades());
    }

    @FXML
    void selectedstock(MouseEvent event) {
        herramientastock = this.tablastock.getSelectionModel().getSelectedItem(); 
        selecionadoLB.setText(herramientastock.getHerramienta());
        cantidadseleccionadaLB.setText("0");
    }
    
    @FXML
    void Añadir(ActionEvent event) {
        if (!cantidadseleccionadaLB.getText().equals("0") && !cantidadseleccionadaLB.getText().equals("")) {
            int i = 0;
            boolean encontrado = false;
            if (!datacesta.isEmpty()) {
                while (i < datacesta.size() && encontrado == false) {
                    if (datacesta.get(i).getHerramienta().equals(herramientastock.getHerramienta())) {
                        encontrado = true;
                    }
                    i++;
                }
            }
            i--;
            if (encontrado == false) {
                herramientacesta = new Herramientas(herramientastock.getHerramienta(), cantidadseleccionadaLB.getText());
                datacesta.add(herramientacesta);
                tablacesta.setItems(datacesta);
            } else {
                Integer suma = (Integer.parseInt(cantidadseleccionadaLB.getText()) + Integer.parseInt(datacesta.get(i).getCantidades()));
                if(suma > Integer.parseInt(herramientastock.getCantidades())){
                    suma = Integer.parseInt(herramientastock.getCantidades());
                }
                herramientacesta = new Herramientas(herramientastock.getHerramienta(), suma.toString());
                datacesta.set(i, herramientacesta);
                tablacesta.setItems(datacesta);
                
            }
        }
    }   

    @FXML
    void Modificar(ActionEvent event) {
        herramientacesta.setCantidades(cantidadseleccionadacestaLB.getText());
        int i = 0;
        boolean encontrado = false;
        if (!datacesta.isEmpty()) {
            while (i < datacesta.size() && encontrado == false) {
                if (datacesta.get(i).getHerramienta().equals(herramientastock.getHerramienta())) {
                    encontrado = true;
                }
                i++;
            }
        }
        i--;
        datacesta.set(i, herramientacesta);
        tablacesta.setItems(datacesta);
    }

    @FXML
    void RealizarPedido(ActionEvent event) {
        if (!datacesta.isEmpty()) {
            boolean realizado = true;
            int i = 0;
            while (i < datacesta.size() && realizado) {
                if (procesos.comprar(datacesta.get(i).getHerramienta(), Integer.parseInt(datacesta.get(i).getCantidades()))) {
                    i++;
                } else {
                    realizado = false;
                    Alerta.mostrarAlertWarning("Error al realizar la compra");
                }
            }
            Parent root;
            try {
                procesos.añadirpedido(datacesta);
                
                root = FXMLLoader.load(getClass().getResource("/vista/MainCliente.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainClienteController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Alerta.mostrarAlertWarning("Error al interactuar con la base de datos");
            }
        }
    }

    @FXML
    void resta(ActionEvent event) {
        if(herramientastock != null){
            Integer resta = Integer.parseInt(cantidadseleccionadaLB.getText()) - 1;
            if(resta >= 0){
                cantidadseleccionadaLB.setText(resta.toString());
            }
        }
    }

    @FXML
    void restacesta(ActionEvent event) {
        if(herramientacesta != null){
            Integer resta = Integer.parseInt(cantidadseleccionadacestaLB.getText()) - 1;
            if(resta >= 0){
                cantidadseleccionadacestaLB.setText(resta.toString());
            }
        }
    }

    @FXML
    void sumar(ActionEvent event) {
        if(herramientastock != null){
            Integer suma = Integer.parseInt(cantidadseleccionadaLB.getText()) + 1;
            if(suma <= Integer.parseInt(herramientastock.getCantidades())){
                cantidadseleccionadaLB.setText(suma.toString());
            }
        }
    }
    
    @FXML
    void sumarcesta(ActionEvent event) {
        if(herramientacesta != null){
            Integer suma = Integer.parseInt(cantidadseleccionadacestaLB.getText()) + 1;
            if(suma <= Integer.parseInt(herramientastock.getCantidades())){
                cantidadseleccionadacestaLB.setText(suma.toString());
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
