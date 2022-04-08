package edu.nus.iss.sg.vtt_day24.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.nus.iss.sg.vtt_day24.exceptions.RecordNotFoundException;
import edu.nus.iss.sg.vtt_day24.models.LineItem;
import edu.nus.iss.sg.vtt_day24.models.PurchaseOrder;
import edu.nus.iss.sg.vtt_day24.models.Sku;
import edu.nus.iss.sg.vtt_day24.services.PurchaseOrderService;

@Controller
@RequestMapping(path="")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService poSvc;

    @GetMapping("")
    public ModelAndView showForm() {
        final ModelAndView mav = new ModelAndView();

        try{
            List<Sku> skus = poSvc.getAllSkus();

            mav.addObject("skus", poSvc.getAllSkus());
            mav.setStatus(HttpStatus.OK);
            mav.addObject("skus", skus);
            mav.setViewName("index");

        }catch(RecordNotFoundException ex) {
            mav.setStatus(HttpStatus.NOT_FOUND);
            mav.addObject("errorMsg", ex.getMessage());
            mav.setViewName("error");
        }

        return mav;
    }
    
    @PostMapping("/postPO")
    public ModelAndView postPO(@RequestBody MultiValueMap<String, String> form) {
        final Optional<PurchaseOrder> po = poSvc.createPO(form);
        final ModelAndView mav = new ModelAndView();

        if(po.isEmpty()) {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.setViewName("error");
            return mav; 
        }
        
        Optional<List<LineItem>> lineItemsOpt = poSvc.createLineItems(form, po.get().getOrderId());

        if(lineItemsOpt.isEmpty()) {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.setViewName("error");
            return mav; 
        }

        mav.setStatus(HttpStatus.OK);
        mav.setViewName("purchaseOrder");
        return mav;
    }
}
