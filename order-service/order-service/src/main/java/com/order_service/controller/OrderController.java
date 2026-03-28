package com.order_service.controller;

import com.order_service.entity.Order;
import com.order_service.repository.OrderRepository;
import com.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderservice;

    @Autowired
    private OrderRepository orderRepository;


    // Endpoint to create an order
    //localhost:8081/orders/create
    @GetMapping("/create")
    public String createOrder() {
        return orderservice.placeOrder();
    }


    // Endpoint to check order status
    //localhost:8081/orders/status/{id}
    @GetMapping("/status/{id}")
    public String getStatus(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(Order::getStatus)
                .orElse("Order Not Found");
    }
}
