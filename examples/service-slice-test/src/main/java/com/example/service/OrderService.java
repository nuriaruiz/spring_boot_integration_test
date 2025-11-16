package com.example.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public BigDecimal calculateTotal(List<OrderItem> items) {
        Objects.requireNonNull(items, "items must not be null");
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            if (item == null || item.getProduct() == null) {
                continue;
            }
            BigDecimal unitPrice = repository.findUnitPrice(item.getProduct());
            if (unitPrice == null) {
                throw new IllegalArgumentException("Unknown product: " + item.getProduct());
            }
            int qty = Math.max(0, item.getQuantity());
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(qty)));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
