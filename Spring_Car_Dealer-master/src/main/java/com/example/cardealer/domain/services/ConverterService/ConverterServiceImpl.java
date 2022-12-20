package com.example.cardealer.domain.services.ConverterService;

import com.example.cardealer.domain.dtos.CarDTOs.CarIdMakeModelTrDistDTO;
import com.example.cardealer.domain.dtos.CustomerDTOs.CustomerExportDTO;
import com.example.cardealer.domain.dtos.SupplierDTOs.SupplierIdNamePCountDTO;
import com.example.cardealer.domain.services.CarService.CarService;
import com.example.cardealer.domain.services.CustomerService.CustomerService;
import com.example.cardealer.domain.services.SupplierService.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.cardealer.utils.Constants.*;
import static com.example.cardealer.utils.Functions.*;

@AllArgsConstructor
@Service
public class ConverterServiceImpl implements ConverterService {

    private final CustomerService customerService;
    private final CarService carService;
    private final SupplierService supplierService;

    // Get all customers, ordered by their birthdate in ascending order.
    // If two customers are born on the same date, first print those, who are not young drivers
    // (e.g. print experienced drivers first). Export the list of customers to JSON.
    @Override
    public void exportOrderedCustomers() throws IOException {
        final ArrayList<CustomerExportDTO> customerExportDTOS = this.customerService.getAllCustomersOrderByBirthDateAscThenByYoungDriversIsFalse()
                .stream().map(e -> MODEL_MAPPER.map(e, CustomerExportDTO.class))
                .collect(Collectors.toCollection(ArrayList::new));

        writeJsonIntoFile(customerExportDTOS, Path.of(ORDERED_CUSTOMERS_JSON_OUTPUT));
    }

    // Get all cars from make Toyota and order them by model alphabetically and then by travelled distance descending.
    // Export the list of cars to JSON.
    @Override
    public void exportCarsFromMakeToyota() throws IOException {
        final HashSet<CarIdMakeModelTrDistDTO> cars = this.carService.getCarsFromMake("Toyota").stream()
                .map(e -> MODEL_MAPPER.map(e, CarIdMakeModelTrDistDTO.class))
                .collect(Collectors.toCollection(HashSet::new));

        writeJsonIntoFile(cars, Path.of(TOYOTA_CARS_JSON_OUTPUT));
    }

    // Get all suppliers that do not import parts from abroad. Get their id, name and the number of parts they can offer to supply.
    // Export the list of suppliers to JSON.
    @Override
    public void exportLocalSuppliers() throws IOException {
        final Set<SupplierIdNamePCountDTO> suppliers = new HashSet<>(this.supplierService.getLocalSuppliers());

        writeJsonIntoFile(suppliers, Path.of(LOCAL_SUPPLIERS_JSON_OUTPUT));
    }

    // Get all cars along with their list of parts. For the car get only make, model and travelled distance.
    // For the parts get only the name and the price. Export the list of cars and their parts to JSON.
    @Override
    public void exportCarsWithTheirListOfParts() throws IOException {
        writeJsonIntoFile(this.carService.getCarsWithTheirListOfParts(), Path.of(CARS_AND_PARTS_JSON_OUTPUT));
    }

    // Get all customers that have bought at least 1 car and get their names, count of cars bought and total money spent on cars.
    // Order the result list by total money spent in descending order then by total cars bought again in descending order.
    // Export the list of customers to JSON.
    @Override
    public void exportCustomerTotalSales() throws IOException {
        writeJsonIntoFile(this.customerService.getTotalSalesByCustomer(), Path.of(CUSTOMERS_TOTAL_SALES_JSON_OUTPUT));
    }


    // Get all sales with information about the car, the customer and the price of the sale with and without discount.
    // Export the list of sales to JSON.
    @Override
    public void exportCustomerTotalSalesAndDiscount() throws IOException {
        writeJsonIntoFile(this.customerService.getTotalSalesAndDiscount(), Path.of(CUSTOMERS_TOTAL_SALES_WITH_DISCOUNT_JSON_OUTPUT));
    }
}
