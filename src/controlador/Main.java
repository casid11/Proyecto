/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import vista.Alerta;

/**
 *
 * @author casid
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("mysql\\mysql_start.bat");
            processBuilder.start();
            primaryStage.setOnCloseRequest(evt -> {
                // prevent window from closing
                evt.consume();
                // execute own shutdown procedure
                Alert alert = new Alert(Alert.AlertType.NONE, "Really close the stage?", ButtonType.YES, ButtonType.NO);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                    try {
                        // you may need to close other windows or replace this with Platform.exit();
                        ProcessBuilder processBuilderstop = new ProcessBuilder("mysql\\mysql_stop.bat");
                        processBuilderstop.start();
                        primaryStage.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            Parent root = FXMLLoader.load(getClass().getResource("/vista/VistaPrincipal.fxml"));
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/vista/Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch(IOException e) {
            Alerta.mostrarAlertWarning("Error al iniciar la base de datos");
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
    
}
