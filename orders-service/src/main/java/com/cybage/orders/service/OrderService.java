package com.cybage.orders.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.models.Orders;
import com.cybage.orders.repository.OrderRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderService  {

	@Autowired
	private OrderRepository orderRepository;
	
	Orders order = new Orders();
	
	OrderDTO orderDTO = new OrderDTO();
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	/**
	 * Get List of All order items
	 */
	public List<Orders> getAllOrders() {

		return orderRepository.findAll();

	}

	/**
	 * Search Order item by using Id
	 */
	public OrderDTO getOrderById(Integer orderId) {
	
		BeanUtils.copyProperties(orderRepository.findById(orderId).get(), orderDTO);	
		return orderDTO;
	}

	/**
	 * Add item into Order table
	 */
	public Orders addOrder(OrderDTO orderDTO) {
		
		BeanUtils.copyProperties(orderDTO, order);
		log.info(("Order received for "+order.getOrderName())
				+" of Quantity "+order.getOrderQuantity());
		return orderRepository.save(order);
	}

	/**
	 * Update Order item information
	 */
	public Orders updateOrder(int orderId, OrderDTO orderDTO) {
		Orders oldOrder = orderRepository.findById(orderId).get();
		oldOrder.setInventoryId(orderDTO.getInventoryId());
		oldOrder.setOrderName(orderDTO.getOrderName());
		oldOrder.setOrderQuantity(orderDTO.getOrderQuantity());
		oldOrder.setCreateTime(orderDTO.getCreateTime());
		oldOrder.setUpdateTime(orderDTO.getUpdateTime());
		log.info(" Updated Orders ID "+(oldOrder.getOrderId())+" Order Name "+(orderDTO.getOrderName()));
		return orderRepository.save(oldOrder);
	}

	/**
	 * Delete order item from table using id
	 */
	public void deleteOrderById(int orderId) {
		log.info("Order is deleted having Id is"+orderId);
		orderRepository.deleteById(orderId);
		
	}

}
