package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class OnlineInvoice implements Resource {

    private final int id;
    private final OnlineOrder idOrder;
    private final String transactionId;
    private final PaymentMethodOnlineEnum paymentType;
    private final String oiDate; //TODO: DATE type (DEFAULT CURRENT_DATE)
    private final double totalPrice;

    public OnlineInvoice(int id, OnlineOrder idOrder, String transactionId, PaymentMethodOnlineEnum paymentType, String oiDate, double totalPrice) {
        this.id = id;
        this.idOrder = idOrder;
        this.transactionId = transactionId;
        this.paymentType = paymentType;
        this.oiDate = oiDate;
        this.totalPrice = totalPrice;
    }

    public final int getId() { return id; }

    public final OnlineOrder getIdOrder() { return idOrder; }

    public final String getTransactionId() { return transactionId; }

    public PaymentMethodOnlineEnum getPaymentType() { return paymentType; }

    public final String getOiDate() { return oiDate; }

    public final double getTotalPrice() { return totalPrice; }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("idOrder", idOrder);
        jsonObject.put("transactionId", transactionId);
        jsonObject.put("paymentType", paymentType);
        jsonObject.put("oiDate", oiDate);
        jsonObject.put("totalPrice", totalPrice);
        return jsonObject;
    }

    public static OnlineInvoice fromJSON(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        OnlineOrder idOrder = OnlineOrder.fromJSON(jsonObject.getJSONObject("idOrder"));
        String transactionId = jsonObject.getString("transactionId");
        PaymentMethodOnlineEnum paymentType = PaymentMethodOnlineEnum.valueOf(jsonObject.getString("paymentType"));
        String oiDate = jsonObject.getString("oiDate");
        double totalPrice = jsonObject.getDouble("totalPrice");
        return new OnlineInvoice(id, idOrder, transactionId, paymentType, oiDate, totalPrice);
    }
}
