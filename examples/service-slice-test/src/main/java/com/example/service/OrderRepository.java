package com.example.service;

import java.math.BigDecimal;

public interface OrderRepository {
    BigDecimal findUnitPrice(String productCode);
}
