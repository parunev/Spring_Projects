package com.example.cardealer.domain.services.SupplierService;

import com.example.cardealer.domain.dtos.SupplierDTOs.SupplierIdNamePCountDTO;

import java.util.List;

public interface SupplierService {

    List<SupplierIdNamePCountDTO> getLocalSuppliers();
}
