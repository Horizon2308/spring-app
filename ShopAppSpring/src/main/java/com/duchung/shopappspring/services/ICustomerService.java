package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.CustomerDTO;
import com.duchung.shopappspring.models.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> searchCustomers(String keyword);

    Customer createCustomer(CustomerDTO customerDTO);
}
