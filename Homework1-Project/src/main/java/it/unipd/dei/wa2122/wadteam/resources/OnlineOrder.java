package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnlineOrder implements Resource{

    private final Integer idOrder;
    private final Integer idCustomer;
    private final DateTime ooDateTime;
    private final List<Product> products;
    private final OrderStatus status;

    public OnlineOrder(Integer idOrder, Integer idCustomer, DateTime ooDateTime, List<Product> products, OrderStatus status) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.ooDateTime = ooDateTime;
        this.products = products;
        this.status = status;
    }

    public final Integer getIdOrder() {
        return idOrder;
    }

    public final Integer getIdCustomer() {
        return idCustomer;
    }

    public final DateTime getOoDateTime() {
        return ooDateTime;
    }

    public List<Product> getProducts() {
        return products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Double getTotalPrice() {
        double totalPrize = 0;
        for (Product product : products){
            totalPrize+=product.getSalePrice()*product.getQuantity();
        }
        return totalPrize;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        if(idOrder!=null){
            jsonObject.put("idOrder", idOrder);
        }
        if(idCustomer!=null){
            jsonObject.put("idCustomer", idCustomer);
        }
        if(ooDateTime!=null){
            jsonObject.put("ooDateTime", ooDateTime.toJSON());
        }
        if(products!=null){
            jsonObject.put("products", products.stream().map(Product::toJSON).toArray());
        }
        if(status!=null){
            jsonObject.put("status", status.toJSON());
        }
        return jsonObject;
    }

    public static OnlineOrder fromJSON(JSONObject jsonObject){
        Integer idOrder = jsonObject.getInt("idOrder");
        Integer idCustomer = jsonObject.getInt("idCustomer");
        DateTime ooDateTime = DateTime.fromJSON(jsonObject.getJSONObject("ooDateTime"));
        List<Product> products = new ArrayList<>();
        for(var item : jsonObject.getJSONArray("products")) {
            products.add(Product.fromJSON((JSONObject) item));
        }
        OrderStatus status = OrderStatus.fromJSON(jsonObject.getJSONObject("status"));
        return new OnlineOrder(idOrder,idCustomer,ooDateTime,products,status);
    }
}
