package com.cybage.orders.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.models.Orders;


public class OrderTestData {

	public OrderTestData() {
	}

	public static Orders createOrder_1() {
		Orders ord1 = new Orders();
		ord1.setOrderId(1l);
		ord1.setInventoryId(9l);
		ord1.setCreateTime(LocalDateTime.now());
		ord1.setQuantity(12);
		ord1.setOrderName("Order 1");
		
		return ord1;
	}

	public static Orders createOrder_2() {
		Orders ord2 = new Orders();
		ord2.setOrderId(2l);
		ord2.setInventoryId(8l);
		ord2.setCreateTime(LocalDateTime.now());
		ord2.setQuantity(22);
		ord2.setOrderName("Order 2");
		
		return ord2;
	}
	public static List<Orders> createOrder2Records() {
		List<Orders> orders = new ArrayList<>();
		orders.add(createOrder_1());
		orders.add(createOrder_2());
		return orders;
	}
	public static Orders createOrder_New() {
		Orders ord1 = new Orders();
		ord1.setOrderId(1l);
		ord1.setInventoryId(9l);
		ord1.setCreateTime(LocalDateTime.now());
		ord1.setQuantity(12);
		ord1.setOrderName("Order 1");
		
		return ord1;
	}
	

	public static OrderDTO createOrderDTO_1() {
		OrderDTO ord1 = new OrderDTO();
		
		ord1.setOrderId(1l);
		ord1.setInventoryId(9l);
		ord1.setCreateTime(LocalDateTime.now());
		ord1.setQuantity(12);
		ord1.setOrderName("Order 1");
		return ord1;
	}
	
	public static OrderDTO createOrderDTO_2() {
		OrderDTO ord2 = new OrderDTO();

		ord2.setOrderId(2l);
		ord2.setInventoryId(8l);
		ord2.setCreateTime(LocalDateTime.now());
		ord2.setQuantity(22);
		ord2.setOrderName("Order 2");
		return ord2;
	}
	
	public static List<OrderDTO> createOrderDTOSingleEntry() {
		List<OrderDTO> stores = new ArrayList<>();
		stores.add(createOrderDTO_1());
		return stores;
	}
	
	public static List<OrderDTO> createOrderDTO2Records() {
		List<OrderDTO> stores = new ArrayList<>();
		stores.add(createOrderDTO_1());
		stores.add(createOrderDTO_2());
		return stores;
	}
	public static Orders createOrderEntity(String orderName, Integer orderQuantity) {
		Orders s = new Orders();
		s.setOrderName(orderName);
		s.setQuantity(orderQuantity);
		s.setCreateTime(LocalDateTime.now());
		return s;
	}


	public static OrderDTO createOrderDTO(String orderName, Integer orderQuantity) {
		OrderDTO s = new OrderDTO();
		s.setOrderName(orderName);
		s.setQuantity(orderQuantity);
		s.setCreateTime(LocalDateTime.now());
		return s;
	}
}