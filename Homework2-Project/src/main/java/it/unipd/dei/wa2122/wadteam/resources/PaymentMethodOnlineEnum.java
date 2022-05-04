package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public enum PaymentMethodOnlineEnum implements Resource {

    CREDIT_CARD("Credit Card"),
    GOOGLE_PAY("Google Pay"),
    APPLE_PAY("Apple Pay");

    /** user-friendly text */
    private final String text;

    PaymentMethodOnlineEnum(final String text) {
        this.text = text;
    }

    public final String getText() {
        return text;
    }

    @Override
    public final String toString() {
        return text;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("paymentType", text);
        return jsonObject;
    }

    public static PaymentMethodOnlineEnum fromJSON(JSONObject jsonObject) {
        return fromString(jsonObject.getString("paymentType"));
    }

    /**
     * @param text user-friendly text provided
     * @return the {@code PaymentMethodOnlineEnum} object associated to the text provided
     */
    public static PaymentMethodOnlineEnum fromString(String text) {
        for(PaymentMethodOnlineEnum item : PaymentMethodOnlineEnum.values()) {
            if(item.text.equals(text)) {
                return item;
            }
        }
        return null;
    }
}