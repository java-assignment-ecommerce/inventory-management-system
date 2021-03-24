package com.cybage.inventory.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
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

import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.models.Inventory;
import com.cybage.inventory.service.impl.InventoryServiceImpl;
import com.cybage.inventory.utils.InventoryTestData;

import lombok.extern.log4j.Log4j2;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@RunWith(SpringJUnit4ClassRunner.class)
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
		mockMvc = MockMvcBuilders.standaloneSetup(inventoryControllerTest).build();
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
		mockMvc.perform(get("/inventory").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent())
				.andReturn();

	}

	@Test
	public void testAddInventory() throws Exception {
		InventoryDTO invDTO = InventoryTestData.createInventoryDTO_1();
		Inventory inv = InventoryTestData.createInventory_New();
		when(inventoryService.save(inv)).thenReturn(invDTO);

		String invJson = mapper.writeValueAsString(inv);
		log.debug("xxxxxxxxxxxxxxxxxx" + invJson);
		RequestBuilder builder = MockMvcRequestBuilders.post(getLocalhostURL()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding(UTF_8).content(invJson);
		ResultActions result = mockMvc.perform(builder).andDo(print()).andExpect(status().isCreated());// .andReturn();
		log.debug(result);
		result.andDo(print());
//		result.andExpect(jsonPath("$", notNullValue())).andDo(print());
//		log.debug(result.getResponse().getContentAsString());

		log.debug("..............");
	}
}