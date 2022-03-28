package it.unipd.dei.wa2122.wadteam.resources;

import java.util.Date;

import org.json.JSONObject;

public class Discount implements Resource {
    private final Integer id;
    private final Integer percentage;
    private final String startDate;//TODO : convert to Date
    private final String endDate;//TODO : convert to Date

    public Discount(Integer id, Integer percentage, String startDate, String endDate) {
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

    public final String getStartDate() {
        return startDate;
    }

    public final String getEndDate() {
        return endDate;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("percentage", percentage);
        jsonObject.put("startDate", startDate);
        jsonObject.put("endDate", endDate);
        return jsonObject;
    }

    public static Discount fromJson(JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        Integer percentage = jsonObject.getInt("percentage");
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        //TODO getDate JSON
        /*
        String dateStr = jsonObject.getString("startDate");
        Si sdf = new Date("yyyy-MM-dd HH:mm:ss");
        Date birthDate = sdf.parse(dateStr);
         */
        return new Discount(id, percentage, startDate, endDate);
    }
}
