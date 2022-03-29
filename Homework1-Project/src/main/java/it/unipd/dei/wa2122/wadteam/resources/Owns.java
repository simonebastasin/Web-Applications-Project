package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Owns implements Resource{
    private final Discount discount;
    private final Product product;

    public Owns(Discount discount, Product product) {
        this.discount = discount;
        this.product = product;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("discount", discount);
        jsonObject.put("product", product);
        return jsonObject;
    }

    public static Owns fromJson(JSONObject jsonObject) {
        Product product = Product.fromJson(jsonObject.getJSONObject("productAlias"));
        Discount discount  = Discount.fromJson(jsonObject.getJSONObject("id"));
        return new Owns(discount, product);
    }
}


