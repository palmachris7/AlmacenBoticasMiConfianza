
package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Conection.ConexionBD;
import com.jfoenix.controls.*;
import entidades.Producto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import metodos.Acciones;


/**
 * FXML Controller class
 *
 * @author palmachris7
 */
public class FrmMedicamentosController<fileChooser> implements Initializable {



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
    void agregar(MouseEvent event) throws IOException {
        fis=new FileInputStream(file);
        Connection con = ConexionBD.getConnection();
        RadioButton selectedRadioButton = (RadioButton) Estado.getSelectedToggle();
//        String query = "insert into productos values (null," + "'" + nombreProducto.getText() + "'," +
//                "'" + presentacionProducto.getValue().toString() + "',"
//                + Integer.parseInt(loteProducto.getText()) + "," +
//                "'" + datePicker.getValue() + "'," +
//                "'" + infoProducto.getText() + "'," +
//                "'" + selectedRadioButton.getText() + "',"
//                + Double.parseDouble(precioUnidad.getText()) + "," +
//                +Double.parseDouble(precioCaja.getText()) + "," +
//                "'" + tipoProducto.getValue().toString() + "'," +
//                "1)";


        try {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO productos VALUES (?,?,?,?,?,?,?,?,?,?,?,?)")) {
                ps.setString(1,null);
                ps.setString(2, nombreProducto.getText());
                ps.setString(3, presentacionProducto.getValue().toString());
                ps.setInt(4, Integer.parseInt(loteProducto.getText()));
                ps.setString(5, String.valueOf(datePicker.getValue()));
                ps.setString(6, infoProducto.getText());
                ps.setString(7, selectedRadioButton.getText());
                ps.setDouble(8, Double.parseDouble(precioUnidad.getText()));
                ps.setDouble(9, Double.parseDouble(precioCaja.getText()));
                ps.setString(10, tipoProducto.getValue().toString());
                ps.setInt(11,1);
                // Imagen
                ps.setBinaryStream(12, (InputStream)fis,(int)file.length());

                ps.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        acciones.NuevaVentana(event, "Productos");

        stage.close();
        okClicked = true;






    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        presentacionProducto.getItems().add("Tableta");
        presentacionProducto.getItems().add("Jarábe");
        presentacionProducto.getItems().add("Cápsula");
        presentacionProducto.getItems().add("Grajea");


        tipoProducto.getItems().add("Genérico");
        tipoProducto.getItems().add("Marca");

        laboratorioProducto.getItems().add("1");
        laboratorioProducto.getItems().add("2");


    }

    public void setDialogStage(Stage dialogStage) {
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
}
