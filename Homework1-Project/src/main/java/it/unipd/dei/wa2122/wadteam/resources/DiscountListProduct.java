package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

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
        return null;
    }
}
