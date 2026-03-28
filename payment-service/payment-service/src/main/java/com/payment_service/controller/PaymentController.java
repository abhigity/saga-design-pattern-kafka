package com.payment_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    //using in feign client in order service

    @GetMapping("/pay/{orderId}")
    public String makePayment(@PathVariable Long orderId) {
        return "Payment successful for Order ID: " + orderId;
    }

    @GetMapping("/refund/{orderId}")
    public String refundPayment(@PathVariable Long orderId) {
        return "Payment refunded for Order ID: " + orderId;
    }
}
