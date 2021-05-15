package controllers;

import Conection.ConexionBD;
import com.jfoenix.controls.*;
import entidades.Producto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import metodos.Acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class FrmEditarMedicamentos implements Initializable {

   Acciones acciones = new Acciones();



    @FXML
    private JFXTextField nombreProducto;

    @FXML
    private JFXTextField loteProducto;

    @FXML
    private JFXTextField precioUnidad;

    @FXML
    private JFXTextField precioCaja;

    @FXML
    private JFXComboBox<String> laboratorioProducto;

    @FXML
    private JFXComboBox<String> presentacionProducto;

    @FXML
    private JFXComboBox<String> tipoProducto;

    @FXML
    private JFXTextArea infoProducto;


    @FXML
    private JFXRadioButton rdbtnActivo;


    @FXML
    private JFXRadioButton rdbtnInacnt;

    @FXML
    private ToggleGroup Estado;

    @FXML
    private DatePicker datePicker;

    @FXML
    private JFXButton btnAgregar;

    @FXML
    private JFXButton btnCancelar;
    @FXML
    private ImageView imageview;

    @FXML
    private JFXButton btnSubir;
    private FileChooser fileChooser;
    private File file;
    private Image image;
    private FileInputStream fis;

    private Stage dialogStage;
    private Producto producto;
    private boolean okClicked = false;




    @FXML
    void Cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();

    }



    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {


            producto.setNombre_producto(nombreProducto.getText());

            producto.setLote_producto(Integer.parseInt(loteProducto.getText()));


            okClicked = true;
            dialogStage.close();
        }
    }


    @FXML
    void agregar(MouseEvent event) throws IOException {
        RadioButton selectedRadioButton = (RadioButton) Estado.getSelectedToggle();
        fis=new FileInputStream(file);
        Connection con = ConexionBD.getConnection();
        String query = ("UPDATE productos SET nombre_producto=?,presentacion_producto=?,lote_producto=?,fecha_vencimiento=?,info_producto=?,estado_producto=?,precio_unid=?,precio_caja=?,tipo_producto=?,fk_laboratorio=?,imagen=?  WHERE id_producto=?");
        try {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(12, producto.getId_producto());
                ps.setString(1,nombreProducto.getText().toString());
                ps.setString(2, presentacionProducto.getValue().toString());
                ps.setInt(3, Integer.parseInt(loteProducto.getText()));
                ps.setString(4, String.valueOf(datePicker.getValue()));
                ps.setString(5, infoProducto.getText());
                ps.setString(6, selectedRadioButton.getText());
                ps.setDouble(7, Double.parseDouble(precioUnidad.getText()));
                ps.setDouble(8, Double.parseDouble(precioCaja.getText()));
                ps.setString(9, tipoProducto.getValue().toString());
                ps.setInt(10, 1);
             // Imagen
                ps.setBinaryStream(11, (InputStream)fis,(int)file.length());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        acciones.NuevaVentana(event, "Productos");

        stage.close();
        okClicked = true;


    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nombreProducto.getText() == null || nombreProducto.getText().length() == 0) {
            errorMessage += "No valido\n";
        }
        if (loteProducto.getText() == null || loteProducto.getText().length() == 0) {
            errorMessage += "No valido!\n";
        }
        if (precioUnidad.getText() == null || precioUnidad.getText().length() == 0) {
            errorMessage += "No valido!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        presentacionProducto.getItems().add("Tableta");
        presentacionProducto.getItems().add("Jarábe");
        presentacionProducto.getItems().add("Cápsula");
        presentacionProducto.getItems().add("Grajea");


        tipoProducto.getItems().add("Genérico");
        tipoProducto.getItems().add("Marca");

        laboratorioProducto.getItems().add("Bayer");
        laboratorioProducto.getItems().add("GardenHouse");



    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void subir(MouseEvent mouseEvent) {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images Files","*.jpg","*.png","*.jpeg")
        );
        Stage stage = (Stage) btnSubir.getScene().getWindow();
        file=fileChooser.showOpenDialog(stage);
        if(file!=null){
            image=new Image(file.toURI().toString());
            imageview.setImage(image);
        }
    }


    public void setProducto(Producto producto) {
        this.producto = producto;


        String estado = producto.getEstado_producto();


        nombreProducto.setText(producto.getNombre_producto());
        if(estado.equals("Activo")){
            rdbtnActivo.setSelected(true);
        }else{
            rdbtnInacnt.setSelected(true);
        }
        loteProducto.setText(Integer.toString(producto.getLote_producto()));

        presentacionProducto.setValue(producto.getPresentacion_producto());
        laboratorioProducto.setValue(producto.getLaboratorio().getNombre_laboratorio());
        datePicker.setValue(LocalDate.now());
        tipoProducto.setValue(producto.getTipo_producto());
        precioUnidad.setText(Double.toString(producto.getPrecio_unid()));
        precioCaja.setText(Double.toString(producto.getPrecio_caja()));
        infoProducto.setText(producto.getInfo_producto());





    }
}
