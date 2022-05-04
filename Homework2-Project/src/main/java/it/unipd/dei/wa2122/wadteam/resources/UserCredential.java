package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class UserCredential implements Resource {

    private final String identification;
    private final String password;
    private final TypeUserEnum type;
    private final String role;
    private final String email;
    private final Integer id;

     public UserCredential(String identification, String password, TypeUserEnum type, String role, String email, Integer id) {
        this.identification = identification;
        this.role=role;
        this.password = password;
        this.type = type;
         this.email = email;
         this.id = id;
     }

    public String getIdentification() {
        return identification;
    }

    public final String getPassword() {
        return password;
    }

    public final TypeUserEnum getType(){
        return type;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("identification", identification);
        if(password != null)
            jsonObject.put("password", password);
        jsonObject.put("type", type);
        if(email != null)
            jsonObject.put("email", email);
        return jsonObject;
    }

    public Integer getId() {
        return id;
    }

    //public enum TypeUser {EMPLOYEE, CUSTOMER}
}
