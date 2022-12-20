package com.example.cardealer.domain.dtos.CarDTOs;

import com.example.cardealer.domain.dtos.PartDTOs.PartNamePriceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarAndPartsDTO {
    private String Make;
    private String Model;
    private Long TravelledDistance;
    private Set<PartNamePriceDTO> parts;
}
