package com.cybage.inventory.service;

import java.util.List;

import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.models.Inventory;

public interface InventoryService {

	List<InventoryDTO> listAll();

	InventoryDTO get(Long inventoryId);

	void save(Inventory inventory);

	Inventory update(Inventory inventory);

	void delete(Long inventoryId);

}