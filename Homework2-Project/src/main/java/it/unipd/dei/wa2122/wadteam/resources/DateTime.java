package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    public String getInputDateFormat(){
        String aux = "";


        String day = "";
        String month = "";
        if(localDateTime.getDayOfMonth()<10) {
            day = "0" + localDateTime.getDayOfMonth();
        } else {
            day = ""+localDateTime.getDayOfMonth();
        }

        if(localDateTime.getMonthValue()<10) {
            month = "0" + localDateTime.getMonthValue();
        } else {
            month = ""+localDateTime.getMonthValue();
        }
        aux = localDateTime.getYear() + "-"+month + "-" + day;


        return aux;
    }


    private int getYear(){
        return localDateTime.getYear();
    }
    private int getMonthValue(){
        return localDateTime.getMonthValue();
    }
    private int getDayOfMonth(){
        return localDateTime.getDayOfMonth();
    }

    public int compareTo0(DateTime otherDate) {
        int cmp = (localDateTime.getYear() - otherDate.getYear());
        if (cmp == 0) {
            cmp = (localDateTime.getMonthValue() - otherDate.getMonthValue());
            if (cmp == 0) {
                cmp = (localDateTime.getDayOfMonth() - otherDate.getDayOfMonth());
            }
        }
        return cmp;
    }



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
