package com.example.service;

import com.example.persistence.model.Customer;

import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 10:13 AM
 */

public interface CustomerService {

    List<Customer> allCustomers();

    Customer getCustomerDetail(final String id);
}
