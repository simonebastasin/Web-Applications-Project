package it.unipd.dei.wa2122.wadteam.resources;


//Todo: Add Owns list
public class Owns {
    private Discount discount;
    private Product product;



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
}


