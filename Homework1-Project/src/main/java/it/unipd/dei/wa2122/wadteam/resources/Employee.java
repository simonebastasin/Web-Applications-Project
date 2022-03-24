package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Employee extends Resource{
    String username;
    String name;
    String surname;
    Role role;
    String password;

    public Employee(String username, String name, String surname, Role role, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.password = password;
    }

    public Employee(String username, String name, String surname, Role role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("name", name);
        jsonObject.put("surname", surname);
        jsonObject.put("role", role.toJSON());
        if(password != null)
            jsonObject.put("password", password);
        return jsonObject;
    }

    public static Employee fromJson(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String name = jsonObject.getString("name");
        String surname = jsonObject.getString("surname");
        Role role = Role.fromJson(jsonObject.getJSONObject("role"));
        String password = null;
        if(jsonObject.has("password"))
            password = jsonObject.getString("password");
        return new Employee(username, name, surname, role, password);
    }
}
