package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.CustomerDTO;
import com.duchung.shopappspring.models.Customer;
import com.duchung.shopappspring.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> searchCustomers(String keyword) {
        return customerRepository.searchCustomers(keyword);
    }

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(convertToCustomer(customerDTO));
    }

    private Customer convertToCustomer(CustomerDTO customerDTO) {
        return Customer.builder()
                .fullName(customerDTO.getFullName())
                .address(customerDTO.getAddress())
                .email(customerDTO.getEmail())
                .build();
    }
}
