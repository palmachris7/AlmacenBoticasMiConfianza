/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import metodos.Acciones;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author palmachris7
 */
public class MainController implements Initializable {
    Acciones acciones = new Acciones();


    @FXML
    private AnchorPane myPane;


    @FXML
    private AnchorPane root_pane;
    @FXML
    private AnchorPane panel_home;

    @FXML
    private AnchorPane panel_productos;

    @FXML
    private AnchorPane panel_inventario;

    @FXML
    private AnchorPane panel_reportes;

    @FXML
    private AnchorPane panel_mantenimiento;

    @FXML
    private JFXButton btniMinimize;

    @FXML
    private JFXButton btnMaximizar;

    @FXML
    private JFXButton btnCerrar;

    @FXML
    private JFXButton btnPrincipal;

    @FXML
    private JFXButton btnProductos;

    @FXML
    private JFXButton btnInventario;

    @FXML
    private JFXButton btnReportes;

    @FXML
    private JFXButton btnMantenimiento;



//Menus de ventana
    @FXML
    void cerrar(MouseEvent event) {
        System.exit(0);
    }
    
    Stage stage = null;
    @FXML
    void maximizar(MouseEvent event) {
       stage = (Stage) myPane.getScene().getWindow();
       if(stage.isMaximized())
           stage.setMaximized(false);
       else
           stage.setMaximized(true);
    }

    @FXML
    void minimizar(MouseEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }
    @FXML
    void salir(MouseEvent event) throws IOException {
        acciones.cerrarsesion(event);

    }
    



    ///Menus
    @FXML
    void principal(MouseEvent event) throws IOException {

    }

    @FXML
    void productos(MouseEvent event) throws IOException {

         acciones.NuevaVentana(event,"Productos3");
    }

    @FXML
    void inventario(MouseEvent event) throws IOException {
        acciones.NuevaVentana(event,"Inventario");

    }

    @FXML
    void reportes(MouseEvent event) {

    }
    @FXML
    void mantenimiento(MouseEvent event) {

   }

    @FXML
    void laboratorio(MouseEvent event) {
        acciones.NuevaventanaModal("laboratorio");
    }














    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}

