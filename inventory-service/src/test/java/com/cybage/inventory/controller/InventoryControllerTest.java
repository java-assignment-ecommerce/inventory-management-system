package com.cybage.inventory.controller;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.web.context.WebApplicationContext;

import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.exception.InventoryExceptionHandler;
import com.cybage.inventory.exception.RecordNotFoundException;
import com.cybage.inventory.models.Inventory;
import com.cybage.inventory.service.InventoryService;
import com.cybage.inventory.service.impl.InventoryServiceImpl;
import com.cybage.inventory.utils.InventoryTestData;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebMvcTest
public class InventoryControllerTest {

	private static final String UTF_8 = "utf-8";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Mock
	private InventoryServiceImpl inventoryService;

	@InjectMocks
	private InventoryController inventoryControllerTest;

	@LocalServerPort
	private int port;

	@Mock
	Errors errors;

	private static ObjectMapper mapper = new ObjectMapper();

	private static final String INVENTORY = "/inventory";
//	private static final String LOCALHOST_URL = "http://localhost:";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		log.debug(context);
//		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockMvc = MockMvcBuilders.standaloneSetup(inventoryControllerTest)
				.setControllerAdvice(InventoryExceptionHandler.class).build();
	}

	private String getLocalhostURL(Long id) {

		return getLocalhostURL() + "/" + id;
	}


	private String getLocalhostURL() {

//		return LOCALHOST_URL + port + INVENTORY;
		return INVENTORY;
	}

	@Test
	public void testGetAllInventory() throws Exception {
		List<InventoryDTO> inventories = InventoryTestData.createInventoryDTO2Records();

		Mockito.when(inventoryService.listAll()).thenReturn(inventories);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(getLocalhostURL())
				.accept(MediaType.APPLICATION_JSON);

		ResultActions result = mockMvc.perform(builder).andExpect(status().isOk());

		log.debug(result);
		result.andDo(print());
		result.andExpect(jsonPath("$", notNullValue())).andDo(print()).andExpect(jsonPath("$", hasSize(2)))
				.andDo(print()).andExpect(jsonPath("$[0].inventoryName", is("Inventory 1")))
				.andExpect(jsonPath("$[0].quantity", is(12)))
				.andExpect(jsonPath("$[1].inventoryName", is("Inventory 2")))
				.andExpect(jsonPath("$[1].quantity", is(22))).andExpect(status().isOk());
	}

	@Test
	public void testGetAllInventory_2() throws Exception {
		List<InventoryDTO> inventories = new ArrayList<>();
		Mockito.when(inventoryService.listAll()).thenReturn(inventories);

		mockMvc.perform(get(getLocalhostURL()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn();

	}

	@Test
	public void testGetAllInventory_3() throws Exception {
		Long invId = 1l;
		Mockito.when(inventoryService.get(invId)).thenThrow(new RecordNotFoundException(invId));

		mockMvc.perform(get(getLocalhostURL(invId)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
				.andExpect(result -> assertEquals(String.format("Record with Id %d not found", invId),
						result.getResolvedException().getMessage()));
		log.debug("-------------------");
	}

	@Test
	public void testAddInventory() throws Exception {
		InventoryDTO invDTO = InventoryTestData.createInventoryDTO_1();
		when(inventoryService.save(invDTO)).thenReturn(invDTO);

		String invJson = mapper.writeValueAsString(invDTO);
		log.debug("xxxxxxxxxxxxxxxxxx" + invJson);
		RequestBuilder builder = MockMvcRequestBuilders.post(getLocalhostURL()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding(UTF_8).content(invJson);
		ResultActions result = mockMvc.perform(builder).andDo(print()).andExpect(status().isCreated());// .andReturn();
		log.debug(result);
		result.andExpect(jsonPath("$", notNullValue())).andDo(print());

		log.debug("..............");
	}
	
	@Test
	public void testGetInventoryById() throws Exception{
		Long id = 1L;
		InventoryDTO invDTO = InventoryTestData.createInventoryDTO_1();
		Mockito.when(inventoryService.get(id)).thenReturn(invDTO);
		
		mockMvc.perform(get(getLocalhostURL(id)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void testGetInventoryByIdNoData() throws Exception{
		Long id = 1L;
		InventoryDTO invDTO = new InventoryDTO();
		when(inventoryService.get(id)).thenReturn(invDTO);
		
		mockMvc.perform(get(getLocalhostURL(id)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteInventoryById() throws Exception {
		Long id = 1L;
		Mockito.doNothing().when(inventoryService).delete(id);
		//the id is the path param, hence should be part of the url
		 this.mockMvc.perform(MockMvcRequestBuilders
		            .delete(getLocalhostURL(11l))
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk());
	
	}
}