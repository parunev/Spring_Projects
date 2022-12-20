package com.example.cardealer.domain.dtos.CustomerDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSeedDTO {
    private String name;
    private String birthDate;
    private boolean isYoungDriver;
}
