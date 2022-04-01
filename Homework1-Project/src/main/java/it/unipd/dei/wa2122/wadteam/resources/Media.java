package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Media implements Resource {
    final int id;
    final String filename;
    final String mimetype;

    public Media(int id, String filename, String mimetype) {
        this.id = id;
        this.filename = filename;
        this.mimetype = mimetype;
    }


    public final int getId() {
        return id;
    }

    public final String getFilename() {
        return filename;
    }

    public final String getMimetype() {
        return mimetype;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("filename", filename);
        jsonObject.put("mimetype", mimetype);
        return  jsonObject;
    }

    public static Media fromJSON(JSONObject jsonObject) {
        return new Media(jsonObject.getInt("id"), jsonObject.getString("filename"), jsonObject.getString("mimetype"));
    }
}
