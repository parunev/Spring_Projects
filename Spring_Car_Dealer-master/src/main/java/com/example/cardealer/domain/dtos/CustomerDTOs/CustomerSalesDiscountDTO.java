package com.example.cardealer.domain.dtos.CustomerDTOs;

import com.example.cardealer.domain.entities.Car;
import com.example.cardealer.domain.dtos.CarDTOs.CarExportDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerSalesDiscountDTO {
    private CarExportDTO car;
    private String customerName;
    private double discount;
    private BigDecimal price;
    private BigDecimal priceWithDiscount;

    public CustomerSalesDiscountDTO(Car car, String customerName, int discount, BigDecimal price, BigDecimal priceWithDiscount) {
        this.car = new CarExportDTO(car.getMake(), car.getModel(), car.getTravelledDistance());
        this.customerName = customerName;
        this.discount = discount / 100.00;
        this.price = price;
        this.priceWithDiscount = priceWithDiscount;
    }
}
