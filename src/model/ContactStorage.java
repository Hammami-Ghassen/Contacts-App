package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ContactStorage {
    private static final String FILE_PATH = "contacts.json";

    /// save contact
    public void saveContact(Contact contact) {
        try {
            JSONArray contactsArray = this.loadContactsJSONArr();
            // Add new contact
            contactsArray.put(contact.toJson());        // Add new contact

            Path path = Paths.get("contacts.json");
            Files.write(path, contactsArray.toString(4).getBytes(), new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING});

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /// delete contact
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



    // Load contacts from the JSON file
    public JSONArray loadContactsJSONArr() {
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
    public List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        JSONArray contactsArray=loadContactsJSONArr();
        for (int i = 0; i < contactsArray.length(); i++) {
            JSONObject contactJson = contactsArray.getJSONObject(i);
            Contact contact =Contact.fromJson(contactJson);
            contacts.add(contact);
        }
        return contacts;
    }

}
