package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entidades.Laboratorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static Conection.ConexionBD.getConnection;

public class LaboratorioController implements Initializable {

    @FXML
    private JFXButton btnCerrar;

    @FXML
    private JFXTextField filterField;


    @FXML
    public TableColumn<Laboratorio,Integer> clmId;
    @FXML
    public TableColumn<Laboratorio, String>clmLaboratorio;
    @FXML
    public TableColumn<Laboratorio, String>clmEstado;
    @FXML
    private TableView<Laboratorio> tblViewLaboratorio;



    @FXML
    void cerrar(MouseEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        showProducts();



    }



    public void actualizar(){
        ObservableList<Laboratorio> laboratoriosList = getLaboratoriosList();
        SortedList<Laboratorio> sortedData = new SortedList<>(laboratoriosList);
        tblViewLaboratorio.setItems(sortedData);
    }


    ///Tabla////
    public ObservableList<Laboratorio> getLaboratoriosList() {
        ObservableList<Laboratorio> laboratoriosList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "Select id_laboratorio, nombre_laboratorio, estado_laboratorio From laboratorio;";



        Statement st;
        ResultSet rs;

        try {
            assert connection != null;
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Laboratorio laboratorio;
            while(rs.next()) {
                laboratorio = new Laboratorio(
                        rs.getInt("id_laboratorio"),
                        rs.getString("nombre_laboratorio"),
                        rs.getString("estado_laboratorio")
                        );

                laboratoriosList.add(laboratorio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return laboratoriosList;
    }





    public void showProducts() {
        ObservableList<Laboratorio> laboratoriosList = getLaboratoriosList();
        clmId.setCellValueFactory(new PropertyValueFactory<Laboratorio,Integer>("id_laboratorio"));
        clmLaboratorio.setCellValueFactory(new PropertyValueFactory<Laboratorio,String>("nombre_laboratorio"));
        clmEstado.setCellValueFactory(new PropertyValueFactory<Laboratorio,String>("estado_laboratorio"));


        FilteredList<Laboratorio> filteredData = new FilteredList<>(laboratoriosList, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(laboratorio -> {



                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (laboratorio.getNombre_laboratorio().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else return laboratorio.getEstado_laboratorio().toLowerCase().indexOf(lowerCaseFilter) != -1; // Filter matches last name.
            });
        });


        SortedList<Laboratorio> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tblViewLaboratorio.comparatorProperty());

        tblViewLaboratorio.setItems(sortedData);

    }


}
