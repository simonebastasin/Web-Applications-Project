package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class ProductCategory implements Resource{
    private final String name;
    private final String description;

    public ProductCategory(String name, String description){
        this.name = name;
        this.description = description;
    }

    public final String getName() { return name; }

    public final String getDescription() { return description; }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", name);
        if(description != null)
            jsonObject.put("description", description);

        return jsonObject;
    }

    public static ProductCategory fromJSON(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = null;
        if(jsonObject.has("description"))
            description = jsonObject.getString("description");

        return new ProductCategory(name, description);
    }
}
