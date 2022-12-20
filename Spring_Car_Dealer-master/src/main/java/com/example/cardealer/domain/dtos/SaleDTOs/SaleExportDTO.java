package com.example.cardealer.domain.dtos.SaleDTOs;

import com.example.cardealer.domain.dtos.CarDTOs.CarExportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleExportDTO {
    private Long id;
    private int discountPercentage;
    private CarExportDTO car;
}
