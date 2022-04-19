package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

import java.util.Date;

public class OnlineInvoice implements Resource {

    private final Integer id;
    private final OnlineOrder idOrder;
    private final String transactionId;
    private final PaymentMethodOnlineEnum paymentType;
    private final double totalPrice;

    public OnlineInvoice(Integer id, OnlineOrder idOrder, String transactionId, PaymentMethodOnlineEnum paymentType, double totalPrice) {
        this.id = id;
        this.idOrder = idOrder;
        this.transactionId = transactionId;
        this.paymentType = paymentType;
        this.totalPrice = totalPrice;
    }

    public final int getId() { return id; }

    public final OnlineOrder getIdOrder() { return idOrder; }

    public final String getTransactionId() { return transactionId; }

    public PaymentMethodOnlineEnum getPaymentType() { return paymentType; }

    public final double getTotalPrice() { return totalPrice; }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("idOrder", idOrder.toJSON());
        jsonObject.put("transactionId", transactionId);
        jsonObject.put("paymentType", paymentType);
        jsonObject.put("totalPrice", totalPrice);
        return jsonObject;
    }

    public static OnlineInvoice fromJSON(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        OnlineOrder idOrder = OnlineOrder.fromJSON(jsonObject.getJSONObject("idOrder"));
        String transactionId = jsonObject.getString("transactionId");
        PaymentMethodOnlineEnum paymentType = PaymentMethodOnlineEnum.fromString(jsonObject.getString("paymentType"));
        DateTime oiDate = DateTime.fromJSON(jsonObject.getJSONObject("oiDate"));
        double totalPrice = jsonObject.getDouble("totalPrice");
        return new OnlineInvoice(id, idOrder, transactionId, paymentType, totalPrice);
    }
}
