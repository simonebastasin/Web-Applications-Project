package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssistanceTicket implements Resource {

    private final Integer id;
    private final String description;
    private final Integer idCustomer;
    private final String productAlias;
    private final List<TicketStatus> ticketStatusList;

    public AssistanceTicket(Integer id, String description, int idCustomer, String productAlias, List<TicketStatus> ticketStatusList) {
        this.id = id;
        this.description = description;
        this.idCustomer = idCustomer;
        this.productAlias = productAlias;
        this.ticketStatusList = ticketStatusList;
    }

    public final Integer getId() {
        return id;
    }

    public final String getDescription() {
        return description;
    }

    public final int getIdCustomer() {
        return idCustomer;
    }

    public final String getProductAlias() {
        return productAlias;
    }

    public final List<TicketStatus> getTicketStatusList() {
        return ticketStatusList;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        if(id!=null) {
            jsonObject.put("id", id);
        }
        if(description!=null) {
            jsonObject.put("description", description);
        }
        if(idCustomer!=null) {
            jsonObject.put("idCustomer", idCustomer);
        }
        if(productAlias!=null) {
            jsonObject.put("productAlias", productAlias);
        }
        if(ticketStatusList!=null) {
            jsonObject.put("ticketStatusList", ticketStatusList.stream().map(TicketStatus::toJSON).toArray());
        }
        return jsonObject;
    }

    public static AssistanceTicket fromJSON(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String description = jsonObject.getString("description");
        int idCustomer = jsonObject.getInt("idCustomer");
        String productAlias = jsonObject.getString("productAlias");
        List<TicketStatus> ticketStatusList = new ArrayList<>();
        for(var item : jsonObject.getJSONArray("ticketStatusList")) {
            ticketStatusList.add(TicketStatus.fromJSON((JSONObject) item));
        }
        return new AssistanceTicket(id, description, idCustomer, productAlias,ticketStatusList);
    }
}
