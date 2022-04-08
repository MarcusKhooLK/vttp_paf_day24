package edu.nus.iss.sg.vtt_day24.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Sku {
    private Integer skuId;
    private String description;
    private Float unitPrice;
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Float getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public static Sku create(final SqlRowSet result) {
        Sku s = new Sku();
        s.setDescription(result.getString("description"));
        s.setSkuId(result.getInt("prod_id"));
        s.setUnitPrice(result.getFloat("unit_price"));
        return s;
    }
}
