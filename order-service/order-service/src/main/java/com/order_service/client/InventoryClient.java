package com.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "inventory-service" , url = "http://localhost:8082") // Name of the inventory service registered in Eureka
public interface InventoryClient {

    @GetMapping("/inventory/reserve/{product}")
    String reserveInventory(@PathVariable String product);
}

