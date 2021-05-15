package metodos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.EventObject;

public class Acciones {
    private double xOffset = 0;
    private double yOffset = 0;
    

    public void Mover(Scene scene, Stage stage){

        scene.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });

        scene.setOnMouseDragged(event12 -> {
            stage.setX(event12.getScreenX() - xOffset);
            stage.setY(event12.getScreenY() - yOffset);
        });

    }




    public void NuevaventanaModal(String string){
        try {

            FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/fxml/"+string+".fxml"));
            Parent ventana= fxmloader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(ventana));
            stage.initStyle(StageStyle.UNDECORATED);
            Mover(ventana.getScene(), stage);
            stage.setScene(ventana.getScene());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void NuevaVentana(MouseEvent event,String string) throws IOException {
        try {
        Parent view2 = FXMLLoader.load(getClass().getResource("/fxml/"+string+".fxml"));

        Scene scene2 = new Scene(view2);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.setMaximized(true);

        Mover(scene2,window);
        window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarsesion(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")));
       Mover(scene,stage);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.centerOnScreen();
        stage.show();
    }
}
