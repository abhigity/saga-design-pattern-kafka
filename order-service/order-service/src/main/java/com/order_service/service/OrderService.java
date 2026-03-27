package com.order_service.service;

import com.order_service.client.InventoryClient;
import com.order_service.client.PaymentClient;

import com.common.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

//    @Autowired
//    private PaymentClient paymentClient;

//    @Autowired
//    private InventoryClient inventoryClient;


    //producer
    public String placeOrder() {
        OrderEvent event = new OrderEvent(101L, "laptop", "CREATED");

        kafkaTemplate.send("order-topic", event);

        return event.getProduct()+ " Order Created";
    }



/*

    public String placeOrder1() {
        Long orderId = 101L;
        String product = "laptop";
        try {
            System.out.println("Order Created");

            // Step 1: Make payment
            String paymentResponse = paymentClient.makePayment(orderId);
            System.out.println(paymentResponse);

            // Step 2: Reserve inventory
            String inventoryResponse = inventoryClient.reserveInventory(product);
            System.out.println(inventoryResponse);

        } catch (Exception e) {
            // Handle exceptions (e.g., inventory reservation failure, payment failure)
            System.out.println("Saga failed: " + e.getMessage());

            String s = paymentClient.refundPayment(orderId);
            System.out.println(s);

            // Compensating action to refund payment
            return "Order Cancelled \n" + s;
        }

        return "Order placed successfully for order ID: " + orderId;
    }

*/

}
