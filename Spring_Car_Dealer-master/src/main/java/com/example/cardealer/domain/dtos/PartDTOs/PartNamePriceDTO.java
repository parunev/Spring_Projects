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
public class PartNamePriceDTO {
    private String Name;
    private BigDecimal Price;
}
