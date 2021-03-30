package com.cybage.inventory.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.exception.RecordNotFoundException;
import com.cybage.inventory.models.Inventory;
import com.cybage.inventory.repository.InventoryRepository;
import com.cybage.inventory.service.InventoryService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	public List<InventoryDTO> listAll() {

		log.info("Get list of all the inventories");
		List<InventoryDTO> inventoryDtos = ((List<Inventory>) inventoryRepository.findAll()).stream()
				.map(InventoryDTO::new).collect(Collectors.toList());
		return inventoryDtos;

	}

	@Override
	public InventoryDTO get(Long inventoryId) {

		log.info("Get inventory for the id : {}", inventoryId);
		return new InventoryDTO(getInventory(inventoryId));
	}

	private Inventory getInventory(Long inventoryId) {
		log.info("Fetching inventory for the id : {}", inventoryId);
		Inventory inventory = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new RecordNotFoundException(inventoryId));
		return inventory;
	}

	@Override
	public InventoryDTO save(Inventory inventory) {
		log.info("Saving new inventory");
		Inventory inv = inventoryRepository.save(inventory);
		return new InventoryDTO(inv);
	}

	@Override
	public InventoryDTO update(Inventory inventory) {
		log.info("updating inventory with id : {}", inventory.getInventoryId());
		getInventory(inventory.getInventoryId());

		Inventory inv = inventoryRepository.save(inventory);
		return new InventoryDTO(inv);
	}

	@Override
	public void delete(Long inventoryId) {
		log.info("deleting inventory with id : {}", inventoryId);
		Inventory inventory = getInventory(inventoryId);
		inventoryRepository.delete(inventory);
	}
}