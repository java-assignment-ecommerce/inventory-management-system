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

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@RestController
@RequestMapping("bookings")
@Log4j2
public class CentralBookingsController {

	@Autowired
	private RetrofitClient retrofit;
	private final static String ALL_ORDERS = "/view";
	private final static String ADD = "/add";
	private final static String UPDATE = "/update";
	private final static String DELETE = "/remove";
	private final static String ALL_INVENTORY = "/view";

	@GetMapping
	public String test() {
		return "Central Bookings";
	}

	@GetMapping("inventory")
	public ResponseEntity<?> getAllInventory() throws IOException {

		Retrofit r = retrofit.createInventoryClient();

		InventoryInterface inv = r.create(InventoryInterface.class);
		Call<List<InventoryDTO>> callInv = inv.getInventories();

		Response<List<InventoryDTO>> response = callInv.execute();

		if (response.isSuccessful()) {
			List<InventoryDTO> dtos = response.body();

			log.info(dtos);
			log.info(response.message());
			return new ResponseEntity<List<InventoryDTO>>(dtos, HttpStatus.OK);
		} else {
			log.debug(response.errorBody());
			log.debug(response.message());
			ObjectMapper mapper = new ObjectMapper();
			ErrorMessage msg = mapper.readValue(response.errorBody().string(), ErrorMessage.class);

			return new ResponseEntity<Object>(msg, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("inventory/{inventoryId}")
	public ResponseEntity<?> getInventory(@PathVariable Long inventoryId) throws IOException {
		Retrofit r = retrofit.createInventoryClient();
		InventoryInterface inv = r.create(InventoryInterface.class);
		Call<InventoryDTO> callInv = inv.getInventory(inventoryId);

		Response<InventoryDTO> response = callInv.execute();
		if (response.isSuccessful()) {
			InventoryDTO dtos = response.body();

			log.info(dtos);
			log.info(response.message());
			log.info("****************************************");
			return new ResponseEntity<InventoryDTO>(dtos, HttpStatus.OK);
		} else {

			log.debug(response.errorBody());
			log.debug(response.message());
			ObjectMapper mapper = new ObjectMapper();
			ErrorMessage msg = mapper.readValue(response.errorBody().string(), ErrorMessage.class);
			HttpStatus.valueOf(response.code());
			return new ResponseEntity<Object>(msg, HttpStatus.valueOf(response.code()));
		}
	}

	@PostMapping("inventory")
	public ResponseEntity<?> addInventory(@RequestBody InventoryDTO inventory) throws IOException {
		Retrofit r = retrofit.createInventoryClient();
		InventoryInterface inv = r.create(InventoryInterface.class);
		Call<InventoryDTO> callInv = inv.addInventory(inventory);

		Response<InventoryDTO> response = callInv.execute();
		if (response.isSuccessful()) {
			InventoryDTO dtos = response.body();

			log.info(dtos);
			log.info(response.message());
			log.info("****************************************");
			return new ResponseEntity<InventoryDTO>(dtos, HttpStatus.OK);
		} else {

			log.debug(response.errorBody());
			log.debug(response.message());
			ObjectMapper mapper = new ObjectMapper();
			ErrorMessage msg = mapper.readValue(response.errorBody().string(), ErrorMessage.class);
			HttpStatus.valueOf(response.code());
			return new ResponseEntity<Object>(msg, HttpStatus.valueOf(response.code()));
		}
	}

	@PutMapping("inventory")
	public ResponseEntity<?> updateInventory(@RequestBody InventoryDTO inventory) throws IOException {
		Retrofit r = retrofit.createInventoryClient();
		InventoryInterface inv = r.create(InventoryInterface.class);
		Call<InventoryDTO> callInv = inv.updateInventory(inventory);

		Response<InventoryDTO> response = callInv.execute();
		if (response.isSuccessful()) {
			InventoryDTO dtos = response.body();

			log.info(dtos);
			log.info(response.message());
			log.info("****************************************");
			return new ResponseEntity<InventoryDTO>(dtos, HttpStatus.OK);
		} else {

			log.debug(response.errorBody());
			log.debug(response.message());
			ObjectMapper mapper = new ObjectMapper();
			ErrorMessage msg = mapper.readValue(response.errorBody().string(), ErrorMessage.class);
			HttpStatus.valueOf(response.code());
			return new ResponseEntity<Object>(msg, HttpStatus.valueOf(response.code()));
		}
	}

	@DeleteMapping("inventory/{inventoryId}")
	public ResponseEntity<?> deleteInventory(@PathVariable Long inventoryId) throws IOException {
		Retrofit r = retrofit.createInventoryClient();
		InventoryInterface inv = r.create(InventoryInterface.class);
		Call<Void> callInv = inv.deleteInventory(inventoryId);

		Response<Void> response = callInv.execute();
		if (response.isSuccessful()) {

			log.info(response.message());
			log.info("****************************************");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {

			log.debug(response.errorBody());
			log.debug(response.message());
			ObjectMapper mapper = new ObjectMapper();
			ErrorMessage msg = mapper.readValue(response.errorBody().string(), ErrorMessage.class);
			HttpStatus.valueOf(response.code());
			return new ResponseEntity<Object>(msg, HttpStatus.valueOf(response.code()));
		}
	}

}
