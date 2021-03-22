package com.cybage.inventory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cybage.inventory.models.Inventory;


@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {

}