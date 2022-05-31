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
import javafx.stage.Stage;
import modelo.ProcesosLogin;
import vista.Alerta;

/**
 * FXML Controller class
 *
 * @author casid
 */
public class VistaPrincipalController implements Initializable {
    private ProcesosLogin procesos;
    
  
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
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        procesos = ProcesosLogin.getProcesosInstancia();
    }
    
    @FXML
    void backtologin(ActionEvent event) {
        LoginBT.setVisible(true);
        movetoregisterBT.setVisible(true);
        registerBT1.setVisible(false);
        backtologinBT.setVisible(false);
        confirmpasswordTF.setVisible(false);
        cofirmpasswordLB.setVisible(false);
    }

    @FXML
    void login(ActionEvent event) {
        try {
            
            if (procesos.login(nombreTF.getText(), passwordTF.getText())) {
                Parent root = FXMLLoader.load(getClass().getResource("/vista/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            Alerta.mostrarAlertError("Error al cambiar menu");
        }
    }

    @FXML
    void movetoregister(ActionEvent event) {
        LoginBT.setVisible(false);
        movetoregisterBT.setVisible(false);
        registerBT1.setVisible(true);
        backtologinBT.setVisible(true);
        confirmpasswordTF.setVisible(true);
        cofirmpasswordLB.setVisible(true);
    }
    @FXML
    void register(ActionEvent event) {
        try {  
            
            if (procesos.registro(nombreTF.getText(), passwordTF.getText(), confirmpasswordTF.getText())) {
                
                Parent root = FXMLLoader.load(getClass().getResource("/vista/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            
            }
        } catch (IOException e) {
            Alerta.mostrarAlertError("Error al cambiar menu");
        }
    }
}

