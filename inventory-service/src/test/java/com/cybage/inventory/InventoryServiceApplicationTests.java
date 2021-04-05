package com.cybage.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cybage.inventory.controller.InventoryController;

@SpringBootTest
public class InventoryServiceApplicationTests {

	@Autowired
	InventoryController inventoryController;

	@Test
	void contextLoads() {
	}

	@Test
	public void contextLoads_1() throws Exception {
		assertThat(inventoryController).isNotNull();
	}

}