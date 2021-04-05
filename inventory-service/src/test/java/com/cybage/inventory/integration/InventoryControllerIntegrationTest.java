package com.cybage.inventory.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cybage.inventory.InventoryServiceApplication;
import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.exception.ErrorMessage;
import com.cybage.inventory.models.Inventory;
import com.cybage.inventory.repository.InventoryRepository;
import com.cybage.inventory.utils.InventoryTestData;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = InventoryServiceApplication.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = InventoryServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
//@TestConfiguration
@Log4j2
public class InventoryControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private InventoryRepository repository;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	
	@LocalServerPort
	private Integer port;
	
	private static final String INVENTORY = "/inventory";
	private static final String LOCALHOST_URL = "http://localhost:";

	@Before
//	@Sql("data.sql")
	public void setup() {
		MockitoAnnotations.initMocks(this);
//		repository.deleteAll();
		log.debug(port);
	}

	@After
	public void clear() {
		repository.deleteAll();
	}

	private String getLocalhostURL(Long id) {

		return getLocalhostURL() + "/" + id;
	}

	private String getLocalhostURL() {

		return LOCALHOST_URL + port + INVENTORY;
	}

	private static final String UTF_8 = "utf-8";

	@Test
	public void testGetAllInventory() throws Exception {
		ResponseEntity<List> response = restTemplate.getForEntity(getLocalhostURL(), List.class);
		List<InventoryDTO> inventories = ((java.util.List) response.getBody());
		assertNull(inventories);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void testGetAllInventory_OnAddingRecord() throws Exception {

		InventoryDTO inv = InventoryTestData.createInventoryDTO("Test Books1", 11);
		restTemplate.postForEntity(getLocalhostURL(), inv, InventoryDTO.class);

		inv = InventoryTestData.createInventoryDTO("Test Books2", 22);
		restTemplate.postForEntity(getLocalhostURL(), inv, InventoryDTO.class);

		ResponseEntity<List> response = restTemplate.getForEntity(getLocalhostURL(), List.class);
		List<InventoryDTO> inventories = ((java.util.List) response.getBody());
		assertEquals(2, inventories.size());

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testGetInventoryById() throws Exception {

		InventoryDTO inv = InventoryTestData.createInventoryDTO("Test Books1", 11);
		restTemplate.postForEntity(getLocalhostURL(), inv, InventoryDTO.class);

		inv = InventoryTestData.createInventoryDTO("Test Books2", 22);
		ResponseEntity<InventoryDTO> invDtoResponse = restTemplate.postForEntity(getLocalhostURL(), inv,
				InventoryDTO.class);

		ResponseEntity<InventoryDTO> response = restTemplate
				.getForEntity(getLocalhostURL(invDtoResponse.getBody().getInventoryId()), InventoryDTO.class);
		log.debug("xxxxxxxxxxxxxxxxxxxxx" + response);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(invDtoResponse.getBody(), response.getBody());

	}

	@Test
	public void testGetInventoryById_NotFound() throws Exception {

		Long id = 100l;
		ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(getLocalhostURL(id), ErrorMessage.class);
		log.debug("xxxxxxxxxxxxxxxxxxxxx" + response);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Record with Id " + id + " not found", response.getBody().getMessage());
		assertEquals("IMS-100", response.getBody().getErrorCode());
	}

	@Test
	public void testAddInventory() throws Exception {
		InventoryDTO inv = InventoryTestData.createInventoryDTO("Test Books1", 11);
		ResponseEntity<InventoryDTO> response = restTemplate.postForEntity(getLocalhostURL(), inv, InventoryDTO.class);
		InventoryDTO inventories = response.getBody();
		log.debug("xxxxxxxxxxxxxxxxxxxxx" + response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(inventories);
		assertEquals(inv.getInventoryName(), inventories.getInventoryName());
		assertEquals(inv.getQuantity(), inventories.getQuantity());
	}

	@Test
	public void testGetAllInventory_usingMockMvc() throws Exception {

		createInventory("Test Books1", 11);
		createInventory("Test Books2", 22);

		mockMvc.perform(get(INVENTORY).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].inventoryName", is("Test Books1")))
				.andExpect(jsonPath("$[0].quantity", is(11)))
				.andExpect(jsonPath("$[1].inventoryName", is("Test Books2")))
				.andExpect(jsonPath("$[1].quantity", is(22))).andExpect(status().isOk());
	}

	private void createInventory(String inventoryName, Integer inventoryQuantity) {

		Inventory s = InventoryTestData.createInventoryEntity(inventoryName, inventoryQuantity);
		repository.save(s);

	}

	@Test
	public void testUpdateInventory() throws Exception {

		InventoryDTO inv = InventoryTestData.createInventoryDTO("Test Books1", 11);
		ResponseEntity<InventoryDTO> response = restTemplate.postForEntity(getLocalhostURL(), inv, InventoryDTO.class);
		InventoryDTO inventories = response.getBody();
		inventories.setInventoryName("abcabc");

		log.debug(inventories);
		String invJsonNew = objectMapper.writeValueAsString(inventories);
		log.debug(invJsonNew);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(getLocalhostURL())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).characterEncoding(UTF_8)
				.content(invJsonNew);

		ResultActions result = mockMvc.perform(builder).andDo(print()).andExpect(status().isOk());
		log.info(result);
	}

}