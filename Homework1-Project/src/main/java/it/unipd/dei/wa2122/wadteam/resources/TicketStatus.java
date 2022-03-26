package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class TicketStatus implements Resource{

    private final int id;
    private final String status;
    private final String description;
    private final String tsDate;
    private final int idTicket;

    public TicketStatus(int id, String status, String description, String tsDate, int idTicket) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.tsDate = tsDate;
        this.idTicket = idTicket;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getTsDate() {
        return tsDate;
    }

    public int getIdTicket() {
        return idTicket;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("status", status);
        jsonObject.put("description", description);
        jsonObject.put("tsDate", tsDate);
        jsonObject.put("idTicket", idTicket);
        return jsonObject;
    }

    public static TicketStatus fromJson(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String status = jsonObject.getString("status");
        String description = jsonObject.getString("status");
        String tsDate = jsonObject.getString("tsDate");
        int idTicket = jsonObject.getInt("idTicket");

        return new TicketStatus(id, status,description,tsDate, idTicket);
    }

}
