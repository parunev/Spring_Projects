package com.example.cardealer.domain.services.CustomerService;

import com.example.cardealer.domain.entities.Customer;
import com.example.cardealer.domain.dtos.CustomerDTOs.CustomerSalesDTO;
import com.example.cardealer.domain.dtos.CustomerDTOs.CustomerSalesDiscountDTO;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomersOrderByBirthDateAscThenByYoungDriversIsFalse();
    List<CustomerSalesDTO> getTotalSalesByCustomer();
    List<CustomerSalesDiscountDTO> getTotalSalesAndDiscount();
}
