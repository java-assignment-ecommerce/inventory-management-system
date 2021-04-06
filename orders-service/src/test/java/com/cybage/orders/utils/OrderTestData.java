package com.cybage.orders.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.models.Orders;

public class OrderTestData {

	public OrderTestData() {
	}

	public static Orders createOrders_1() {
		Orders order1 = new Orders();
		order1.setCreateTime(LocalDateTime.now());
	//	order1.setUpdateTime(LocalDateTime.now());
		order1.setOrderName("Order 1");
		order1.setInventoryId(1);
		order1.setOrderQuantity(12);
	
		return order1;
	}

	public static Orders createOrders_2() {
		Orders order2 = new Orders();
		order2.setCreateTime(LocalDateTime.now());
	//  order2.setUpdateTime(LocalDateTime.now());
		order2.setOrderName("Order 2");
		order2.setInventoryId(2);
		order2.setOrderQuantity(22);
		return order2;
	}

//	public static Optional<Orders> createOrdersModel_1() {
//		Optional<Orders> order1 = new Orders();
//		order1.setCreateTime(LocalDateTime.now());
//		order1.setOrdersId(1l);
//		order1.setOrdersName("Orders 1");
//		order1.setQuantity(12);
//		return order1;
//	}
	
	public static List<Orders> createOrders2Records() {
		List<Orders> orders = new ArrayList<>();
		orders.add(createOrders_1());
		orders.add(createOrders_2());
		return orders;
	}

	public static OrderDTO createOrdersDTO_1() {
		OrderDTO order1 = new OrderDTO();
		order1.setCreateTime(LocalDateTime.now());
		order1.setUpdateTime(LocalDateTime.now());
		order1.setOrderId(1);
		order1.setInventoryId(2);
		order1.setOrderName("Order 1");
		order1.setOrderQuantity(5);
		return order1;
	}

	public static OrderDTO createOrdersDTO_2() {
		OrderDTO order2 = new OrderDTO();
		order2.setCreateTime(LocalDateTime.now());
		order2.setUpdateTime(LocalDateTime.now());
		order2.setOrderId(1);
		order2.setInventoryId(2);
		order2.setOrderName("Order 1");
		order2.setOrderQuantity(5);
		return order2;
	}
	public static List<OrderDTO> createOrdersDTOSingleEntry() {
		List<OrderDTO> stores = new ArrayList<>();
		stores.add(createOrdersDTO_1());
		return stores;
	}

	public static List<OrderDTO> createOrdersDTO2Records() {
		List<OrderDTO> stores = new ArrayList<>();
		stores.add(createOrdersDTO_1());
		stores.add(createOrdersDTO_2());
		return stores;
	}

}