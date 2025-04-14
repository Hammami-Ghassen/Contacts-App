package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class MainApp {
    public void showContactView() {
        try{
            Stage stage = new Stage();
            Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/contactView.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}