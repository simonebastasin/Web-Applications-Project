package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Role implements Resource {
    String name;
    String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Role(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        if(description != null)
            jsonObject.put("description", description);
        return jsonObject;
    }

    public static Role fromJson(JSONObject jsonObject) {
        String role = jsonObject.getString("role");
        String description = null;
        if(jsonObject.has("description"))
            description = jsonObject.getString("description");
        return new Role(role, description);
    }
}
