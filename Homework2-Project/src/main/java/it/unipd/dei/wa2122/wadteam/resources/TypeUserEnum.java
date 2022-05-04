package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public enum TypeUserEnum implements Resource{
    EMPLOYEE("Employee"), CUSTOMER("Customer");

    final String text; //user-friendly text

    TypeUserEnum(final String text){ this.text = text; }

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
        jsonObject.put("userType", text); //put user-friendly text in json
        return  jsonObject;
    }

    public static TypeUserEnum fromJSON(JSONObject jsonObject) {
        return fromString(jsonObject.getString("userType"));
    }

    /**
     * return the TypeUserEnum instance associated to the user-friendly text provided
     * @param text
     * @return the TypeUserEnum instance associated to the user-friendly text provided
     */
    public static TypeUserEnum fromString(String text) {
        for (TypeUserEnum item : TypeUserEnum.values()) {
            if (item.text.equals(text)) {
                return item; //return the TicketStatusEnum instance associated to the user-friendly text provided
            }
        }
        return null;
    }
}
