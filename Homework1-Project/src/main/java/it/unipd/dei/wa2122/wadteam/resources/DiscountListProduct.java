package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiscountListProduct implements Resource{
    final Discount discount;
    final List<Product> productList;

    public DiscountListProduct(Discount discount, List<Product> productList) {
        this.discount = discount;
        this.productList = productList;
    }

    public Discount getDiscount() {
        return discount;
    }

    public List<Product> getProductList() {
        return productList;
    }

    /**
     * Returns a JSON representation of the {@code Resource} into the given {@code OutputStream}.
     */
    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("discount", discount.toJSON());
        jsonObject.put("productList", productList.stream().map(Product::toJSON).toArray());

        return jsonObject;
    }

    public static DiscountListProduct fromJSON(JSONObject jsonObject) {

        Discount discount = Discount.fromJSON(jsonObject.getJSONObject("discount"));
        List<Product> productList = new ArrayList<>();

        for(var item : jsonObject.getJSONArray("productList")) {
            productList.add(Product.fromJSON((JSONObject) item));
        }
        return new DiscountListProduct(discount, productList);
    }
}
