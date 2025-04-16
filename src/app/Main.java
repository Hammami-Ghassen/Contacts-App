package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.LoginView;

import java.util.Objects;

public class Main extends Application {
    static LoginView loginView = new LoginView();

    public static void main(String[] args) {
        loginView.eventsHandler();
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(loginView.getRoot(), 400, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}