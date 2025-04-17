package model;

import org.json.JSONObject;

public class Contact {
    private String name;
    private String phone;
    private String email;
//    private String photoPath;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;

    }



    // Getters
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

//    public String getPhotoPath() {
//        return photoPath;
//    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setPhotoPath(String photoPath) {
//        this.photoPath = photoPath;
//    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("phone", phone);
        json.put("email", email);

        return json;
    }

    public static Contact fromJson(JSONObject contactJson) {
        return new Contact(
                contactJson.getString("name"),
                contactJson.getString("phone"),
                contactJson.getString("email")
        );
    }
}
