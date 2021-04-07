package com.cybage.orders;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cybage.orders.controller.OrderController;

@SpringBootTest
class OrdersServiceApplicationTests {

	@Autowired
	OrderController orderController;

	@Test
	void contextLoads() {
	}

	@Test
	public void contextLoads_1() throws Exception {
		assertThat(orderController).isNotNull();
	}


}
