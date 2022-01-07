package com.example.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 2:05 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends RepresentationModel<Order> {
    private String orderId;
    private double price;
    private int quantity;

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", price=" + price + ", quantity=" + quantity + "]";
    }
}
