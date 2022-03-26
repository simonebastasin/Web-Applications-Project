package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Employee implements Resource {
    final String username;
    final String name;
    final String surname;
    final Role role;
    final String password;

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
        this.password = null;
    }

    public final String getUsername() {
        return username;
    }

    public final String getName() {
        return name;
    }

    public final String getSurname() {
        return surname;
    }

    public final Role getRole() {
        return role;
    }

    public final String getPassword() {
        return password;
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
