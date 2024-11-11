package com.erikat.examenprimeraevaad_erikat;

import com.erikat.examenprimeraevaad_erikat.Utils.JavaFXUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        JavaFXUtils.openInNewStage("Equipos", "EquipoView.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}