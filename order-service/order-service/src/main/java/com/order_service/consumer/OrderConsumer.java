package com.order_service.consumer;

import com.common.dto.OrderEvent;
import com.order_service.entity.Order;
import com.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OrderConsumer {

    @Autowired
    private OrderRepository orderRepository;

    //consumer
    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void finalStatus(OrderEvent event) {

        Order order = orderRepository.findById(event.getOrderId()).orElse(null);

        if (order != null) {
            order.setStatus(event.getStatus());
            orderRepository.save(order);
        }

        System.out.println("Order Event Received: " + event);

        if ("COMPLETED".equals(event.getStatus())) {
            System.out.println("✅ Order Completed");
        }

        if ("REFUND_DONE".equals(event.getStatus())) {
            System.out.println("❌ Order Cancelled + Payment Refunded");
        }
    }
}
