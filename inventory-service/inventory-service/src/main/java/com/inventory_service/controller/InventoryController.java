package com.inventory_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @GetMapping("/reserve/{product}")
    public String reserveInventory(@PathVariable String product) {

        if(product.toLowerCase().equals("laptop")) { //simulate failure for Laptop product
                throw new RuntimeException("Inventory Service Down");
        }

        return "Inventory reserved for : " + product;
    }

}
