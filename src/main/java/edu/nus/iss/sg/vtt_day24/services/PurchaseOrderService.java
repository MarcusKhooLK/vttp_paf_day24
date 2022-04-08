package edu.nus.iss.sg.vtt_day24.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import edu.nus.iss.sg.vtt_day24.exceptions.RecordNotFoundException;
import edu.nus.iss.sg.vtt_day24.models.LineItem;
import edu.nus.iss.sg.vtt_day24.models.PurchaseOrder;
import edu.nus.iss.sg.vtt_day24.models.Sku;
import edu.nus.iss.sg.vtt_day24.repository.PurchaseOrderRepo;

@Service
public class PurchaseOrderService {
    
    @Autowired
    private PurchaseOrderRepo repo;

    public List<Sku> getAllSkus(){
        List<Sku> skus = repo.getAllSkus();
        if(skus.isEmpty())
            throw new RecordNotFoundException("No SKUs in database");
        return skus;
    }

    public Optional<PurchaseOrder> createPO(final MultiValueMap<String, String> form) {

        PurchaseOrder po = new PurchaseOrder();

        do{
            po = PurchaseOrder.create(form.getFirst("name"), Date.valueOf(form.getFirst("date")));
        }while(repo.getPOByID(po.getOrderId()));

        boolean result = repo.createPO(po);

        if(!result) {
            return Optional.empty();
        }
        
        return Optional.of(po);
    }
    
    public Optional<List<LineItem>> createLineItems(final MultiValueMap<String, String> form, String orderId) {

        List<Sku> skus = getAllSkus();
        List<LineItem> lineItems = new ArrayList<>();

        for(int i = 0; i < skus.size(); i++) {
            Integer skuId = skus.get(i).getSkuId();
            String quantityStr = form.getFirst(String.valueOf(skuId));
            Integer qty = Integer.parseInt(quantityStr);
            // skip line item if qty is 0
            if(qty == 0)
                continue;
            lineItems.add(LineItem.create(qty, orderId, skuId));
        }

        if(lineItems.isEmpty())
            return Optional.empty();

        repo.createLineItems(lineItems);

        return Optional.of(lineItems);
    }
}
