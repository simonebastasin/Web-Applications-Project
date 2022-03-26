package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class AssistantTicket implements Resource {

    private final int id;
    private final String description;
    private final int idCostomer;
    private final String productAlias;

    public AssistantTicket(int id, String description, int idCostomer, String productAlias) {
        this.id = id;
        this.description = description;
        this.idCostomer = idCostomer;
        this.productAlias = productAlias;
    }

    public final int getId() {
        return id;
    }

    public final String getDescription() {
        return description;
    }

    public final int getIdCostomer() {
        return idCostomer;
    }

    public final String getProductAlias() {
        return productAlias;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("description",description);
        jsonObject.put("idCostomer",idCostomer);
        jsonObject.put("productAlias", productAlias);
        return jsonObject;
    }

    public static AssistantTicket fromJson(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String description = jsonObject.getString("description");
        int idCostomer = jsonObject.getInt("idCostumer");
        String productAlias = jsonObject.getString("productAlias");
        return new AssistantTicket(id, description, idCostomer, productAlias);
    }
}
