package com.example.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@Import(OrderService.class)
class OrderServiceSliceTest {

    @MockBean
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("Calcula total a partir de precios del repositorio mockeado")
    void calculateTotal_ok() {
        BDDMockito.given(orderRepository.findUnitPrice("A"))
                .willReturn(new BigDecimal("10.50"));
        BDDMockito.given(orderRepository.findUnitPrice("B"))
                .willReturn(new BigDecimal("2.00"));

        BigDecimal total = orderService.calculateTotal(List.of(
                new OrderItem("A", null, 2),
                new OrderItem("B", null, 5)
        ));

        assertThat(total).isEqualTo(new BigDecimal("31.00"));
    }

    @Test
    @DisplayName("Lanza IllegalArgumentException para producto desconocido")
    void calculateTotal_unknownProduct() {
        BDDMockito.given(orderRepository.findUnitPrice("X"))
                .willReturn(null);

        assertThatThrownBy(() ->
                orderService.calculateTotal(List.of(new OrderItem("X", null, 1)))
        ).isInstanceOf(IllegalArgumentException.class)
         .hasMessageContaining("Unknown product: X");
    }
}
