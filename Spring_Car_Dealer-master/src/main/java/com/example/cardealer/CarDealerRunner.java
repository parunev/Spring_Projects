package com.example.cardealer;

import com.example.cardealer.domain.services.ConverterService.ConverterService;
import com.example.cardealer.domain.services.SeedService.SeedService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Component
public class CarDealerRunner implements CommandLineRunner {

    private SeedService seedService;
    private ConverterService converterService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        // Database design and implementation - works

        // Seeding the suppliers - works
        // this.seedService.seedSuppliers();

        // Seeding the parts - works
        // this.seedService.seedParts();

        // Seeding the cars - works
        // this.seedService.seedCars();

        // Seeding the customers - works
        // this.seedService.seedCustomers();

        // Seeding the sales - works
        // this.seedService.seedSales();

        this.seedService.seedAll();
        // All imports work just fine

        // QUERY N1 - Ordered Customers - works
        // this.converterService.exportOrderedCustomers();

        // QUERY N2 - Cars from Make Toyota - works
        // this.converterService.exportCarsFromMakeToyota();

        // QUERY N3 - Local Suppliers - works
        // this.converterService.exportLocalSuppliers();

        // QUERY N4 - Cars with Their List of Parts - works
        // this.converterService.exportCarsWithTheirListOfParts();

        // QUERY N5 - Total Sales By Customer - works
        // this.converterService.exportCustomerTotalSales();

        // QUERY N6 - Sales with Applied Discount - works
        // this.converterService.exportCustomerTotalSalesAndDiscount();

        this.converterService.exportAll();
        //all exports work just fine
    }
}
