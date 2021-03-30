package com.cybage.orders.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.service.OrderService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/orders")
@Log4j2
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	ModelMapper modelMapper;
	
	/**
	 * This will return a list of ORDERS
	 */
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders().stream()
				.map(o -> modelMapper.map(o, OrderDTO.class)).collect(Collectors.toList()));
	}

	/**
	 * This will return particular inventory using id
	 */
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDTO> getOrderByid(@PathVariable int orderId) {
	
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(orderId));
	}

	/**
	 * This will a single order record into table
	 */
	@PostMapping
	public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO) {
		OrderDTO orderDto1=new OrderDTO();
		BeanUtils.copyProperties(orderService.addOrder(orderDTO), orderDto1);
		log.info("In Order controller,entry received for "+orderDto1.getOrderName());
		return ResponseEntity.status(HttpStatus.CREATED).body(orderDto1);

	}

	/**
	 * This will update single record using id
	 */
	@PutMapping("/{orderId}")
	public ResponseEntity<OrderDTO> updateOrder(@PathVariable int orderId,
			@RequestBody OrderDTO orderDTO) {
		
		OrderDTO orderDto1=new OrderDTO();
		BeanUtils.copyProperties(orderService.updateOrder(orderId,orderDTO), orderDto1);
		log.info("In Order controller,update request for ID"+orderId);
		return ResponseEntity.status(HttpStatus.OK).body(orderDto1);

	}

	/**
	 * This will delete single record of order from table If any dependency is there
	 * then stop/revert this operation
	 */
	@DeleteMapping("/{orderId}")
	public ResponseEntity<?> deleteInventoryById(@PathVariable int orderId) {
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK);

	}

}