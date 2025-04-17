package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static controller.LoginController.handleLoginButton;

public class LoginView {

    ///  Building the login view UI

    private final VBox root = new VBox(10);
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button loginButton = new Button("Login");
    private final Label errorLabel = new Label();
    public LoginView() {
        // Create labels
        Label userLabel = new Label("Username:");

        // Create the custom context menu for the username field
        ContextMenu usernameContextMenu = new ContextMenu();

        // Create the "Paste" menu item for username
        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(e -> {
            // Perform the paste action
            usernameField.paste();
        });

        // Add the "Paste" menu item to the context menu
        usernameContextMenu.getItems().add(pasteItem);

        // Set the custom context menu to the username field
        usernameField.setContextMenu(usernameContextMenu);

        // Password field (context menu disabled)
        Label passLabel = new Label("Password:");

        passwordField.setContextMenu(new ContextMenu()); // Disable the context menu

        // Login button

        HBox buttonBox = new HBox(loginButton);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

        // Error label
        HBox errorBox = new HBox(errorLabel);
        errorLabel.getStyleClass().add("error-label");
        errorBox.setAlignment(javafx.geometry.Pos.CENTER);

        // Add all components to the root container
        root.getChildren().addAll(userLabel, usernameField, passLabel, passwordField, buttonBox, errorBox);

        // Add some styling for the VBox
        root.getStyleClass().add("vbox");
        VBox.setMargin(buttonBox, new Insets(30, 0, 0, 0)); // top, right, bottom, left
    }

    /// Show login view UI
    public void showLoginView() {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Login");
            eventsHandler(stage);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eventsHandler(Stage stage) {
        loginButton.setDefaultButton(true);
        loginButton.setOnAction(e -> {
            handleLoginButton(usernameField.getText(), passwordField.getText(), stage, this);
        });
    }
    public Label getErrorLabel() {
        return errorLabel;
    }

    public VBox getRoot() {
        return root;
    }



}
