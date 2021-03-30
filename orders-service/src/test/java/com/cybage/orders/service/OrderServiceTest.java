package com.cybage.orders.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.models.Orders;
import com.cybage.orders.repository.OrderRepository;
import com.cybage.orders.utils.OrderTestData;

@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;
	// ---------------
//	@Mock
//	@Autowired
//	@MockBean
//	private ModelMapper modelMapper = new ModelMapper();
//private ModelMapper modelMapper = modelmapp
	// ---------------
//	@Autowired
//	@MockBean
//	@Mock
	private ModelMapper modelMapper;
	// ---------------
	@InjectMocks
//	@Autowired
	private OrderService orderService;

//	@Autowired

	@Before
	public void setUp() {
		modelMapper = spy(new ModelMapper());
//		modelMapper.getConfiguration().setFieldMatchingEnabled(true) ;
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
//		modelMapper.addMappings(new Inventory());
//		new ModelMapper();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllOrders() {

		Mockito.when(orderRepository.findAll()).thenReturn(OrderTestData.createOrders2Records());
		List<Orders> orderFromService = orderService.getAllOrders();
		assertNotNull(orderFromService);
		assertEquals(2, orderFromService.size());
		Orders o = orderFromService.get(0);
		//assertEquals(null, o.getOrderId());
		assertEquals("Order 1", o.getOrderName());
		assertEquals(12, o.getOrderQuantity());
		assertEquals(1,o.getInventoryId());
		verify(orderRepository, times(1)).findAll();

	}

	@Test
	public void testGetAllOrder_NoRecords() {

		Mockito.when(orderRepository.findAll()).thenReturn(new ArrayList<>());
		List<Orders> orderFromService = orderService.getAllOrders();
		assertNotNull(orderFromService);
		assertTrue(orderFromService.isEmpty());
		verify(orderRepository, times(1)).findAll();
	}

	@Test
	public void testGetOrderById() {

		when(orderRepository.findById(1)).thenReturn(Optional.of(OrderTestData.createOrders_1()));
		OrderDTO order= orderService.getOrderById(1);
		assertEquals("Order 1", order.getOrderName());
		assertEquals(1,order.getInventoryId());
		assertEquals(12, order.getOrderQuantity());
		verify(orderRepository, times(1)).findById(1);
	}

	@Test(expected = Exception.class)
	public void testGetOrderById_NotFound() {

		when(orderRepository.findById(1)).thenReturn(Optional.empty());

		orderService.getOrderById(1);

		verify(orderRepository, times(1)).findById(1);
	}

	@Test
	public void testCreateOrder() {
		Orders orders = OrderTestData.createOrders_1();
		OrderDTO orderDTO= OrderTestData.createOrdersDTO_1();
		when(orderRepository.findById(1)).thenReturn(Optional.of(orders));		
		when(orderRepository.save(orders)).thenReturn(orders);
		orderService.updateOrder(1,orderDTO);
		verify(orderRepository, times(1)).save(orders);
	}
}