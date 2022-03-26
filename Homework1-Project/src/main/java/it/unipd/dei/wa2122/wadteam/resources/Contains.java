package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Contains implements Resource {
    private final int idOrder; // TODO: FOREIGN KEY _ REFERENCES OnlineOrder(id)
    private final String productAlias; // TODO: FOREIGN KEY _ REFERENCES Product(alias)
    private final int quantity;
    private final int priceApplied;

    public Contains(int idOrder, String productAlias, int quantity, int priceApplied) {
        this.idOrder = idOrder;
        this.productAlias = productAlias;
        this.quantity = quantity;
        this.priceApplied = priceApplied;
    }

    public final int getIdOrder() { return idOrder; }

    public final String getProductAlias() { return productAlias; }

    public final int getQuantity() { return quantity; }

    public final int getPriceApplied() { return priceApplied; }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idOrder", idOrder);
        jsonObject.put("productAlias", productAlias);
        jsonObject.put("quantity", quantity);
        jsonObject.put("priceApplied", priceApplied);
        return jsonObject;
    }

    public static Contains fromJson(JSONObject jsonObject) {
        int idOrder = jsonObject.getInt("idOrder");
        String productAlias = jsonObject.getString("productAlias");
        int quantity = jsonObject.getInt("quantity");
        int priceApplied = jsonObject.getInt("priceApplied");
        return new Contains(idOrder, productAlias, quantity, priceApplied);
    }
}
