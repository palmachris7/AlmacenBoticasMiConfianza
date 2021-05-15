package controllers;

import Conection.ConexionBD;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entidades.Laboratorio;
import entidades.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metodos.Acciones;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class InventarioController implements Initializable {


    Acciones acciones = new Acciones();
    ConexionBD conexionBD = new ConexionBD();


    @FXML
    private AnchorPane myPane;



    @FXML
    private AnchorPane root_pane;
    @FXML
    private AnchorPane panel_home;

    @FXML
    private JFXTextField filterField;

    @FXML
    private ImageView imagen;

    @FXML
    private JFXTextArea infoProducto;
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


    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnEditar;

    @FXML
    public TableColumn<Producto,Integer>clmId;
    @FXML
    public TableColumn<Producto, String>clmProductoNombre;
    @FXML
    public TableColumn<Producto, String>clmTipo;
    @FXML
    public TableColumn<Producto, String>clmPresentacion;
    @FXML
    public TableColumn<Producto, Integer>clmLote;
    @FXML
    public TableColumn<Producto, Date>clmFecha_vencimiento;
    @FXML
    public TableColumn<Producto, String>clmInfo_producto;
    @FXML
    public TableColumn<Producto, String>clmEstado_producto;
    @FXML
    public TableColumn<Producto, Double>clmPrecio_unid;
    @FXML
    public TableColumn<Producto, Double>clmPrecio_caja;
    @FXML
    public TableColumn<Producto, Laboratorio> clmLaboratorio;




    @FXML
    private TableView<Producto>tblViewProductos;
    private final ObservableList<Producto> dataList = FXCollections.observableArrayList();




    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }



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

        acciones.NuevaVentana(event,"Main");


    }

    @FXML
    void productos(MouseEvent event) throws IOException {

        acciones.NuevaVentana(event,"Productos3");
        showProducts();

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








    public void actualizar(){

    }






    @FXML
    void eliminar(MouseEvent event) throws SQLException {






    }










    public static Stage getPrimaryStage() {
        return new Stage();
    }
    @FXML
    void editar(MouseEvent event) throws IOException {

        Producto selectedPerson = tblViewProductos.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            boolean okClicked = ProductosController.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                actualizar();
                showProducts();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            // alert.initOwner(ProductosController.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("Seleccione producto");
            alert.setContentText("Por favor  selecciones un producto de la tabla.");

            alert.showAndWait();
            //
        }

    }

    @FXML
    void nuevo(MouseEvent event) {
        boolean okClicked = ProductosController.showPersonGuardarDialog();
        if (okClicked) {
            actualizar();
            showProducts();
        }





    }


    private void showProductDetails(Producto producto) {
        if (producto != null) {

            infoProducto.setText(producto.getInfo_producto());




        } else {

            infoProducto.setText("");

        }
    }




    ///Tabla////
    public void getProductsList() {

    }




    public void showProducts() {


    }








}