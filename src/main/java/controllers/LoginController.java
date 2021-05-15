package controllers;

import Conection.ConexionBD;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author palmachris7
 */
public class LoginController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    Connection connection;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @FXML
    private StackPane stackpane;

    @FXML
    private Label lblErrors;

    @FXML
    private Label lblRecup;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private JFXTextField txtUser;

    @FXML
    private JFXButton btnIniciar;

    @FXML
    private JFXButton btnminimize;

    @FXML
    private JFXButton btnclose;


    public void Iniciando(ActionEvent event) throws IOException {

        String user = txtUser.getText();
        String pass = txtPass.getText();

        if (user.equals("") && pass.equals("")) {

            setLblError(Color.TOMATO, "Los campos están vacíos");

        } else {
            String sql = "SELECT * FROM usuarios Where username = ? and contrasena = ?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, pass);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {


                    //Instancia dialog
                    JFXDialogLayout dialogLayout = new JFXDialogLayout();

                    JFXButton button = new JFXButton("Aceptar");
                    button.setStyle("-fx-background-color: #27A752");
                    button.setButtonType(JFXButton.ButtonType.FLAT);
                    dialogLayout.setHeading(new Text("Bienvenido al Sistema"));
                    String content = "Usuario y Contraseña Correctos";
                    dialogLayout.setBody(new Text(content));

                    JFXDialog dialog = new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                    dialogLayout.getStyleClass().add("dialog");
                   dialogLayout.setStyle("-fx-background-color: #91F587");
                   dialogLayout.setStyle("-fx-border-color: blue");
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent eventi) -> {
                        dialog.close();



                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        try {
                            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Main.fxml")));
                            stage.setScene(scene);
                            stage.setMaximized(true);
                            stage.centerOnScreen();
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }


                    });


                    dialogLayout.setActions(button);
                    dialog.show();

                } else if (!resultSet.next()) {
                    setLblError(Color.PURPLE, "Usuario y/o Cotraseña Incorrectos");
                }

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());

            }
        }


    }


    @FXML
    void Iniciar(ActionEvent event) throws IOException {

        Iniciando(event);

    }


    @FXML
    void presionar(KeyEvent event) throws IOException {

        String user = txtUser.getText();
        String pass = txtPass.getText();

        if (user.equals("") && pass.equals("")) {

            setLblError(Color.TOMATO, "Los campos están vacíos");

        } else {
            String sql = "SELECT * FROM usuarios Where username = ? and contrasena = ?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, pass);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Main.fxml")));
                    stage.centerOnScreen();
                    Mover(scene,stage);
                    stage.show();


                } else if (!resultSet.next()) {
                    setLblError(Color.PURPLE, "Usuario y/o Cotraseña Incorrectos");
                }

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());

            }
        }


    }


    @FXML
    void recuperar(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Recuperacion.fxml")));

        Mover(scene,stage);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void minimizar(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    void cerrar(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void IniciarEnter(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {


        }

    }

    public LoginController() {
        connection = ConexionBD.getConnection();
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }



    public void Mover(Scene scene,Stage stage){

        scene.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });

        scene.setOnMouseDragged(event12 -> {
            stage.setX(event12.getScreenX() - xOffset);
            stage.setY(event12.getScreenY() - yOffset);
        });

        stage.setScene(scene);
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }





}