package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Discount implements Resource {
    private final Integer id;
    private final Integer percentage;
    private final DateTime startDate;
    private final DateTime endDate;

    public Discount(Integer id, Integer percentage, DateTime startDate, DateTime endDate) {
        this.id = id;
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public final Integer getId() {
        return id;
    }

    public final Integer getPercentage() {
        return percentage;
    }

    public final DateTime getStartDate() {
        return startDate;
    }

    public final DateTime getEndDate() {
        return endDate;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("percentage", percentage);
        jsonObject.put("startDate", startDate.toJSON());
        jsonObject.put("endDate", endDate.toJSON());
        return jsonObject;
    }

    public static Discount fromJSON(JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        Integer percentage = jsonObject.getInt("percentage");
        DateTime startDate = DateTime.fromJSON(jsonObject.getJSONObject("startDate"));
        DateTime endDate = DateTime.fromJSON(jsonObject.getJSONObject("endDate"));
        return new Discount(id, percentage, startDate, endDate);
    }
}
