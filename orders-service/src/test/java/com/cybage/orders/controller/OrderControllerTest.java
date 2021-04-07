package com.cybage.orders.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.web.context.WebApplicationContext;

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.exception.OrderExceptionHandler;
import com.cybage.orders.exception.RecordNotFoundException;
import com.cybage.orders.service.impl.OrderServiceImpl;
import com.cybage.orders.utils.OrderTestData;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebMvcTest
public class OrderControllerTest {

	private static final String UTF_8 = "utf-8";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Mock
	private OrderServiceImpl orderService;

	@InjectMocks
	private OrderController orderControllerTest;

	@LocalServerPort
	private int port;

	@Mock
	Errors errors;

	private static ObjectMapper mapper = new ObjectMapper();

	private static final String ORDERS = "/orders";
//	private static final String LOCALHOST_URL = "http://localhost:";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		log.debug(context);
//		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockMvc = MockMvcBuilders.standaloneSetup(orderControllerTest)
				.setControllerAdvice(OrderExceptionHandler.class).build();
	}

	private String getLocalhostURL(Long id) {

		return getLocalhostURL() + "/" + id;
	}


	private String getLocalhostURL() {

//		return LOCALHOST_URL + port + ORDERS;
		return ORDERS;
	}

	@Test
	public void testGetAllOrder() throws Exception {
		List<OrderDTO> orders = OrderTestData.createOrderDTO2Records();

		Mockito.when(orderService.listAll()).thenReturn(orders);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(getLocalhostURL())
				.accept(MediaType.APPLICATION_JSON);

		ResultActions result = mockMvc.perform(builder).andExpect(status().isOk());

		log.debug(result);
		result.andDo(print());
		result.andExpect(jsonPath("$", notNullValue())).andDo(print()).andExpect(jsonPath("$", hasSize(2)))
				.andDo(print()).andExpect(jsonPath("$[0].orderName", is("Order 1")))
				.andExpect(jsonPath("$[0].quantity", is(12)))
				.andExpect(jsonPath("$[1].orderName", is("Order 2")))
				.andExpect(jsonPath("$[1].quantity", is(22))).andExpect(status().isOk());
	}

	@Test
	public void testGetAllOrder_2() throws Exception {
		List<OrderDTO> orders = new ArrayList<>();
		Mockito.when(orderService.listAll()).thenReturn(orders);

		mockMvc.perform(get(getLocalhostURL()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn();

	}

	@Test
	public void testGetAllOrder_3() throws Exception {
		Long ordId = 1l;
		Mockito.when(orderService.get(ordId)).thenThrow(new RecordNotFoundException(ordId));

		mockMvc.perform(get(getLocalhostURL(ordId)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
				.andExpect(result -> assertEquals(String.format("Record with Id %d not found", ordId),
						result.getResolvedException().getMessage()));
		log.debug("-------------------");
	}

	@Test
	public void testAddOrder() throws Exception {
		OrderDTO ordDTO = OrderTestData.createOrderDTO_1();
		when(orderService.save(ordDTO)).thenReturn(ordDTO);

		String ordJson = mapper.writeValueAsString(ordDTO);
		log.debug("xxxxxxxxxxxxxxxxxx" + ordJson);
		RequestBuilder builder = MockMvcRequestBuilders.post(getLocalhostURL()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding(UTF_8).content(ordJson);
		ResultActions result = mockMvc.perform(builder).andDo(print()).andExpect(status().isCreated());// .andReturn();
		log.debug(result);
		result.andExpect(jsonPath("$", notNullValue())).andDo(print());

		log.debug("..............");
	}
	
	@Test
	public void testGetOrderById() throws Exception{
		Long id = 1L;
		OrderDTO ordDTO = OrderTestData.createOrderDTO_1();
		Mockito.when(orderService.get(id)).thenReturn(ordDTO);		
		mockMvc.perform(get(getLocalhostURL(id)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void testGetOrderByIdNoData() throws Exception{
		Long id = 1L;
		OrderDTO ordDTO = new OrderDTO();
		when(orderService.get(id)).thenReturn(ordDTO);
		
		mockMvc.perform(get(getLocalhostURL(id)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void testDeletOrderById() throws Exception {
		Long id = 1L;
		Mockito.doNothing().when(orderService).delete(id);
		//the id is the path param, hence should be part of the url
		 this.mockMvc.perform(MockMvcRequestBuilders
		            .delete(getLocalhostURL(11l))
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk());
	
	}
}