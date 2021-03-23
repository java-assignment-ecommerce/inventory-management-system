package com.cybage.inventory.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cybage.inventory.InventoryServiceApplication;
import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.models.Inventory;
import com.cybage.inventory.repository.InventoryRepository;

@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = InventoryServiceApplication.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = InventoryServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class InventoryControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InventoryRepository repository;

	@Autowired
	private TestRestTemplate restTemplate;

	private static final String INVENTORY = "/inventory";
	private static final String LOCALHOST_URL = "http://localhost:";

	@LocalServerPort
	private Integer port;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllInventory() throws Exception {
		ResponseEntity<List> reponse = restTemplate.getForEntity(getLocalhostURL(), List.class);
		List<InventoryDTO> inventories = ((java.util.List) reponse.getBody());
		assertEquals(8, inventories.size());
		assertEquals(HttpStatus.OK, reponse.getStatusCode());
	}

	@Test
	public void testGetAllInventory_OnAddingRecord() throws Exception {

		Inventory inv = createInventoryEntity("Test Books1", 11);
		restTemplate.postForEntity(getLocalhostURL(), inv, Inventory.class);

		ResponseEntity<List> reponse = restTemplate.getForEntity(getLocalhostURL(), List.class);
		List<InventoryDTO> inventories = ((java.util.List) reponse.getBody());
		assertEquals(9, inventories.size());

		assertEquals(HttpStatus.OK, reponse.getStatusCode());

	}

	@Test
	public void testAddInventory() throws Exception {

		Inventory inv = createInventoryEntity("Test Books1", 11);
		ResponseEntity<?> response = restTemplate.postForEntity(getLocalhostURL(), inv, Inventory.class);
		List<InventoryDTO> inventories = ((java.util.List) response.getBody());
		System.out.println(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	private String getLocalhostURL() {

		return LOCALHOST_URL + port + INVENTORY;
	}

	@Test
	public void testGetAllInventory_usingMockMvc() throws Exception {

		createInventory("Test Books1", 11);
		createInventory("Test Books2", 22);

		mockMvc.perform(get(INVENTORY).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$", hasSize(10))).andExpect(jsonPath("$[8].inventoryName", is("Test Books1")))
				.andExpect(jsonPath("$[8].quantity", is(11)))
				.andExpect(jsonPath("$[9].inventoryName", is("Test Books2")))
				.andExpect(jsonPath("$[9].quantity", is(22))).andExpect(status().isOk());
	}

	private void createInventory(String inventoryName, Integer inventoryQuantity) {

		Inventory s = createInventoryEntity(inventoryName, inventoryQuantity);
		repository.save(s);

	}

	private Inventory createInventoryEntity(String inventoryName, Integer inventoryQuantity) {
		Inventory s = new Inventory();
		s.setInventoryName(inventoryName);
		s.setQuantity(inventoryQuantity);
		s.setCreateTime(LocalDateTime.now());
		return s;
	}
}