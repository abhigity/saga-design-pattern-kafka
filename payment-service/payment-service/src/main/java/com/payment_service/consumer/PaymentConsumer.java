package com.payment_service.consumer;

import com.common.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Component
public class PaymentConsumer {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    // ✅ Step 1: Listen from ORDER SERVICE
    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void processPayment(OrderEvent event) {

//        if (event.getProduct().equalsIgnoreCase("phone")) {
//            event.setStatus("PAYMENT_FAILED");
//            kafkaTemplate.send("order-topic", event);
//            return;
//        }
//        event.setStatus("PAYMENT_SUCCESS");

        if ("CREATED".equals(event.getStatus())) {

            System.out.println("💰 Processing Payment...");

            try {
                event.setStatus("PAYMENT_SUCCESS");
                System.out.println("💰 Payment Success...");
            } catch (Exception e) {
                event.setStatus("PAYMENT_FAILED");
                System.out.println("💰 Payment Failed...");
            }

            // ✅ Send to INVENTORY SERVICE
            kafkaTemplate.send("payment-topic", event);

        }
    }

    // ✅ Step 2: Handle INVENTORY FAILURE (Compensation)
    @KafkaListener(topics = "inventory-topic", groupId = "payment-group")
    public void handleRefund(OrderEvent event) {

        if ("INVENTORY_FAILED".equals(event.getStatus())) {

            System.out.println("Received for refund: " + event);
            System.out.println("💸 Refunding payment...");
            event.setStatus("REFUND_DONE");

            // ✅ Send final result to ORDER SERVICE
            kafkaTemplate.send("order-topic", event);
        }
    }
}