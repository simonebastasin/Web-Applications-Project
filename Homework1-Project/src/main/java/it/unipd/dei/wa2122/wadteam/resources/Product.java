package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Product implements Resource {

    private final String alias;
    private final String name;
    private final String brand;
    private final String description;
    private final Double purchasePrice;
    private final Double salePrice;
    private final int quantity;
    private final ProductCategory category;
    private final boolean evidence;
    private final List<Integer> pictures;   // This is a list of ID_Medias
    private final Discount discount;

    public Product(String alias, String name, String brand, String description, int quantity, Double purchasePrice, Double salePrice, ProductCategory category, boolean evidence, List<Integer> pictures, Discount discount)
    {
        this.alias = alias;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.category = category;
        this.evidence = evidence;
        this.pictures = pictures;
        this.discount = discount;
    }

    public final String getAlias() { return alias; }

    public final String getName() { return name; }

    public final String getBrand() { return brand; }

    public final String getDescription() { return  description; }

    public final Double getPurchasePrice() { return purchasePrice; }

    public final Double getSalePrice() { return salePrice; }

    public final int getQuantity() { return quantity; }

    public final Double getDiscountSale() {
        if(discount == null) return  null;
        return (salePrice - (salePrice)/100.0* discount.getPercentage());
    }

    public final Double getFinalSalePrice() {
        if(discount == null) return  salePrice;
        return (salePrice - (salePrice)/100.0* discount.getPercentage());
    }

    public final ProductCategory getCategory() { return category; }

    public final boolean getEvidence() { return evidence; }

    public final List<Integer> getPictures() { return pictures; }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("alias", alias);
        jsonObject.put("name", name);
        jsonObject.put("brand", brand);
        if(description != null)
            jsonObject.put("description", description);
        if(purchasePrice != null)
            jsonObject.put("purchase", purchasePrice);
        if(salePrice != null)
            jsonObject.put("sale", salePrice);
        jsonObject.put("quantity", quantity);
        if(category != null)
            jsonObject.put("category", category.toJSON());
        jsonObject.put("evidence", evidence);
        if(pictures != null)
            jsonObject.put("pictures", pictures);
        if(discount != null)
            jsonObject.put("discount", discount);
        return jsonObject;
    }

    public static Product fromJSON(JSONObject jsonObject) {
        String alias = jsonObject.getString("alias");
        String name = jsonObject.getString("name");
        String brand = jsonObject.getString("brand");
        String description = jsonObject.has("description") ?   jsonObject.getString("description") : null;
        double purchase = jsonObject.getDouble("purchase");
        double sale = jsonObject.getDouble("sale");
        int quantity = jsonObject.getInt("quantity");
        List<Integer> pictures = new ArrayList<>();
        if(jsonObject.has("picture")){
            for(var item :  jsonObject.getJSONArray("pictures")) {
                pictures.add((Integer) item);
            }
        }
        ProductCategory category =jsonObject.has("category") ? ProductCategory.fromJSON(jsonObject.getJSONObject("category")) : null;
        boolean evidence = jsonObject.getBoolean("evidence");
        Discount discount = Discount.fromJSON(jsonObject.getJSONObject("discount"));

        return new Product(alias, name, brand, description, quantity, purchase, sale, category, evidence, pictures, discount);
    }

    public Discount getDiscount() {
        return discount;
    }
}
