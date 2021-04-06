/*
 * package com.cybage.orders.integration;
 * 
 * import static org.hamcrest.Matchers.hasSize; import static
 * org.hamcrest.Matchers.is; import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 * 
 * import java.time.LocalDateTime;
 * 
 * import org.junit.Before; import org.junit.Test; import
 * org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
 * import org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.http.MediaType; import
 * org.springframework.test.context.TestPropertySource; import
 * org.springframework.test.context.junit4.SpringRunner; import
 * org.springframework.test.web.servlet.MockMvc;
 * 
 * import com.cybage.orders.OrdersServiceApplication; import
 * com.cybage.orders.models.Orders; import
 * com.cybage.orders.repository.OrderRepository;
 * 
 * @RunWith(SpringRunner.class)
 * 
 * //@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
 * 
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes
 * = OrdersServiceApplication.class)
 * 
 * @AutoConfigureMockMvc
 * 
 * @TestPropertySource(locations = "classpath:application.properties") public
 * class OrderControllerIntegrationTest {
 * 
 * @Autowired private MockMvc mockMvc;
 * 
 * @Autowired private OrderRepository orderrepository;
 * 
 * private static final String STORE_URL = "/inventory";
 * 
 * // @LocalServerPort // private int port;
 * 
 * @Before public void setup() { // MockitoAnnotations.initMocks(this); }
 * 
 * @Test public void givenEmployees_whenGetEmployees_thenStatus200() throws
 * Exception {
 * 
 * createOrder(1,"Test Books1", 11); createOrder(2,"Test Books2", 22);
 * 
 * mockMvc.perform(get(STORE_URL).contentType(MediaType.APPLICATION_JSON)).
 * andExpect(status().isOk())
 * .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).
 * andDo(print()) .andExpect(jsonPath("$", hasSize(10)))
 * .andExpect(jsonPath("$[8].inventoryName", is("Test Books1")))
 * .andExpect(jsonPath("$[8].quantity", is(11)))
 * .andExpect(jsonPath("$[9].inventoryName", is("Test Books2")))
 * .andExpect(jsonPath("$[9].quantity", is(22))).andExpect(status().isOk()); }
 * 
 * private void createOrder(Integer orderId, String orderName, Integer
 * orderQuantity) {
 * 
 * Orders o = new Orders(); // s.setOrderId(inventoryId);
 * o.setOrderName(orderName); o.setOrderQuantity(orderQuantity);
 * o.setCreateTime(LocalDateTime.now()); orderrepository.save(o);
 * 
 * } }
 */