package com.example.persistence.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 2:02 PM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Customer extends RepresentationModel<Customer> {
    private String customerId;
    private String customerName;
    private String companyName;
    private Map<String,Order> orders;

    public Customer(final String customerId, final String customerName, final String companyName) {
        super();
        this.customerId = customerId;
        this.customerName = customerName;
        this.companyName = companyName;
    }
}
