package com.cybage.inventory.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.exception.RecordNotFoundException;
import com.cybage.inventory.models.Inventory;
import com.cybage.inventory.repository.InventoryRepository;
import com.cybage.inventory.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	public List<InventoryDTO> listAll() {

		List<InventoryDTO> inventoryDtos = ((List<Inventory>) inventoryRepository.findAll()).stream()
				.map(InventoryDTO::new).collect(Collectors.toList());
		return inventoryDtos;

	}

	@Override
	public InventoryDTO get(Long inventoryId) {

		return new InventoryDTO(getInventory(inventoryId));
	}

	private Inventory getInventory(Long inventoryId) {
		Inventory inventory = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new RecordNotFoundException(inventoryId));
		return inventory;
	}

	@Override
	public void save(Inventory inventory) {
		inventoryRepository.save(inventory);
	}

	@Override
	public Inventory update(Inventory inventory) {
		getInventory(inventory.getInventoryId());

		return inventoryRepository.save(inventory);
	}

	@Override
	public void delete(Long inventoryId) {
		Inventory inventory = getInventory(inventoryId);
		inventoryRepository.delete(inventory);
	}
}
