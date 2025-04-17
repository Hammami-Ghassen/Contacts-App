package controller;

import javafx.stage.Stage;
import view.LoginView;


import static controller.ContactController.showContactView;

public class LoginController {
    public static void handleLoginButton(String username, String password, Stage currentStage, LoginView loginView) {
        if (username.equals("admin") && password.equals("1234")) {
            currentStage.close();
            showContactView();
            System.out.println("Login successful!");
        } else {
            loginView.getErrorLabel().setText("Invalid username or password");
        }
    }




     // Flag to track if the login button was pressed










//
//        root.setOnKeyPressed(event -> {
//        if (event.getCode() == KeyCode.ENTER) {
//            loginButton.fire();
//
//        }
//    });
//
//        usernameField.setOnKeyReleased(e -> {
//        if (e.getCode() != KeyCode.ENTER) {
//            errorLabel.setText("");
//        }
//    });
//
//        passwordField.setOnKeyReleased(e -> {
//        if (e.getCode() != KeyCode.ENTER) {
//            errorLabel.setText("");
//        }
//    });
}
