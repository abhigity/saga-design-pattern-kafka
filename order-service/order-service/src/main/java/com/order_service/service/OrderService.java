package com.order_service.service;

import com.order_service.client.InventoryClient;
import com.order_service.client.PaymentClient;

import com.common.dto.OrderEvent;
import com.order_service.entity.Order;
import com.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    private OrderRepository orderRepository;


    public String placeOrder() {

        Long orderId = System.currentTimeMillis(); // unique ID

        //"Samsung S26 Ultra"
        String product = "laptop";

        // ✅ Save initial order
        Order order = new Order();
        order.setOrderId(orderId);
        order.setProduct(product);
        order.setStatus("CREATED");

        orderRepository.save(order);

        // ✅ Send event to Kafka
        OrderEvent event = new OrderEvent();
        event.setOrderId(orderId);
        event.setProduct(product);
        event.setStatus("CREATED");

        kafkaTemplate.send("order-topic", event);

        return "Order Created with ID: " + orderId;
    }



/*
// using Kafka(without DB) (Asynchronous) - Recommended for Saga pattern
   @Autowired
    private KafkaTemplate kafkaTemplate;

    //producer
    public String placeOrder() {
        OrderEvent event = new OrderEvent(101L, "laptop", "CREATED");

        kafkaTemplate.send("order-topic", event);

        return event.getProduct()+ " Order Created";
    }

*/




/*

//    using Feign Clients (Synchronous) - Not recommended for Saga pattern

//    @Autowired
//    private PaymentClient paymentClient;

//    @Autowired
//    private InventoryClient inventoryClient;

    public String placeOrder() {
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
