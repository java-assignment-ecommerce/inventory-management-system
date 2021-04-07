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
import org.springframework.test.context.junit4.SpringRunner;

import com.cybage.orders.dto.OrderDTO;
import com.cybage.orders.exception.RecordNotFoundException;
import com.cybage.orders.models.Orders;
import com.cybage.orders.repository.OrderRepository;
import com.cybage.orders.service.impl.OrderServiceImpl;
import com.cybage.orders.utils.OrderTestData;


@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

	@Mock
	private OrderRepository orderRepository;
	@Mock
	OrderDTO orderDTO;
	@InjectMocks
	private OrderServiceImpl orderService;

	ModelMapper modelMapper;

	@Before
	public void setUp() {
		modelMapper = spy(new ModelMapper());
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllOrders() {

		Mockito.when(orderRepository.findAll()).thenReturn(OrderTestData.createOrder2Records());

		List<OrderDTO> orderFromService = orderService.listAll();

		assertNotNull(orderFromService);
		assertEquals(2, orderFromService.size());
		OrderDTO s = orderFromService.get(0);
		assertEquals(1l, s.getOrderId());
		assertEquals(9l, s.getInventoryId());
		assertEquals("Order 1", s.getOrderName());
		assertEquals(12, s.getQuantity());
		verify(orderRepository, times(1)).findAll();

	}

	@Test
	public void testGetAllInventory_NoRecords() {

		Mockito.when(orderRepository.findAll()).thenReturn(new ArrayList<>());

		List<OrderDTO> orderFromService = orderService.listAll();
		assertNotNull(orderFromService);
		assertTrue(orderFromService.isEmpty());
		verify(orderRepository, times(1)).findAll();
	}

	@Test
	public void testGetInventoryById() {

		when(orderRepository.findById(1l)).thenReturn(Optional.of(OrderTestData.createOrder_1()));

		OrderDTO ord = orderService.get(1l);
		
		assertEquals(9l,ord.getInventoryId());
		assertEquals("Order 1", ord.getOrderName());
		assertEquals(12, ord.getQuantity());
		verify(orderRepository, times(1)).findById(1l);
	}

	@Test(expected = RecordNotFoundException.class)
	public void testGetOrderById_NotFound() {

		when(orderRepository.findById(1l)).thenReturn(Optional.empty());

		orderService.get(1l);

		verify(orderRepository, times(1)).findById(1l);
	}

	@Test
	public void testCreateOrder() {

		OrderDTO ordDto = OrderTestData.createOrderDTO_1();
		Orders ord = OrderTestData.createOrder_1();

		when(orderRepository.save(Mockito.any())).thenReturn(ord);

		orderService.save(ordDto);

		verify(orderRepository, times(1)).save(ord);
	}

	@Test
	public void testUpdateOrder() {
		OrderDTO ordDto = OrderTestData.createOrderDTO_1();
		Orders ord = OrderTestData.createOrder_1();
		when(orderRepository.save(ord)).thenReturn(ord);

		// need to mock this as well
		when(orderRepository.findById(ordDto.getOrderId()))
				.thenReturn(Optional.of(OrderTestData.createOrder_1()));
		OrderDTO ordDTO = orderService.update(ordDto);
		assertEquals("Order 1", ordDTO.getOrderName());
		verify(orderRepository, times(1)).save(ord);
	}

	@Test
	public void testDeleteInventory() {
		Optional<Orders> i = Optional.of(OrderTestData.createOrder_1());
		when(orderRepository.findById(Mockito.anyLong())).thenReturn(i);
		Mockito.doNothing().when(orderRepository).delete(Mockito.any());
		orderService.delete(1L);
		verify(orderRepository, times(1)).delete(i.get());
	}

}