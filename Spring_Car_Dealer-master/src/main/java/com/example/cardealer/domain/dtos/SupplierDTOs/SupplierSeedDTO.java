package com.example.cardealer.domain.dtos.SupplierDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierSeedDTO {
    private String name;
    private boolean isImporter;
}
