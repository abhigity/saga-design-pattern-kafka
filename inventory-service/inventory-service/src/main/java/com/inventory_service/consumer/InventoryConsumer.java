package com.inventory_service.consumer;

import com.common.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class InventoryConsumer {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @KafkaListener(topics = "payment-topic", groupId = "inventory-group")
    public void reserveInventory(OrderEvent event) {


        if ("PAYMENT_SUCCESS".equals(event.getStatus())) {

            System.out.println("📦 Reserving Inventory...");

            try {
                if (event.getProduct().equalsIgnoreCase("laptop")) {
                    throw new RuntimeException("Inventory Failed");
                }

                System.out.println("✅ Inventory Reserved");
                event.setStatus("COMPLETED");

                // ✅ SUCCESS → go to Order
                kafkaTemplate.send("order-topic", event);

            } catch (Exception e) {
                System.out.println("❌ Inventory Failed");

                event.setStatus("INVENTORY_FAILED");

                // 🔥 FAILURE → go to Payment for REFUND
                kafkaTemplate.send("inventory-topic", event);
            }
        }
    }
}