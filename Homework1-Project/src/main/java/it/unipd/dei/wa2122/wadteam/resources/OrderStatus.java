package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class OrderStatus implements Resource {
    private final int id;
    private final OrderStatusEnum status;
    private final String description;
    private final DateTime osDateTime;
    private final int idOrder;

    public OrderStatus(int id, OrderStatusEnum status, String description, DateTime osDateTime, int idOrder) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.osDateTime = osDateTime;
        this.idOrder = idOrder;
    }

    public int getId() {
        return id;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getOsDateTime() {
        return osDateTime;
    }

    public int getIdOrder() {
        return idOrder;
    }

    @Override
    public final String toString() {
        return status.getText();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("status", status);
        jsonObject.put("description", description);
        jsonObject.put("osDateTime", osDateTime.toJSON());
        jsonObject.put("idOrder", idOrder);
        return jsonObject;
    }

    public static OrderStatus fromJSON(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        OrderStatusEnum status = OrderStatusEnum.fromString(jsonObject.getString("status"));
        String description = jsonObject.getString("description");
        DateTime osDateTime = DateTime.fromJSON(jsonObject.getJSONObject("osDateTime"));
        int idOrder = jsonObject.getInt("idOrder");

        return new OrderStatus(id, status, description, osDateTime, idOrder);
    }
}
