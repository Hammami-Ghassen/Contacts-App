package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Contact;
import model.ContactStorage;

import java.io.IOException;
import java.util.Objects;

public class AddContactController {


    /// handle the add contact button
    public ContactStorage contactModel=new ContactStorage();

    @FXML
    TextField nameField;
    @FXML
    TextField phoneField;
    @FXML
    TextField emailField;
    @FXML
    private Label header;
    @FXML
    Button button;


    public void setNameField(String name) {
        this.nameField.setText(name);

    }
    public void setPhoneField(String phone) {
        this.phoneField.setText(phone);
    }
    public void setEmailField(String email) {
        this.emailField.setText(email);
    }

    public void setHeadline(String editContact) {
        this.header.setText(editContact);
    }
    public void setButtonText(String editContact) {
        this.button.setText(editContact);
    }

    public void setButtonId(String editContact) {
        this.button.setId(editContact);
    }
}
