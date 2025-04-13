package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.LoginView;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginView loginView = new LoginView();
        loginView.eventsHandler();
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(loginView.getRoot(),400,350);
        String css=this.getClass().getResource("/login.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}