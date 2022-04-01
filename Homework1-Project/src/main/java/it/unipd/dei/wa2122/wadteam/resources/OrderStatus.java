package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class OrderStatus implements Resource {
    private final Integer id;
    private final OrderStatusEnum status;
    private final String description;
    private final Integer idOrder;
    private final String osDateTime;

    public OrderStatus(Integer id, OrderStatusEnum status, String description, Integer idOrder, String osDateTime) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.idOrder = idOrder;
        this.osDateTime = osDateTime;
    }

    public Integer getId() {
        return id;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public String getOsDateTime() {
        return osDateTime;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("status", status);
        jsonObject.put("description", description);
        jsonObject.put("idOrder", idOrder);
        jsonObject.put("osDateTime", osDateTime);
        return jsonObject;
    }

    public static OrderStatus fromJson(JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        OrderStatusEnum status = OrderStatusEnum.valueOf(jsonObject.getString("status"));
        String description = jsonObject.getString("description");
        Integer idOrder = jsonObject.getInt("idOrder");
        String osDateTime = jsonObject.getString("osDateTime");
        return new OrderStatus(id, status, description, idOrder, osDateTime);
    }
}
