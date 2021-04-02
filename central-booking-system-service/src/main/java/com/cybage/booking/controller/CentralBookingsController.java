package com.cybage.booking.controller;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.booking.config.RetrofitClient;
import com.cybage.booking.dto.InventoryDTO;
import com.cybage.booking.exception.ErrorMessage;
import com.cybage.booking.service.InventoryInterface;
import com.cybage.booking.service.InventoryService;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@RestController
@RequestMapping("bookings")
@Log4j2
public class CentralBookingsController {

	@Autowired
	InventoryService inventoryService;

	@GetMapping
	public String test() {
		return "Central Bookings";
	}

	@GetMapping("inventory")
	public ResponseEntity<?> getAllInventory() throws IOException {

		return inventoryService.getAllInventory();

	}

	@GetMapping("inventory/{inventoryId}")
	public ResponseEntity<?> getInventory(@PathVariable Long inventoryId) throws IOException {
		return inventoryService.getInventory(inventoryId);
	}

	@PostMapping("inventory")
	public ResponseEntity<?> addInventory(@RequestBody InventoryDTO inventory) throws IOException {
		return inventoryService.addInventory(inventory);
	}

	@PutMapping("inventory")
	public ResponseEntity<?> updateInventory(@RequestBody InventoryDTO inventory) throws IOException {
		return inventoryService.updateInventory(inventory);
	}

	@DeleteMapping("inventory/{inventoryId}")
	public ResponseEntity<?> deleteInventory(@PathVariable Long inventoryId) throws IOException {
		return inventoryService.deleteInventory(inventoryId);
	}

}
