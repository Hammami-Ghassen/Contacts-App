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



public class addContactController  {

    public ContactStorage contactModel=new ContactStorage();
    ContactController contactController = new ContactController();
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
    /// TODO: add image path to the contact model


    @FXML
    private void addContact() {

        Contact contact = new Contact(nameField.getText(), phoneField.getText(), emailField.getText());
        contactModel.saveContact(contact.toJson());
        FXMLLoader contactLoader=new FXMLLoader(getClass().getResource("/view/contactView.fxml"));
        ContactController contactController = contactLoader.getController();

        nameField.clear();
        phoneField.clear();
        emailField.clear();
        photoPreview.setImage(null);
    }


}
