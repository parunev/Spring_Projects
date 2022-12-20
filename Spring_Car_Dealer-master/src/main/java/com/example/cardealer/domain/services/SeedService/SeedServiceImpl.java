package com.example.cardealer.domain.services.SeedService;

import com.example.cardealer.domain.entities.Car;
import com.example.cardealer.domain.dtos.CarDTOs.CarSeedDTO;
import com.example.cardealer.domain.entities.Customer;
import com.example.cardealer.domain.dtos.CustomerDTOs.CustomerSeedDTO;
import com.example.cardealer.domain.entities.Part;
import com.example.cardealer.domain.dtos.PartDTOs.PartSeedDTO;
import com.example.cardealer.domain.entities.Sale;
import com.example.cardealer.domain.entities.Supplier;
import com.example.cardealer.domain.dtos.SupplierDTOs.SupplierSeedDTO;
import com.example.cardealer.domain.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static com.example.cardealer.utils.Constants.*;
import static com.example.cardealer.utils.Functions.GSON;
import static com.example.cardealer.utils.Functions.MODEL_MAPPER;

@AllArgsConstructor
@Service
public class SeedServiceImpl implements SeedService {
    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final SaleRepository saleRepository;

    @Override
    public void seedSuppliers() throws FileNotFoundException {
        Arrays.stream(GSON.fromJson(new FileReader(SUPPLIERS_PATH), SupplierSeedDTO[].class))
                .map(e -> MODEL_MAPPER.map(e, Supplier.class)).forEach(this.supplierRepository::save);
    }

    // When importing the parts, set to each part a random supplier from the already imported suppliers.
    @Override
    public void seedParts() throws FileNotFoundException {
        Arrays.stream(GSON.fromJson(new FileReader(PARTS_PATH), PartSeedDTO[].class))
                .map(e -> MODEL_MAPPER.map(e, Part.class)).forEach(p -> {
                    setRandomSupplier(p); this.partRepository.save(p); });
    }

    // When importing the cars add between 3 and 5 random parts to each car.
    @Override
    public void seedCars() throws FileNotFoundException {
        Arrays.stream(GSON.fromJson(new FileReader(CARS_PATH), CarSeedDTO[].class))
                .map(e -> MODEL_MAPPER.map(e, Car.class)).forEach(c -> {
                    setRandomParts(c); this.carRepository.save(c); });
    }

    @Override
    public void seedCustomers() throws FileNotFoundException {
        Arrays.stream(GSON.fromJson(new FileReader(CUSTOMERS_PATH), CustomerSeedDTO[].class))
                .map(e -> MODEL_MAPPER.map(e, Customer.class)).forEach(this.customerRepository::save);
    }

    // Import the sales records by randomly selecting a car,
    // customer and the amount of discount to be applied (discounts can be 0%, 5%, 10%, 15%, 20%, 30%, 40% or 50%).
    @Override
    public void seedSales() {
        long customerCount = this.customerRepository.count();
        int discountCount = DISCOUNTS.length;

        for (int i = 0; i < 60; i++) {
            Car car = this.carRepository.getById((long) i + 1);

            long customerId = new Random().nextLong(customerCount) + 1;
            Customer customer = this.customerRepository.getById(customerId);

            int discount = DISCOUNTS[new Random().nextInt(discountCount)];

            if (customer.isYoungDriver()) {
                discount -= 5;
            }

            if (discount < 0) {
                discount = 0;
            }

            this.saleRepository.save(new Sale(car, customer, discount));
        }
    }

    private void setRandomParts(Car c) {
        long partsCount = this.partRepository.count();

        int neededPartsCount = new Random().nextInt(2) + 3;

        Set<Part> partsToAdd = new HashSet<>();

        while (partsToAdd.size() < neededPartsCount) {
            long partId = new Random().nextLong(partsCount) + 1;
            Part part = this.partRepository.getById(partId);
            partsToAdd.add(part);
        }

        c.setParts(partsToAdd);
    }

    private void setRandomSupplier(Part p) {
        long suppliersCount = this.supplierRepository.count();
        long supplierId = new Random().nextLong(suppliersCount) + 1;
        Supplier supplier = this.supplierRepository.getById(supplierId);
        p.setSupplier(supplier);
    }
}
