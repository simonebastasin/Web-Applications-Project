package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public enum TicketStatusEnum implements Resource{
    OPEN("Open"), PROCESSING("Processing"), CLOSED("Closed"), RETURN("RETURN");

    final String text; //user-friendly text

    TicketStatusEnum(final String text){ this.text = text; }

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
        jsonObject.put("ticketStatus", text); //put user-friendly text in json
        return  jsonObject;
    }

    public static TicketStatusEnum fromJSON(JSONObject jsonObject) {
        return fromString(jsonObject.getString("ticketStatus"));
    }

    /**
     * return the TicketStatusEnum instance associated to the user-friendly text provided
     * @param text
     * @return the TicketStatusEnum instance associated to the user-friendly text provided
     */
    public static TicketStatusEnum fromString(String text) {
        for (TicketStatusEnum item : TicketStatusEnum.values()) {
            if (item.text.equals(text)) {
                return item; //return the TicketStatusEnum instance associated to the user-friendly text provided
            }
        }
        return null;
    }
}
