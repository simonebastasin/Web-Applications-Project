package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class OrderStatus extends Resource{
    public enum OrderStatusEnum{ OPEN, PAYMENT_ACCEPTED, SHIPPED, DELIVERED }
    Integer ID;
    OrderStatusEnum status;
    String description;
    Integer ID_order;
    String OS_dateTime;

    public OrderStatus(Integer ID, OrderStatusEnum status, String description, Integer ID_order, String OS_dateTime) {
        this.ID = ID;
        this.status = status;
        this.description = description;
        this.ID_order = ID_order;
        this.OS_dateTime = OS_dateTime;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", ID);
        jsonObject.put("status", status);
        jsonObject.put("description", description);
        jsonObject.put("ID_order", ID_order);
        jsonObject.put("OS_dateTime", OS_dateTime);
        return jsonObject;
    }

    public static OrderStatus fromJson(JSONObject jsonObject) {
        Integer ID = jsonObject.getInt("ID");
        OrderStatusEnum status = OrderStatusEnum.valueOf(jsonObject.getString("status"));
        String description = jsonObject.getString("description");
        Integer ID_order = jsonObject.getInt("ID_order");
        String OS_dateTime = jsonObject.getString("OS_dateTime");
        return new OrderStatus(ID, status, description, ID_order, OS_dateTime);
    }
}
