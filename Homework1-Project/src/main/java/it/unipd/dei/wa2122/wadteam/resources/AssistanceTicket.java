package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssistanceTicket implements Resource {

    private final int id;
    private final String description;
    private final int idCustomer;
    private final String productAlias;
    private final List<TicketStatus> ticketStatusList;

    public AssistanceTicket(int id, String description, int idCustomer, String productAlias, List<TicketStatus> ticketStatusList) {
        this.id = id;
        this.description = description;
        this.idCustomer = idCustomer;
        this.productAlias = productAlias;
        this.ticketStatusList = ticketStatusList;
    }

    public final int getId() {
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
        jsonObject.put("id",id);
        jsonObject.put("description",description);
        jsonObject.put("idCustomer",idCustomer);
        jsonObject.put("productAlias", productAlias);
        jsonObject.put("ticketStatusList",ticketStatusList.stream().map(TicketStatus::toJSON).toArray());
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
