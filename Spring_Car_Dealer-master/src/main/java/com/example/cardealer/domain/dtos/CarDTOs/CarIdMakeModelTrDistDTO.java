package com.example.cardealer.domain.dtos.CarDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarIdMakeModelTrDistDTO {
    private Long Id;
    private String Make;
    private String Model;
    private Long TravelledDistance;
}
