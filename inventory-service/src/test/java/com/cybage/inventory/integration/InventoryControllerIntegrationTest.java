package com.cybage.inventory.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cybage.inventory.InventoryServiceApplication;
import com.cybage.inventory.models.Inventory;
import com.cybage.inventory.repository.InventoryRepository;

@RunWith(SpringRunner.class)

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = InventoryServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class InventoryControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InventoryRepository repository;

	private static final String STORE_URL = "/inventory";

//	@LocalServerPort
//	private int port;

	@Before
	public void setup() {
//		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {

		createInventory(1l,"Test Books1", 11);
		createInventory(2l,"Test Books2", 22);

		mockMvc.perform(get(STORE_URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$", hasSize(10)))
				.andExpect(jsonPath("$[8].inventoryName", is("Test Books1")))
				.andExpect(jsonPath("$[8].quantity", is(11)))
				.andExpect(jsonPath("$[9].inventoryName", is("Test Books2")))
				.andExpect(jsonPath("$[9].quantity", is(22))).andExpect(status().isOk());
	}

	private void createInventory(Long inventoryId, String inventoryName, Integer inventoryQuantity) {

		Inventory s = new Inventory();
//		s.setInventoryId(inventoryId);
		s.setInventoryName(inventoryName);
		s.setQuantity(inventoryQuantity);
		s.setCreateTime(LocalDateTime.now());
		repository.save(s);

	}
}