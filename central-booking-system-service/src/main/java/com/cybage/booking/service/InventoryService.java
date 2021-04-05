package com.cybage.booking.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cybage.booking.dto.InventoryDTO;
import com.cybage.booking.exception.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;
import retrofit2.Response;

@Service
@Log4j2
public class InventoryService {

	@Autowired
	@Qualifier("inventoryClient")
	private InventoryInterface inventoryClient;
	
	private  ObjectMapper  objectMapper;

	public ResponseEntity<?> getAllInventory() throws IOException {
		Call<List<InventoryDTO>> callInv = inventoryClient.getInventories();

		Response<List<InventoryDTO>> response = callInv.execute();

		if (response.isSuccessful()) {
			List<InventoryDTO> dtos = response.body();

			log.info(dtos);
			log.info(response.message());
			return new ResponseEntity<List<InventoryDTO>>(dtos, HttpStatus.OK);
		} else {
			log.debug(response.errorBody());
			log.debug(response.message());
			 
			ErrorMessage msg = objectMapper.readValue(response.errorBody().string(), ErrorMessage.class);

			return new ResponseEntity<Object>(msg, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> getInventory(@PathVariable Long inventoryId) throws IOException {
		Call<InventoryDTO> callInv = inventoryClient.getInventory(inventoryId);

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
			 
			ErrorMessage msg = objectMapper.readValue(response.errorBody().string(), ErrorMessage.class);
			HttpStatus.valueOf(response.code());
			return new ResponseEntity<Object>(msg, HttpStatus.valueOf(response.code()));
		}
	}

	public ResponseEntity<?> addInventory(@RequestBody InventoryDTO inventory) throws IOException {
		Call<InventoryDTO> callInv = inventoryClient.addInventory(inventory);

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
			 
			ErrorMessage msg = objectMapper.readValue(response.errorBody().string(), ErrorMessage.class);
			HttpStatus.valueOf(response.code());
			return new ResponseEntity<Object>(msg, HttpStatus.valueOf(response.code()));
		}
	}

	public ResponseEntity<?> updateInventory(@RequestBody InventoryDTO inventory) throws IOException {
		Call<InventoryDTO> callInv = inventoryClient.updateInventory(inventory);

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
		 
			ErrorMessage msg = objectMapper.readValue(response.errorBody().string(), ErrorMessage.class);
			HttpStatus.valueOf(response.code());
			return new ResponseEntity<Object>(msg, HttpStatus.valueOf(response.code()));
		}
	}

	public ResponseEntity<?> deleteInventory(@PathVariable Long inventoryId) throws IOException {
		Call<Void> callInv = inventoryClient.deleteInventory(inventoryId);

		Response<Void> response = callInv.execute();
		if (response.isSuccessful()) {

			log.info(response.message());
			log.info("****************************************");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {

			log.debug(response.errorBody());
			log.debug(response.message());
		 
			ErrorMessage msg = objectMapper.readValue(response.errorBody().string(), ErrorMessage.class);
			HttpStatus.valueOf(response.code());
			return new ResponseEntity<Object>(msg, HttpStatus.valueOf(response.code()));
		}
	}

}
