package com.example.cardealer.domain.dtos.CustomerDTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CustomerSalesDTO {
    private String fullName;
    private long boughtCars;
    private BigDecimal spentMoney;

    public CustomerSalesDTO(String fullName, long boughtCars, BigDecimal spentMoney) {
        this.fullName = fullName;
        this.boughtCars = boughtCars;
        this.spentMoney = spentMoney;
    }
}
