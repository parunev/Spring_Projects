package com.example.cardealer.domain.services.CustomerService;

import com.example.cardealer.domain.entities.Customer;
import com.example.cardealer.domain.dtos.CustomerDTOs.CustomerSalesDTO;
import com.example.cardealer.domain.dtos.CustomerDTOs.CustomerSalesDiscountDTO;
import com.example.cardealer.domain.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomersOrderByBirthDateAscThenByYoungDriversIsFalse() {
        return this.customerRepository.findAllOrderByBirthDateAscThenByYoungDriversIsFalse();
    }

    @Override
    public List<CustomerSalesDTO> getTotalSalesByCustomer() {
        return this.customerRepository.findAllSales();
    }

    @Override
    public List<CustomerSalesDiscountDTO> getTotalSalesAndDiscount() {
        return this.customerRepository.findAllSalesWithDiscount();
    }
}
