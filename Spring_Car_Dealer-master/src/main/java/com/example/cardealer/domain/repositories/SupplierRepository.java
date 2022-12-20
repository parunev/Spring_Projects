package com.example.cardealer.domain.repositories;

import com.example.cardealer.domain.entities.Supplier;
import com.example.cardealer.domain.dtos.SupplierDTOs.SupplierIdNamePCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT new com.example.cardealer.domain.dtos.SupplierDTOs.SupplierIdNamePCountDTO(" +
            "s.id, s.name, size(p)) FROM Supplier s JOIN s.parts p WHERE s.isImporter = false")
    List<SupplierIdNamePCountDTO> findByIsImporterFalse();
}
