package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Contains implements Resource {
    private final OnlineOrder idOrder;
    private final Product productAlias;
    private final int quantity;
    private final double priceApplied;

    public Contains(OnlineOrder idOrder, Product productAlias, int quantity, double priceApplied) {
        this.idOrder = idOrder;
        this.productAlias = productAlias;
        this.quantity = quantity;
        this.priceApplied = priceApplied;
    }

    public final OnlineOrder getIdOrder() { return idOrder; }

    public final Product getProductAlias() { return productAlias; }

    public final int getQuantity() { return quantity; }

    public final double getPriceApplied() { return priceApplied; }

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
        OnlineOrder idOrder = OnlineOrder.fromJson(jsonObject.getJSONObject("idOrder"));
        Product productAlias = Product.fromJson(jsonObject.getJSONObject("productAlias"));
        int quantity = jsonObject.getInt("quantity");
        double priceApplied = jsonObject.getDouble("priceApplied");
        return new Contains(idOrder, productAlias, quantity, priceApplied);
    }
}
