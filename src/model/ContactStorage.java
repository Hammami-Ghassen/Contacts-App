package model;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public class ContactStorage {


    private static final String FILE_PATH = "contacts.json";

    public void saveContact(JSONObject contact) {
        try {
            JSONArray contactsArray;

            // Load existing contacts or create new array
            Path path = Paths.get(FILE_PATH);
            if (Files.exists(path)) {
                String content = new String(Files.readAllBytes(path));
                contactsArray = new JSONArray(content);
            } else {
                contactsArray = new JSONArray();
            }

            // Add new contact
            contactsArray.put(contact);

            // Save updated array to file
            Files.write(path,
                    contactsArray.toString(4).getBytes(), // Indent with 4 spaces
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );



        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    ///method to handle image upload

    @FXML
public String uploadImage(ImageView photoPreview) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose a photo");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
    );

    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
        try {
            // Display the selected image in the ImageView
            Image image = new Image(file.toURI().toString());
            photoPreview.setImage(image);

            // Save the image to the destination folder
            String fileName = UUID.randomUUID() + "_" + file.getName();
            File destDir = new File("resources/photos");
            if (!destDir.exists()) destDir.mkdirs();

            File destFile = new File(destDir, fileName);
            Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return "/resources/photos/"+fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return null;
}

    public Contact[] loadContacts() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (Files.exists(path)) {
                String content = new String(Files.readAllBytes(path));
                JSONArray contactsArray = new JSONArray(content);
                Contact[] contacts = new Contact[contactsArray.length()];

                for (int i = 0; i < contactsArray.length(); i++) {
                    JSONObject contactJson = contactsArray.getJSONObject(i);
                    Contact contact = new Contact(
                            contactJson.getString("name"),
                            contactJson.getString("phone"),
                            contactJson.getString("email")
                    );
                    contacts[i] = contact;
                }
                return contacts;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Contact[0];
    }



    public boolean deleteContact(String name) {
        try {
            // Read the existing contacts from the file
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONArray contactsArray = new JSONArray(content);

            // Iterate through the contacts array to find the contact by name
            for (int i = 0; i < contactsArray.length(); i++) {
                JSONObject contact = contactsArray.getJSONObject(i);
                if (contact.getString("name").equalsIgnoreCase(name)) {
                    // Remove the contact from the array
                    contactsArray.remove(i);

                    // Save the updated contacts array back to the file
                    Files.write(Paths.get(FILE_PATH),
                            contactsArray.toString(4).getBytes(),
                            StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING);

                    return true; // Contact found and deleted
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Contact not found
    }
}


