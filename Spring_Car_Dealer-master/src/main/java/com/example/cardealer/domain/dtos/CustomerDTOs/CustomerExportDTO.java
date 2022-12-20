package com.example.cardealer.domain.dtos.CustomerDTOs;

import com.example.cardealer.domain.dtos.SaleDTOs.SaleExportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerExportDTO {
    private Long Id;
    private String Name;
    private String BirthDate;
    private boolean IsYoungDriver;
    private Set<SaleExportDTO> Sales;
}
