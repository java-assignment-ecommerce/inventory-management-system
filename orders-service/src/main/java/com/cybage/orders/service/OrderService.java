package com.cybage.orders.service;

import java.util.List;

import com.cybage.orders.dto.OrderDTO;


public interface OrderService {

	List<OrderDTO> listAll();

	OrderDTO get(Long orderId);

	OrderDTO save(OrderDTO order);

	OrderDTO update(OrderDTO order);

	void delete(Long orderId);

}