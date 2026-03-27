package com.common.dto;

public class OrderEvent {

    private Long orderId;
    private String product;
    private String status;

    // CREATED, PAYMENT_SUCCESS, PAYMENT_FAILED, COMPLETED

    public OrderEvent() {}

    public OrderEvent(Long orderId, String product, String status) {
        this.orderId = orderId;
        this.product = product;
        this.status = status;
    }

    // getters & setters


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}