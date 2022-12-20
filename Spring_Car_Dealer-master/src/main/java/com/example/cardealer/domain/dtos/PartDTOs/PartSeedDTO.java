package com.example.cardealer.domain.dtos.PartDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartSeedDTO {
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
