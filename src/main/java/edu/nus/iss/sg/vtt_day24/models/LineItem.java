package edu.nus.iss.sg.vtt_day24.models;

public class LineItem {
    private Integer quantity;
    private String orderId;
    private Integer skuId;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public static LineItem create(Integer quantity, String orderId, Integer skuId) {
        LineItem item = new LineItem();
        item.setOrderId(orderId);
        item.setSkuId(skuId);
        item.setQuantity(quantity);
        return item;
    }
}
