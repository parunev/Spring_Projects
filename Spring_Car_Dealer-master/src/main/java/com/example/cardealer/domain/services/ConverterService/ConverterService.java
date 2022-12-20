package com.example.cardealer.domain.services.ConverterService;

import java.io.IOException;

public interface ConverterService {

    void exportOrderedCustomers() throws IOException;
    void exportCarsFromMakeToyota() throws IOException;
    void exportLocalSuppliers() throws IOException;
    void exportCarsWithTheirListOfParts() throws IOException;
    void exportCustomerTotalSales() throws IOException;
    void exportCustomerTotalSalesAndDiscount() throws IOException;

    default void exportAll() throws IOException {
        exportOrderedCustomers();
        exportCarsFromMakeToyota();
        exportLocalSuppliers();
        exportCarsWithTheirListOfParts();
        exportCustomerTotalSales();
        exportCustomerTotalSalesAndDiscount();
    }
}
