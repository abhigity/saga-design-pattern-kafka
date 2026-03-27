package com.order_service.controller;

import com.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderservice;

        // Endpoint to create an order
    //localhost:8081/orders/create
    @GetMapping("/create")
    public String createOrder() {
        return orderservice.placeOrder();
    }

}
