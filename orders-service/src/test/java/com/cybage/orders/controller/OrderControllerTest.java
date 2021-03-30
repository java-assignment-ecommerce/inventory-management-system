package com.cybage.orders.controller;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.models.Orders;
import com.cybage.orders.service.OrderService;
import com.cybage.orders.utils.OrderTestData;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderControllerTest;

	private static final String STORE_ID = "/orders";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		System.out.println(context);
//		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockMvc = MockMvcBuilders.standaloneSetup(orderControllerTest).build();
	}
	
//	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
//		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
//				String.class)).contains("Hello, World");
		
//		assertThat(this.restTemplate.getForObject("/inventory",
//				String.class).)
//		
//		mockMvc.perform(get("/inventory").contentType(MediaType.APPLICATION_JSON)).
//		andExpect(status().isNoContent()).andReturn();

	}

	@Test
	public void testGetAllOrders() throws Exception {
//		List<OrderDTO> orders = OrderTestData.createOrdersDTO2Records();
//		Mockito.when(orderService.getAllOrders()).thenReturn(orders);
//		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(STORE_ID).accept(MediaType.APPLICATION_JSON);
//
//		mockMvc.perform(builder).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue())).andDo(print())
//				.andExpect(jsonPath("$", hasSize(2))).andDo(print())
//				.andExpect(jsonPath("$[0].orderName", is("Orders 1")))
//				.andExpect(jsonPath("$[0].quantity", is(12)))
//				.andExpect(jsonPath("$[1].orderName", is("Orders 2")))
//				.andExpect(jsonPath("$[1].quantity", is(22)))
//				.andExpect(status().isOk());
	}

	@Test
	public void testGetAllOrders_2() throws Exception {
		mockMvc.perform(get("/orders").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();

	}

//	@Test
//	public void greetingShouldReturnDefaultMessage() throws Exception {
////		OrdersDTO s = new OrdersDTO();
////		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/inventory", OrdersDTO.class))
////				.contains(s);
//	}
}