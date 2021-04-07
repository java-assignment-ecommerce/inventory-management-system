package com.cybage.orders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.exception.InvalidDataException;
import com.cybage.orders.exception.RecordNotFoundException;
import com.cybage.orders.service.OrderService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("orders")
//@Validated
public class OrderController {

	@Autowired
	OrderService orderService;

	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> getAllOrders(){
		
		log.info("Request to fetch all orders");
		List<OrderDTO> order = orderService.listAll();
		
		if (order.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);

	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDTO> getInventoryById(@PathVariable Long orderId)
			throws RecordNotFoundException {
		log.info("Request to gefetch order with id : {}", orderId);
		return new ResponseEntity<>(orderService.get(orderId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO order, Errors errors) {

		log.info("Request to create a new order");
		if (errors.hasErrors()) {

			List<FieldError> ferrs = errors.getFieldErrors();
			Map<String, Object> errMap = ferrs.stream().collect(HashMap::new,
					(m, v) -> m.put(v.getField(), v.getRejectedValue()), HashMap::putAll);

			throw new InvalidDataException(errMap);
		}

		return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<?> updateOrder(@Valid @RequestBody OrderDTO order, Errors errors) {
		log.info("Request to update order with id : {}", order.getOrderId());
		if (errors.hasErrors()) {

			List<FieldError> ferrs = errors.getFieldErrors();
			Map<String, Object> errMap = ferrs.stream().collect(HashMap::new,
					(m, v) -> m.put(v.getField(), v.getRejectedValue()), HashMap::putAll);

			throw new InvalidDataException(errMap);
		}
		return new ResponseEntity<>(orderService.update(order), HttpStatus.OK);

	}

	@DeleteMapping("{orderId}")
	public ResponseEntity<?> deleteOrderById(@PathVariable @Min(1) Long orderId) {

		log.info("Request to delete order with id : {}", orderId);
		orderService.delete(orderId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}