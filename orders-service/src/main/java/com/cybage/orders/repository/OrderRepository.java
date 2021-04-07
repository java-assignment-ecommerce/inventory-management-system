package com.cybage.orders.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cybage.orders.models.Orders;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {

}
