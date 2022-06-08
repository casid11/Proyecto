/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Procesos;
import vista.Alerta;

/**
 * FXML Controller class
 *
 * @author casid
 */
public class VistaPrincipalController implements Initializable {
    private Procesos procesos;
    
  
     @FXML
    private Button LoginBT;

    @FXML
    private Button backtologinBT;

    @FXML
    private Label cofirmpasswordLB;

    @FXML
    private TextField confirmpasswordTF;

    @FXML
    private Button movetoregisterBT;

    @FXML
    private Label nombreLB;

    @FXML
    private TextField nombreTF;

    @FXML
    private Label passwordLB;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button registerBT1;
    
    @FXML
    private CheckBox empleadocheck;
    
    @FXML
    private TextField DNITF;
    
    @FXML
    private Label DNILB;
    
    @FXML
    private Label apellidosLB;

    @FXML
    private TextField apellidosTF;
    
    @FXML
    private Label nameLB;

    @FXML
    private TextField nameTF;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        procesos = Procesos.getProcesosInstancia();
        
        
    }
    
    @FXML
    void backtologin(ActionEvent event) {
        LoginBT.setVisible(true);
        movetoregisterBT.setVisible(true);
        registerBT1.setVisible(false);
        empleadocheck.setVisible(false);
        backtologinBT.setVisible(false);
        confirmpasswordTF.setVisible(false);
        cofirmpasswordLB.setVisible(false);
        DNITF.setVisible(false);
        DNILB.setVisible(false);
        apellidosLB.setVisible(false);
        apellidosTF.setVisible(false);
        nameLB.setVisible(false);
        nameTF.setVisible(false);
    }
    
    @FXML
    void login(ActionEvent event) {
        try {
            if (procesos.login(nombreTF.getText(), passwordTF.getText())) {
                if (nombreTF.getText().equals("admin")) {
                    Parent root = FXMLLoader.load(getClass().getResource("/vista/MainMenu.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    if(procesos.checkempleado(nombreTF.getText())){
                        Parent root = FXMLLoader.load(getClass().getResource("/vista/MainUser.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }else{
                        Parent root = FXMLLoader.load(getClass().getResource("/vista/MainCliente.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }                    
                }
            }
        } catch (IOException e) {
            //Alerta.mostrarAlertError("Error al cambiar menu");
            e.printStackTrace();
        }
    }

    @FXML
    void movetoregister(ActionEvent event) {
        LoginBT.setVisible(false);
        movetoregisterBT.setVisible(false);
        registerBT1.setVisible(true);
        empleadocheck.setVisible(true);
        DNITF.setVisible(true);
        DNILB.setVisible(true);
        backtologinBT.setVisible(true);
        confirmpasswordTF.setVisible(true);
        cofirmpasswordLB.setVisible(true);
        apellidosLB.setVisible(true);
        apellidosTF.setVisible(true);
        nameLB.setVisible(true);
        nameTF.setVisible(true);
        empleadocheck.setSelected(false);
    }
    
    @FXML
    void register(ActionEvent event) {
        try {
            if(empleadocheck.selectedProperty().getValue()){
                if(procesos.registro(nombreTF.getText(), passwordTF.getText(), confirmpasswordTF.getText(), "empleado" , nameTF.getText(), apellidosTF.getText(), DNITF.getText())) {

                    Parent root = FXMLLoader.load(getClass().getResource("/vista/MainUser.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();                    
                }
            }else{
                if(procesos.registro(nombreTF.getText(), passwordTF.getText(), confirmpasswordTF.getText(), "cliente", nameTF.getText(), apellidosTF.getText(), DNITF.getText())){
                Parent root = FXMLLoader.load(getClass().getResource("/vista/MainCliente.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();    
                }
            }
        } catch (IOException e) {
            Alerta.mostrarAlertError("Error al cambiar menu");
        }
    }
    
    
    @FXML
    void checkclick(MouseEvent event) {
        if(DNITF.isVisible()){
            DNITF.setVisible(false);
            DNILB.setVisible(false);
            apellidosLB.setVisible(false);
            apellidosTF.setVisible(false);
            nameLB.setVisible(false);
            nameTF.setVisible(false);
        }else{
            DNITF.setVisible(true);
            DNILB.setVisible(true);
            apellidosLB.setVisible(true);
            apellidosTF.setVisible(true);
            nameLB.setVisible(true);
            nameTF.setVisible(true);
        }
    }
}

