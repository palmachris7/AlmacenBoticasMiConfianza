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
import javafx.css.Size;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metodos.Acciones;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;

import static Conection.ConexionBD.getConnection;

public class ProductosController implements Initializable {


    Acciones acciones = new Acciones();
    ConexionBD conexionBD = new ConexionBD();

    /// cambio
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


        showProducts();


        try {
            showProductDetails(null);
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        // Listen for selection changes and show the person details when changed.
        tblViewProductos.getSelectionModel().selectedItemProperty().addListener(
              (observable, oldValue, newValue) -> {
                  try {
                      showProductDetails(newValue);
                  } catch (FileNotFoundException | SQLException e) {
                      e.printStackTrace();
                  }
              });




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
        ObservableList<Producto> productsList = getProductsList();
        SortedList<Producto> sortedData = new SortedList<>(productsList);
        tblViewProductos.setItems(sortedData);
    }







    @FXML
    void eliminar(MouseEvent event) throws SQLException {
        Producto producto = tblViewProductos.getSelectionModel().getSelectedItem();
        if (producto != null) {

            String query = "Delete from productos where id_producto="+producto.getId_producto();
            ConexionBD.executeQuery(query);
            actualizar();
            showProducts();
        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Producto no seleccionado");
            alert.setContentText("Por favor seleccione un producto de la tabla");

            alert.showAndWait();
        }





    }






    public static boolean showPersonEditDialog(Producto producto) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ProductosController.class.getResource("/fxml/FrmEditMedicamentos.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Acciones acciones= new Acciones();
            acciones.Mover(scene,dialogStage);
            //Set the person into the controller.
            FrmEditarMedicamentos controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProducto(producto);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean showPersonGuardarDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ProductosController.class.getResource("/fxml/FrmMedicamentos.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Acciones acciones= new Acciones();
            acciones.Mover(scene,dialogStage);
            //Set the person into the controller.
            FrmMedicamentosController controller = loader.getController();
            controller.setDialogStage(dialogStage);
           // controller.setProducto(producto);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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


    private void showProductDetails(Producto producto) throws FileNotFoundException, SQLException {
        if (producto != null) {

            infoProducto.setText(producto.getInfo_producto());

            try {
                Blob img = producto.getImage();
                InputStream inputStream= img.getBinaryStream();
                Image image = new Image(inputStream);
                imagen.setImage(image);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        } else {

            infoProducto.setText("");
            imagen.setImage(null);

        }
    }





    ///Tabla////
    public ObservableList<Producto> getProductsList() {
        ObservableList<Producto> productsList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query =   "Select P.id_producto, " +
                "P.nombre_producto, " +
                "P.tipo_producto," +
                "P.presentacion_producto, " +
                "P.lote_producto, "+
                "P.fecha_vencimiento, "+
                "P.info_producto, " +
                "P.estado_producto, "+
                "P.precio_unid, " +
                "P.precio_caja, " +
                "P.imagen, "+
                "L.id_laboratorio, "+
                "L.nombre_laboratorio " +
                "From productos P " +
                "Inner join laboratorio L " +
                "On(P.fk_laboratorio = L.id_laboratorio)" ;
        Statement st;
        ResultSet rs;

        try {
            assert connection != null;
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Producto producto;
            while(rs.next()) {
                producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getString("tipo_producto"),
                        rs.getString("presentacion_producto"),
                        rs.getInt("lote_producto"),
                        rs.getDate("fecha_vencimiento"),
                        rs.getString("info_producto"),
                        rs.getString("estado_producto"),
                        rs.getDouble("precio_unid"),
                        rs.getDouble("precio_caja"),
                        new Laboratorio(rs.getInt("id_laboratorio"),
                                rs.getString("nombre_laboratorio")
                        ),
                       rs.getBlob("imagen")
                );

                productsList.add(producto);






            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productsList;
    }




    public void showProducts() {
        ObservableList<Producto> productsList = getProductsList();
        //clmId.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("id_producto"));
        clmProductoNombre.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre_producto"));
        clmTipo.setCellValueFactory(new PropertyValueFactory<Producto,String>("tipo_producto"));
        clmPresentacion.setCellValueFactory(new PropertyValueFactory<Producto,String>("presentacion_producto"));
        clmLote.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("lote_producto"));
        clmFecha_vencimiento.setCellValueFactory(new PropertyValueFactory<Producto,Date>("fecha_vencimiento"));
        //clmInfo_producto.setCellValueFactory(new PropertyValueFactory<Producto,String>("info_producto"));
        clmEstado_producto.setCellValueFactory(new PropertyValueFactory<Producto,String>("estado_producto"));
        clmPrecio_unid.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precio_unid"));
        clmPrecio_caja.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precio_caja"));
        clmLaboratorio.setCellValueFactory(new PropertyValueFactory<Producto,Laboratorio>("laboratorio"));


        FilteredList<Producto> filteredData = new FilteredList<>(productsList, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(producto -> {



                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (producto.getNombre_producto().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else if (producto.getTipo_producto().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (producto.getInfo_producto().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                else if(producto.getLaboratorio().getNombre_laboratorio().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }

                else
                    return false;
            });
        });


        SortedList<Producto> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tblViewProductos.comparatorProperty());

        tblViewProductos.setItems(sortedData);

    }








}