//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.json.JSONArray;
import org.json.JSONObject;

public class ContactStorage {
    private static final String FILE_PATH = "contacts.json";

    public ContactStorage() {
    }

    public void saveContact(JSONObject contact) {
        try {
            JSONArray contactsArray = this.loadContacts();
            // Add new contact
            contactsArray.put(contact);        // Add new contact

            Path path = Paths.get("contacts.json");
            Files.write(path, contactsArray.toString(4).getBytes(), new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING});

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public String uploadImage(ImageView photoPreview) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a photo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Images", new String[]{"*.png", "*.jpg", "*.jpeg"})});
        File file = fileChooser.showOpenDialog((Window)null);
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                photoPreview.setImage(image);
                String var10000 = String.valueOf(UUID.randomUUID());
                String fileName = var10000 + "_" + file.getName();
                File destDir = new File("resources/photos");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                File destFile = new File(destDir, fileName);
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return "/resources/photos/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public JSONArray loadContacts() {
        try {
            Path path = Paths.get("contacts.json");
            if (Files.exists(path, new LinkOption[0])) {
                String content = new String(Files.readAllBytes(path));
                return new JSONArray(content);
            } else {
                return new JSONArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteContact(String name) {
        try {
            Path path = Paths.get("contacts.json");
            String content = new String(Files.readAllBytes(path));
            System.out.println(content);
            JSONArray contactsArray = new JSONArray(content);

            for(int i = 0; i < contactsArray.length(); ++i) {
                JSONObject contact = contactsArray.getJSONObject(i);
                if (contact.getString("name").equalsIgnoreCase(name)) {
                    contactsArray.remove(i);
                    Files.write(path, contactsArray.toString(4).getBytes(), new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING});
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
