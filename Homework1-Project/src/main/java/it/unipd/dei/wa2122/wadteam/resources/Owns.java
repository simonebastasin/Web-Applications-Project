package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Owns implements Resource{
    private final Integer ID_Discount;
    private final String Product_Alias;

    public Owns(Integer ID_Discount, String Product_Alias) {
        this.ID_Discount = ID_Discount;
        this.Product_Alias = Product_Alias;
    }

    public Integer getDiscount() {
        return ID_Discount;
    }

    public String getProduct() {
        return Product_Alias;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID_Discount", ID_Discount);
        jsonObject.put("Product_Alias", Product_Alias);
        return jsonObject;
    }

    public static Owns fromJson(JSONObject jsonObject) {
        String product = jsonObject.getString("Product_Alias");
        Integer discount  = jsonObject.getInt("ID_Discount");
        return new Owns(discount, product);
    }
}


