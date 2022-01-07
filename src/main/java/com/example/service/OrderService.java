package com.example.service;

import com.example.persistence.model.Order;

import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 10:24 AM
 */

public interface OrderService {

    List<Order> getAllOrdersForCustomer(String customerId);

    Order getOrderByIdForCustomer(String customerId,String orderId);
}
