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
import model.Contact;
import model.ContactStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactController {

    // The FlowPane where all the contact cards are displayed
    @FXML
    private FlowPane flowPane;

    // TextField for entering search query (make sure to add this TextField into your FXML)
    @FXML
    private TextField searchTextField;

    // Store all contacts loaded from the JSON file
    private List<Contact> allContacts = new ArrayList<>();

    @FXML
    public void initialize() {
        // Load all contacts only once
        loadAllContacts();

        // Show all contacts initially
        showContacts(allContacts);

        // Add a listener to filter contacts as the user types (if the TextField was linked correctly in FXML)
        if (searchTextField != null) {
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filterContacts(newValue));
        }
    }

    // Loads contacts from storage and stores them in the allContacts list.
    private void loadAllContacts() {
        ContactStorage contactStorage = new ContactStorage();
        allContacts = List.of(contactStorage.loadContacts());
    }

    // Filters the contacts based on the query and updates the FlowPane.
    private void filterContacts(String query) {
        // Clear existing cards in the UI.
        flowPane.getChildren().clear();

        // When query is empty, show all contacts.
        if (query == null || query.trim().isEmpty()) {
            showContacts(allContacts);
        } else {
            // Filter contacts by name (case insensitive)
            List<Contact> filtered = allContacts.stream()
                    .filter(contact -> contact.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            showContacts(filtered);
        }
    }

    // Utility method that adds a list of contacts to the FlowPane.
    private void showContacts(List<Contact> contacts) {
        for (Contact contact : contacts) {
            AnchorPane cardContainer = createContactCard(contact);
            updateContactCard(cardContainer);
        }
    }

    // This method creates a single contact card (as an AnchorPane) for the given contact.
    private AnchorPane createContactCard(Contact contact) {
        // Outer AnchorPane (the card)
        AnchorPane cardContainer = new AnchorPane();
        cardContainer.setPrefSize(188, 150);
        cardContainer.getStyleClass().add("card_container");

        // Create ContextMenu with Edit and Delete options
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        ContextMenu cardContextMenu = new ContextMenu(editItem, deleteItem);

        // Set actions for Edit and Delete
        editItem.setOnAction(e -> handleEditContact(contact));
        cardContextMenu.setAutoHide(true);
        // Use a local variable for this card container as it is needed in the delete handler.
        AnchorPane currentCard = cardContainer;
        deleteItem.setOnAction(e -> handleDeleteContact(contact, currentCard));

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

    // Add the card to the UI (FlowPane)
    @FXML
    public void updateContactCard(AnchorPane contactCard) {
        flowPane.getChildren().add(contactCard);
    }

    // Handle the addition of a new contact (opens a new window)
    @FXML
    private void handleAddContact(ActionEvent event) throws Exception {
        addContactController addContactController = new addContactController();
        addContactController.start(new Stage());
    }

    // Edit contact handler
    private void handleEditContact(Contact contact) {
        System.out.println("Editing contact: " + contact.getName());
        // Add your editing logic here – for example, open a pre-filled form.
    }

    // Delete contact handler: remove from storage and UI.
    private void handleDeleteContact(Contact contact, AnchorPane cardContainer) {
        ContactStorage contactStorage = new ContactStorage();
        contactStorage.deleteContact(contact.getName());
        flowPane.getChildren().remove(cardContainer);
        System.out.println("Deleted contact: " + contact.getName());
    }
}
