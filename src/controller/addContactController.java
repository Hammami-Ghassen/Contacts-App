package controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Contact;
import model.ContactStorage;

import java.util.Objects;



public class addContactController extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/addContactView.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public ContactStorage contactModel=new ContactStorage();
    private String imagePath;

    /// images handler

    @FXML
    private ImageView photoPreview;
    @FXML
    private void handleChoosePhoto( ) {
        imagePath= contactModel.uploadImage(photoPreview);
    }

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;

    /// images handler


    @FXML
    private void addContact() {
        Contact contact = new Contact(nameField.getText(), phoneField.getText(), emailField.getText());
        contactModel.saveContact(contact.toJson());
        nameField.clear();
        phoneField.clear();
        emailField.clear();

        photoPreview.setImage(null);
    }


}
