package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class OnlineOrder implements Resource{

    private final Integer idOrder;
    private final Integer idCustomer;
    private final String ooDateTime;

    public OnlineOrder(Integer idOrder, Integer idCustomer, String ooDateTime) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.ooDateTime = ooDateTime;
    }

    public final Integer getIdOrder() {
        return idOrder;
    }

    public final Integer getIdCustomer() {
        return idCustomer;
    }

    public final String getOoDateTime() {
        return ooDateTime;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idOrder", idOrder);
        jsonObject.put("idCustomer", idCustomer);
        jsonObject.put("ooDateTime", ooDateTime);
        return jsonObject;
    }

    public static OnlineOrder fromJson(JSONObject jsonObject){
        Integer idOrder = jsonObject.getInt("idOrder");
        Integer idCustomer = jsonObject.getInt("idCustomer");
        String ooDateTime = jsonObject.getString("ooDateTime");
        return new OnlineOrder(idOrder,idCustomer,ooDateTime);
    }
}
