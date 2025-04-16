package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static controller.ContactController.showContactView;

public class LoginView {

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




    public VBox getRoot() {
        return root;
    }

    public void eventsHandler() {

        loginButton.setDefaultButton(true);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("admin") && password.equals("password")) {
                // Simulate successful login
                Stage stage = (Stage) root.getScene().getWindow();
                stage.close(); // Close the login window
                showContactView();// Pass an empty array as a placeholder
                // Close the login window




                System.out.println("Login successful!");
            } else {
                errorLabel.setText("Invalid username or password");
            }
        });

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();

            }
        });

        usernameField.setOnKeyReleased(e -> {
            if (e.getCode() != KeyCode.ENTER) {
                errorLabel.setText("");
            }
        });

        passwordField.setOnKeyReleased(e -> {
            if (e.getCode() != KeyCode.ENTER) {
                errorLabel.setText("");
            }
        });
    }




}
