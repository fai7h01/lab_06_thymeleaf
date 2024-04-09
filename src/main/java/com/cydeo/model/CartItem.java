package com.cydeo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class CartItem {

    private Product product;
    private Integer quantity;
    private BigDecimal totalAmount;
}