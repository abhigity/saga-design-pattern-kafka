package com.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "payment-service" , url = "http://localhost:8083") // Specify the URL of the payment service
public interface PaymentClient {

    @GetMapping("/payment/pay/{orderId}")
    String makePayment(@PathVariable Long orderId);

    @GetMapping("/payment/refund/{orderId}")
    String refundPayment(@PathVariable Long orderId);

}
