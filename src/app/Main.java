package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;

public class Main extends Application {
    static LoginView loginView = new LoginView();
    public static void main(String[] args) {
        launch(args);


    }
    @Override
    public void start(Stage stage) throws Exception {
        loginView.showLoginView();
    }
}
