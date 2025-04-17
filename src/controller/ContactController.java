package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
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
import org.json.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContactController {


    // The FlowPane where all the contact cards are displayed
    @FXML
    private FlowPane flowPane;

    // TextField for entering search query (make sure to add this TextField into your FXML)
    @FXML
    private TextField searchTextField;




    /// Show ContactView UI
    public static void showContactView() {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ContactController.class.getResource("/fxml/ContactView.fxml"));
            Parent root= loader.load();
            loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///  initialize
    public void initialize(){
        showContacts(new ContactStorage().loadContacts());
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterContacts(newValue);
        });
    }


    ///getters and setters
    public FlowPane getFlowPane() {
        return flowPane;
    }





   /// handlers
    //handle search
   private void filterContacts(String query) {
       // Clear existing cards in the UI.
       flowPane.getChildren().clear();
       List <Contact> allContacts = new ContactStorage().loadContacts();
       // When query is empty, show all contacts.
       if (query == null || query.trim().isEmpty()) {
           showContacts(allContacts);
       } else {
           // Filter contacts by name (case-insensitive)
           List<Contact> filtered = allContacts.stream()
                   .filter(contact -> contact.getName().toLowerCase().contains(query.toLowerCase()))
                   .collect(Collectors.toList());
           showContacts(filtered);
       }
   }


   //handle add contact button

    public void handleAddContact() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ContactController.class.getResource("/fxml/AddContactView.fxml")));
        Parent root;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        AddContactController addContactController = loader.getController();
        stage.show();
        addContactController.button.setOnAction(event -> {
            String name = addContactController.nameField.getText();
            String phone = addContactController.phoneField.getText();
            String email = addContactController.emailField.getText();

            // Validation des champs
            if (name == null || name.trim().isEmpty()) {
                addContactController.setErrorLabel("Le nom est obligatoire.");
                return;
            }
            if (phone == null || phone.trim().isEmpty()) {
                addContactController.setErrorLabel("Le numéro de téléphone est obligatoire.");
                return;
            }
            if (email == null || email.trim().isEmpty() || !email.contains("@")) {
                addContactController.setErrorLabel("L'adresse e-mail est invalide.");
                return;
            }

            // Si tout est valide, créer et sauvegarder le contact
            Contact contact = new Contact(name, phone, email);
            ContactStorage contactStorage = new ContactStorage();
            contactStorage.saveContact(contact);
            flowPane.getChildren().clear();
            showContacts(contactStorage.loadContacts());
            stage.close();
        });

    }



    // Delete contact handler: remove from storage and UI.
    private void handleDeleteContact(Contact contact, AnchorPane cardContainer) {
        ContactStorage contactStorage = new ContactStorage();
        contactStorage.deleteContact(contact.getName());
        flowPane.getChildren().remove(cardContainer);
        System.out.println("Deleted contact: " + contact.getName());
    }
    // Edit contact handler: open the edit form.
    private void handleEditContact(Contact contact) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ContactController.class.getResource("/fxml/AddContactView.fxml")));
        Parent root;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        AddContactController addContactController = loader.getController();
        addContactController.setNameField(contact.getName());
        addContactController.setPhoneField(contact.getPhone());
        addContactController.setEmailField(contact.getEmail());
        addContactController.setHeadline("Edit Contact");
        addContactController.setButtonText("Save");
        addContactController.setButtonId("editContact");
        stage.show();

        // Set the action for the "Save" button
        addContactController.button.setOnAction(event -> {
            String name = addContactController.nameField.getText();
            String phone = addContactController.phoneField.getText();
            String email = addContactController.emailField.getText();

            // Validation des champs
            if (name == null || name.trim().isEmpty()) {
                addContactController.setErrorLabel("Le nom est obligatoire.");
                return;
            }
            if (phone == null || phone.trim().isEmpty() || !phone.matches("\\d{8}")) {
                addContactController.setErrorLabel("Le numéro de téléphone doit contenir 10 chiffres.");
                return;
            }
            if (email == null || email.trim().isEmpty() || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                addContactController.setErrorLabel("L'adresse e-mail est invalide.");
                return;
            }

            // Mise à jour des informations du contact
            contact.setName(name);
            contact.setPhone(phone);
            contact.setEmail(email);
            ContactStorage contactStorage = new ContactStorage();
            List<Contact> contacts = contactStorage.loadContacts();
            String originalName = contact.getName();

            // Mise à jour du contact dans le stockage
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getName().equalsIgnoreCase(originalName)) {
                    contacts.set(i, contact);
                    break;
                }
            }



            // Save the updated list back to storage
            try {
                JSONArray updatedContacts = new JSONArray();
                for (Contact c : contacts) {
                    updatedContacts.put(c.toJson());
                }
                Files.write(Paths.get("contacts.json"), updatedContacts.toString(4).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Close the edit window and refresh the UI
            stage.close();
            flowPane.getChildren().clear();
            showContacts(contactStorage.loadContacts());
        });


    }

    public void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }


    /// create a card container
    public AnchorPane createContactCard(Contact contact) {
        // Outer AnchorPane (the card)
        AnchorPane cardContainer = new AnchorPane();
        cardContainer.setPrefSize(188, 150);
        cardContainer.getStyleClass().add("card_container");

        // Create ContextMenu with Edit and Delete options
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        ContextMenu cardContextMenu = new ContextMenu(editItem, deleteItem);
        cardContextMenu.getStyleClass().add("context-menu");
        deleteItem.getStyleClass().add("menu-item");
        editItem.getStyleClass().add("menu-item");

        // Set actions for Edit and Delete
        editItem.setOnAction(e -> handleEditContact(contact));
        cardContextMenu.setAutoHide(true);
        // Use a local variable for this card container as it is needed in the delete handler.
        deleteItem.setOnAction(e -> handleDeleteContact(contact, cardContainer));

        // Menu button to trigger the context menu
        Image menuImg = new Image(ContactController.class.getResource("/icons/icons8-points-de-suspension-90.png").toExternalForm());
        ImageView menuIcon = new ImageView(menuImg);
        menuIcon.setFitHeight(21);
        menuIcon.setFitWidth(24);
        Button menuButton = new Button();
        menuButton.setGraphic(menuIcon);
        menuButton.setLayoutX(165);
        menuButton.setLayoutY(7);
        menuButton.setOnAction(e -> cardContextMenu.show(menuButton, Side.BOTTOM, 0, 0));

        // VBox content for contact details
        VBox mainVBox = new VBox(10);
        mainVBox.setLayoutX(7);
        mainVBox.setLayoutY(6);
        mainVBox.setPrefSize(156, 138);

        // HBox for photo and name
        ImageView photoView = new ImageView();
        photoView.setFitHeight(40);
        photoView.setFitWidth(40);
        photoView.setPreserveRatio(true);
        // Circular clip for the photo
        Circle clip = new Circle(20);
        photoView.setClip(clip);

        Label nameLabel = new Label(contact.getName());
        nameLabel.setPrefSize(66, 40);
        nameLabel.getStyleClass().add("name");
        nameLabel.setStyle("-fx-font-weight: bold;");
        VBox nameBox = new VBox(nameLabel);
        HBox topHBox = new HBox(10, photoView, nameBox);

        // Separator line
        Separator separator = new Separator();

        // Contact details (phone and email)
        Label phoneIcon = new Label("📞");
        Label phoneLabel = new Label(contact.getPhone());
        HBox phoneBox = new HBox(10, phoneIcon, phoneLabel);

        Label emailIcon = new Label("📧");
        Label emailLabel = new Label(contact.getEmail());
        emailLabel.setPrefSize(123, 25);
        HBox emailBox = new HBox(10, emailIcon, emailLabel);

        VBox detailsBox = new VBox(8, phoneBox, emailBox);
        detailsBox.setPrefSize(253, 67);

        // Assemble the card layout
        mainVBox.getChildren().addAll(topHBox, separator, detailsBox);
        cardContainer.getChildren().addAll(menuButton, mainVBox);

        // Set context menu to show on right-click anywhere on the card
        cardContainer.setOnContextMenuRequested(e -> cardContextMenu.show(cardContainer, e.getScreenX(), e.getScreenY()));

        return cardContainer;
    }



    // Method to show contacts in the FlowPane
    public void showContacts(List<Contact> contacts) {
        for (Contact contact : contacts) {
            AnchorPane cardContainer = createContactCard(contact);
            flowPane.getChildren().add(cardContainer);
        }
    }


}
