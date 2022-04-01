package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;
import java.util.List;

public class Product implements Resource {

    private final String alias;
    private final String name;
    private final String brand;
    private final String description;
    private final double purchase;
    private final double sale;
    private final int quantity;
    private final ProductCategory category;
    private final boolean evidence;
    private final List<Integer> pictures;   // This is a list of ID_Medias

    public Product(String alias, String name, String brand, String description, int quantity, double purchase, double sale, ProductCategory category, boolean evidence, List<Integer> pictures)
    {
        this.alias = alias;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.quantity = quantity;
        this.purchase = purchase;
        this.sale = sale;
        this.category = category;
        this.evidence = evidence;
        this.pictures = pictures;
    }

    public final String getAlias() { return alias; }

    public final String getName() { return name; }

    public final String getBrand() { return brand; }

    public final String getDescription() { return  description; }

    public final double getPurchase() { return purchase; }

    public final double getSale() { return sale; }

    public final int getQuantity() { return quantity; }

    public final ProductCategory getCategory() { return category; }

    public final boolean getEvidence() { return evidence; }

    public final List<Integer> getPictures() { return pictures; }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("alias", alias);
        jsonObject.put("name", name);
        jsonObject.put("brand", brand);
        if(description != null)
            jsonObject.put("description", description);
        jsonObject.put("purchase", purchase);
        jsonObject.put("sale", sale);
        jsonObject.put("quantity", quantity);
        jsonObject.put("category", category);
        jsonObject.put("evidence", evidence);
        if(pictures != null)
            jsonObject.put("pictures", pictures);

        return jsonObject;
    }

    public static Product fromJson(JSONObject jsonObject) {
        String alias = jsonObject.getString("alias");
        String name = jsonObject.getString("name");
        String brand = jsonObject.getString("brand");
        String description = null;
        if(jsonObject.has("description"))
            description = jsonObject.getString("description");
        double purchase = jsonObject.getDouble("purchase");
        double sale = jsonObject.getDouble("sale");
        int quantity = jsonObject.getInt("quantity");
        List<Integer> pictures = null;
        if(jsonObject.has("picture"))
            pictures = (List<Integer>) jsonObject.get("pictures");
        ProductCategory category = ProductCategory.fromJson(jsonObject.getJSONObject("category"));
        boolean evidence = jsonObject.getBoolean("evidence");

        return new Product(alias, name, brand, description, quantity, purchase, sale, category, evidence, pictures);
    }
}
