package com.cybage.orders.serice.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.orders.dto.OrderDto;
import com.cybage.orders.model.Orders;
import com.cybage.orders.repository.OrderRepository;
import com.cybage.orders.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * Get List of All order items
	 */
	@Override
	public List<Orders> getAllOrders() {
		return null;

//		List<OrderDTO> orderDtos = ((List<Orders>) orderRepository.findAll()).stream()
//				.map(o -> modelMapper.map(o, OrderDTO.class)).collect(Collectors.toList());
//		return orderDtos;

//		return orderRepository.findAll();

	}

	/**
	 * Search Order item by using Id
	 */
	@Override
	public OrderDto getOrderById(Integer orderId) {
		OrderDto orderDto = new OrderDto();

		BeanUtils.copyProperties(orderRepository.findById(orderId).get(), orderDto);

		return orderDto;
	}

	/**
	 * Add item into Order table
	 */
	@Override
	public Orders addOrder(Orders order) {
		return orderRepository.save(order);
	}

	/**
	 * Update Order item information
	 */
	@Override
	public Orders updateOrder(int orderId, Orders order) {
		Orders oldOrder = orderRepository.findById(orderId).get();

		oldOrder.setInventoryId(order.getInventoryId());
		oldOrder.setOrderName(order.getOrderName());
		oldOrder.setOrderQuantity(order.getOrderQuantity());
		oldOrder.setCreateTime(order.getCreateTime());
		oldOrder.setUpdateTime(order.getUpdateTime());

		return orderRepository.save(oldOrder);
	}

	/**
	 * Delete order item from table using id
	 */
	@Override
	public String deleteOrderById(int orderId) {
		orderRepository.deleteById(orderId);
		return "deleted successfully";
	}

}