package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class UserCredential implements Resource {

    private final String identification;
    private final String password;
    private final TypeUser type;
    private final String role;

     public UserCredential(String identification, String password, TypeUser type, String role) {
        this.identification = identification;
        this.role=role;
        this.password = password;
        this.type = type;
    }

    public String getIdentification() {
        return identification;
    }

    public final String getPassword() {
        return password;
    }

    public final TypeUser getType(){
        return type;
    }

    public String getRole() {
        return role;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("identification", identification);
        jsonObject.put("password", password);
        jsonObject.put("type", type);
        return jsonObject;
    }
    public enum TypeUser {EMPLOYEE, CUSTOMER}
}
