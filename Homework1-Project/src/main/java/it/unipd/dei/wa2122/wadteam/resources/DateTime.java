package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime implements Resource{
    private final LocalDateTime localDateTime;

    public DateTime(LocalDateTime modernJavaDateTime) {
        this.localDateTime = modernJavaDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String toString(String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public String toString() {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static DateTime fromString(String date, String pattern) {
        return new DateTime(LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern)));
    }

    public String getHumanDate() { return toString("dd/MM/yyyy HH:mm"); }
    public String getHumanDateTimeless() { return toString("dd/MM/yyyy"); }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("date", localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
        return jsonObject;
    }

    public static DateTime fromJSON(JSONObject jsonObject) {
        return new DateTime(LocalDateTime.parse( jsonObject.getString("date"), DateTimeFormatter.ISO_DATE_TIME));
    }
}
