package com.example.cardealer.domain.dtos.CarDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarExportDTO {
    private String make;
    private String model;
    private Long travelledDistance;
}
