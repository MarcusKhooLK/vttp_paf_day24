package edu.nus.iss.sg.vtt_day24.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import edu.nus.iss.sg.vtt_day24.models.LineItem;
import edu.nus.iss.sg.vtt_day24.models.PurchaseOrder;
import edu.nus.iss.sg.vtt_day24.models.Sku;

@Repository
public class PurchaseOrderRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Sku> getAllSkus() {
        final SqlRowSet result = jdbcTemplate.queryForRowSet(Queries.SQL_GET_ALL_SKUS);

        List<Sku> skus = new ArrayList<>();
        while(result.next()) {
            skus.add(Sku.create(result));
        }
        return skus;
    }

    public boolean createPO(final PurchaseOrder po) {
        int result = jdbcTemplate.update(Queries.SQL_CREATE_PO, po.getOrderId(), po.getName(), po.getOrderDate());
        return result > 0;
    }

    public boolean getPOByID(final String poId) {
        final SqlRowSet result = jdbcTemplate.queryForRowSet(Queries.SQL_GET_PO_BY_PO_ID, poId);
        return result.next();
    }

    public boolean createLineItems(final List<LineItem> lineItems) {
        List<Object[]> params = lineItems.stream()
            .map(item -> new Object[]{ item.getQuantity(), item.getOrderId(), item.getSkuId()})
            .collect(Collectors.toList());

        int added[] = jdbcTemplate.batchUpdate(Queries.SQL_CREATE_LINE_ITEMS, params);

        for(int result: added) {
            if(result <= 0)
                return false;
        }

        return true;
    }
}
