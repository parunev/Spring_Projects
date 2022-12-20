package com.example.cardealer.domain.services.SupplierService;

import com.example.cardealer.domain.dtos.SupplierDTOs.SupplierIdNamePCountDTO;
import com.example.cardealer.domain.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SupplierServiceImpl implements SupplierService {

    private SupplierRepository supplierRepository;

    @Override
    public List<SupplierIdNamePCountDTO> getLocalSuppliers() {
        return this.supplierRepository.findByIsImporterFalse();
    }
}
