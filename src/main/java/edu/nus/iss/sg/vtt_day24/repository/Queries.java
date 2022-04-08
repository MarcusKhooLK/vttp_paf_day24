package edu.nus.iss.sg.vtt_day24.repository;

public class Queries {
    public static final String SQL_GET_ALL_SKUS = "select * from sku;";
    public static final String SQL_CREATE_PO = "insert into purchaseorder (order_id, name, order_date) values (?, ?, ?);";
    public static final String SQL_GET_PO_BY_PO_ID = "select * from purchaseorder where order_id = ?";
    public static final String SQL_CREATE_LINE_ITEMS = "insert into lineitem (quantity, order_id, prod_id) values (?, ?, ?);";
}
