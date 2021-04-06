package com.cybage.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybage.orders.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
