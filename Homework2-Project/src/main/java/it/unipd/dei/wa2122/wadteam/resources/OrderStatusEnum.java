package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public enum OrderStatusEnum implements Resource {
    OPEN("Open"), PAYMENT_ACCEPTED("Payment accepted"), SHIPPED("Shipped"), DELIVERED("Delivered"), CANCELLED("Cancelled");

    final String text; //user-friendly text

    OrderStatusEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderStatus", text); //put user-friendly text in json
        return  jsonObject;
    }

    public static OrderStatusEnum fromJSON(JSONObject jsonObject) {
        return fromString(jsonObject.getString("orderStatus"));
    }

    /**
     * return the OrderStatusEnum instance associated to the user-friendly text provided
     * @param text
     * @return the OrderStatusEnum instance associated to the user-friendly text provided
     */
    public static OrderStatusEnum fromString(String text) {
        for (OrderStatusEnum item : OrderStatusEnum.values()) {
            if (item.text.equals(text)) {
                return item; //return the OrderStatusEnum instance associated to the user-friendly text provided
            }
        }
        return null;
    }


}
