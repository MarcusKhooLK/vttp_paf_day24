package edu.nus.iss.sg.vtt_day24.models;

import java.security.SecureRandom;
import java.sql.Date;

public class PurchaseOrder {
    private String orderId;
    private String name;
    private Date orderDate;
    private static SecureRandom random = new SecureRandom();

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public static PurchaseOrder create(String name, Date date) {
        PurchaseOrder po = new PurchaseOrder();
        po.setOrderId(generatePoId());
        po.setName(name);
        po.setOrderDate(date);
        return po;
    }

    private static String generatePoId() {
        StringBuilder builder = new StringBuilder();
        while(builder.length() < 8) {
            builder.append(Integer.toHexString(random.nextInt()));
        }

        return builder.toString();
    }
}
