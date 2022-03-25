package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class OrderStatus extends Resource{
    public enum OrderStatusEnum{ OPEN, PAYMENT_ACCEPTED, SHIPPED, DELIVERED }
    Integer id;
    OrderStatusEnum status;
    String description;
    Integer idOrder; //TODO change with the Order POJO
    String osDateTime;

    public OrderStatus(Integer id, OrderStatusEnum status, String description, Integer idOrder, String osDateTime) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.idOrder = idOrder;
        this.osDateTime = osDateTime;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("status", status);
        jsonObject.put("description", description);
        jsonObject.put("idOrder", idOrder); //TODO: USE POJO
        jsonObject.put("osDateTime", osDateTime);
        return jsonObject;
    }

    public static OrderStatus fromJson(JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        OrderStatusEnum status = OrderStatusEnum.valueOf(jsonObject.getString("status"));
        String description = jsonObject.getString("description");
        Integer idOrder = jsonObject.getInt("idOrder"); //TODO: USE POJO
        String osDateTime = jsonObject.getString("osDateTime");
        return new OrderStatus(id, status, description, idOrder, osDateTime);
    }
}
