/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package controlador;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author casid
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            
             Parent root = FXMLLoader.load(getClass().getResource("/vista/VistaPrincipal.fxml"));
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/vista/Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch(IOException e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
    
}
