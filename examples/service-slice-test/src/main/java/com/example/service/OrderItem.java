package com.example.service;

import java.math.BigDecimal;

public class OrderItem {
    private String product;
    private BigDecimal unitPrice;
    private int quantity;

    public OrderItem() {}

    public OrderItem(String product, BigDecimal unitPrice, int quantity) {
        this.product = product;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
