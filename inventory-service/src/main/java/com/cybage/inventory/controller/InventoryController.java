package com.cybage.inventory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

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

@RestController
@RequestMapping("inventory")
//@Validated

public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@GetMapping
	public ResponseEntity<List<InventoryDTO>> getAllInventory() {

		List<InventoryDTO> inventory = inventoryService.listAll();
		if (inventory.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(inventory, HttpStatus.OK);

	}

	@GetMapping("/{inventoryId}")
	public ResponseEntity<InventoryDTO> getInventoryByid(@Valid @PositiveOrZero @PathVariable Long inventoryId) {
		return new ResponseEntity<>(inventoryService.get(inventoryId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> addInventory(@Valid @RequestBody Inventory inventory, Errors errors) {
		if (errors.hasErrors()) {

			List<FieldError> ferrs = errors.getFieldErrors();
			Map<String, Object> errMap = ferrs.stream().collect(HashMap::new,
					(m, v) -> m.put(v.getField(), v.getRejectedValue()), HashMap::putAll);

			throw new InvalidDataException(errMap);
		}
		inventoryService.save(inventory);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<?> updateInventory(@Valid @RequestBody Inventory inventory, Errors errors) {
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

		inventoryService.delete(inventoryId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}