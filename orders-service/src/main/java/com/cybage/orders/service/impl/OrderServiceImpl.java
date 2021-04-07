package com.cybage.orders.service.impl;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.exception.RecordNotFoundException;
import com.cybage.orders.models.Orders;
import com.cybage.orders.repository.OrderRepository;
import com.cybage.orders.service.OrderService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<OrderDTO> listAll() {

		log.info("Get list of all the orders");
		List<OrderDTO> orderDtos = ((List<Orders>) orderRepository.findAll()).stream()
				.map(OrderDTO::new).collect(Collectors.toList());
		return orderDtos;

	}

	@Override
	public OrderDTO get(Long orderId) {

		log.info("Get order for the id : {}", orderId);
		return new OrderDTO(getOrder(orderId));
	}

	private Orders getOrder(Long orderId) {
		log.info("Fetching order for the id : {}", orderId);
		Orders order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RecordNotFoundException(orderId));
		return order;
	}

	@Override
	public OrderDTO save(OrderDTO orderDto) {
		log.info("Saving new order");
		Orders order = modelMapper.map(orderDto, Orders.class);
		Orders ord = orderRepository.save(order);
		return new OrderDTO(ord);
	}

	@Override
	public OrderDTO update(OrderDTO orderDto) {
		log.info("updating order with id : {}", orderDto.getOrderId());
		Orders order = modelMapper.map(orderDto, Orders.class);
		getOrder(order.getOrderId());

		Orders ord = orderRepository.save(order);
		return new OrderDTO(ord);
	}

	@Override
	public void delete(Long orderId) {
		log.info("deleting order with id : {}", orderId);
		Orders order = getOrder(orderId);
		orderRepository.delete(order);
	}
}