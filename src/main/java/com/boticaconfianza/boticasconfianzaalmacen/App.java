package com.boticaconfianza.boticasconfianzaalmacen;

import metodos.Acciones;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.StageStyle;


public class App extends Application {

    Acciones acciones = new Acciones();
    @Override
    public void start(Stage stage) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
         stage.setMaximized(false);

        Scene scene = new Scene(root, 343, 435);
        acciones.Mover(scene,stage);
        stage.setScene(scene);
        stage.show();
        
    }


    public static void main(String[] args) {
        launch();
    }

}