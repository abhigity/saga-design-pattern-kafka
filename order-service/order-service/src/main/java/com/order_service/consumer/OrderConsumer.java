package com.order_service.consumer;

import com.common.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OrderConsumer {

    //consumer
    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void finalStatus(OrderEvent event) {
        System.out.println("Order Event Received: " + event);
        if ("COMPLETED".equals(event.getStatus())) {
            System.out.println("✅ Order Completed");
        }

        if ("REFUND_DONE".equals(event.getStatus())) {
            System.out.println("❌ Order Cancelled + Payment Refunded");
        }
    }
}
