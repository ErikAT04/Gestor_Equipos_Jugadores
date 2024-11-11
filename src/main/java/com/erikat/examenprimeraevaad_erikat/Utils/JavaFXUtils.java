package com.erikat.examenprimeraevaad_erikat.Utils;

import com.erikat.examenprimeraevaad_erikat.Scenes.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class JavaFXUtils {
    public static Optional<ButtonType> makeAlert(Alert.AlertType alertType, String mensaje, String titulo){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.setTitle(titulo);
        return alert.showAndWait();
    } //Devuelve la respuesta del botón que se pulse del Alert (Si es que se pulsa)

    public static Controller openInNewStage(String titulo, String ruta){ //Función que introduce un nuevo stage con una escena
        Controller controller = null;
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(R.getUIResource(ruta));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            controller = loader.getController();
        }catch (Exception e){
            System.out.println("Error de Apertura");
        }
        return controller; //Devuelve cualquier controller que se cargue
    }

    public static Controller openInThisStage(Stage stage, String titulo, String ruta){ //Función que cambia de escena
        Controller controller = null;
        try{
            FXMLLoader loader = new FXMLLoader(R.getUIResource(ruta));
            Scene scene = new Scene(loader.load());
            stage.setTitle(titulo);
            stage.setScene(scene);
            controller = loader.getController();
            if (!stage.isShowing()){
                stage.show();
            }
        }catch (Exception e){
            System.out.println("Error de Apertura: " + e.getMessage());
        }
        return controller; //Devuelve cualquier controller que se cargue
    }
}
