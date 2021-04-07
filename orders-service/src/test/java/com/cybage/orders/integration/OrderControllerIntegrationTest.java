package com.cybage.orders.integration;

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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
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

import com.cybage.orders.OrdersServiceApplication;
import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.exception.ErrorMessage;
import com.cybage.orders.models.Orders;
import com.cybage.orders.repository.OrderRepository;
import com.cybage.orders.utils.OrderTestData;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = OrdersServiceApplication.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = OrderServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
//@TestConfiguration
@Log4j2
public class OrderControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private OrderRepository repository;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	
	@LocalServerPort
	private Integer port;
	
	private static final String ORDERS = "/orders";
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

		return LOCALHOST_URL + port + ORDERS;
	}

	private static final String UTF_8 = "utf-8";

	@Test
	public void testGetAllOrder() throws Exception {
		ResponseEntity<List> response = restTemplate.getForEntity(getLocalhostURL(), List.class);
		List<OrderDTO> orders = ((java.util.List) response.getBody());
		assertNull(orders);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void testGetAllOrder_OnAddingRecord() throws Exception {

		OrderDTO ord = OrderTestData.createOrderDTO("Test Books1", 11);
		restTemplate.postForEntity(getLocalhostURL(), ord, OrderDTO.class);

		ord = OrderTestData.createOrderDTO("Test Books2", 22);
		restTemplate.postForEntity(getLocalhostURL(), ord, OrderDTO.class);

		ResponseEntity<List> response = restTemplate.getForEntity(getLocalhostURL(), List.class);
		List<OrderDTO> orders = ((java.util.List) response.getBody());
		assertEquals(2, orders.size());

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testGetOrderById() throws Exception {

		OrderDTO ord = OrderTestData.createOrderDTO("Test Books1", 11);
		restTemplate.postForEntity(getLocalhostURL(), ord, OrderDTO.class);

		ord = OrderTestData.createOrderDTO("Test Books2", 22);
		ResponseEntity<OrderDTO> ordDtoResponse = restTemplate.postForEntity(getLocalhostURL(), ord,
				OrderDTO.class);

		ResponseEntity<OrderDTO> response = restTemplate
				.getForEntity(getLocalhostURL(ordDtoResponse.getBody().getOrderId()), OrderDTO.class);
		log.debug("xxxxxxxxxxxxxxxxxxxxx" + response);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ordDtoResponse.getBody(), response.getBody());

	}

	@Test
	public void testGetOrderById_NotFound() throws Exception {

		Long id = 100l;
		ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(getLocalhostURL(id), ErrorMessage.class);
		log.debug("xxxxxxxxxxxxxxxxxxxxx" + response);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Record with Id " + id + " not found", response.getBody().getMessage());
		assertEquals("IMS-100", response.getBody().getErrorCode());
	}

	@Test
	public void testAddOrder() throws Exception {
		OrderDTO ord = OrderTestData.createOrderDTO("Test Books1", 11);
		ResponseEntity<OrderDTO> response = restTemplate.postForEntity(getLocalhostURL(), ord, OrderDTO.class);
		OrderDTO orders = response.getBody();
		log.debug("xxxxxxxxxxxxxxxxxxxxx" + response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(orders);
		assertEquals(ord.getInventoryId(),orders.getInventoryId());
		assertEquals(ord.getOrderName(), orders.getOrderName());
		assertEquals(ord.getOrderName(), orders.getOrderName());
		assertEquals(ord.getQuantity(), orders.getQuantity());
	}

	@Test
	public void testGetAllOrder_usingMockMvc() throws Exception {

		createOrder("Test Books1", 11);
		createOrder("Test Books2", 22);

		mockMvc.perform(get(ORDERS).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].inventoryId", is(1)))
				.andExpect(jsonPath("$[0].orderName", is("Test Books1")))
				.andExpect(jsonPath("$[0].quantity", is(11)))
				.andExpect(jsonPath("$[1].inventoryId", is(2)))
				.andExpect(jsonPath("$[1].orderName", is("Test Books2")))
				.andExpect(jsonPath("$[1].quantity", is(22))).andExpect(status().isOk());
	}

	private void createOrder(String orderName, Integer orderQuantity) {

		Orders s = OrderTestData.createOrderEntity(orderName, orderQuantity);
		repository.save(s);

	}

	@Test
	public void testUpdateInventory() throws Exception {

		OrderDTO ord = OrderTestData.createOrderDTO("Test Books1", 11);
		ResponseEntity<OrderDTO> response = restTemplate.postForEntity(getLocalhostURL(), ord, OrderDTO.class);
		OrderDTO orders = response.getBody();
		orders.setOrderName("abcabc");

		log.debug(orders);
		String ordJsonNew = objectMapper.writeValueAsString(orders);
		log.debug(ordJsonNew);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(getLocalhostURL())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).characterEncoding(UTF_8)
				.content(ordJsonNew);

		ResultActions result = mockMvc.perform(builder).andDo(print()).andExpect(status().isOk());
		log.info(result);
	}

}