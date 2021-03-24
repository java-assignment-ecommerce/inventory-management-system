package com.cybage.inventory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.exception.InvalidDataException;
import com.cybage.inventory.models.Inventory;
import com.cybage.inventory.service.InventoryService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("inventory")
//@Validated

public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@GetMapping
	public ResponseEntity<List<InventoryDTO>> getAllInventory() {
		log.info("Request to fetch all inventories");
		List<InventoryDTO> inventory = inventoryService.listAll();
		if (inventory.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(inventory, HttpStatus.OK);

	}

	@GetMapping("/{inventoryId}")
	public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long inventoryId) {
		log.info("Request to gefetch inventory with id : {}", inventoryId);
		return new ResponseEntity<>(inventoryService.get(inventoryId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InventoryDTO> addInventory(@Valid @RequestBody Inventory inventory, Errors errors) {

		log.info("Request to create a new inventory");
		if (errors.hasErrors()) {

			List<FieldError> ferrs = errors.getFieldErrors();
			Map<String, Object> errMap = ferrs.stream().collect(HashMap::new,
					(m, v) -> m.put(v.getField(), v.getRejectedValue()), HashMap::putAll);

			throw new InvalidDataException(errMap);
		}

		return new ResponseEntity<>(inventoryService.save(inventory), HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<?> updateInventory(@Valid @RequestBody Inventory inventory, Errors errors) {
		log.info("Request to update inventory with id : {}", inventory.getInventoryId());
		if (errors.hasErrors()) {

			List<FieldError> ferrs = errors.getFieldErrors();
			Map<String, Object> errMap = ferrs.stream().collect(HashMap::new,
					(m, v) -> m.put(v.getField(), v.getRejectedValue()), HashMap::putAll);

			throw new InvalidDataException(errMap);
		}
		return new ResponseEntity<>(inventoryService.update(inventory), HttpStatus.OK);

	}

	@DeleteMapping("{inventoryId}")
	public ResponseEntity<?> deleteInventoryById(@PathVariable @Min(1) Long inventoryId) {

		log.info("Request to delete inventory with id : {}", inventoryId);
		inventoryService.delete(inventoryId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}