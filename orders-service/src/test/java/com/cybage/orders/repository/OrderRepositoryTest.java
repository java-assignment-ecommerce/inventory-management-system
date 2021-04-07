package com.cybage.orders.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.cybage.orders.models.Orders;


@RunWith(SpringRunner.class)
@DataJpaTest
//@ActiveProfiles("test")
public class OrderRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	OrderRepository orderRepository;

	@Test
	public void contextLoads() {
		assertThat(entityManager).isNotNull();
		assertThat(orderRepository).isNotNull();
	}

	@Test
	public void testFindByAll() {

		Iterable<Orders> orders = orderRepository.findAll();

		assertThat(orders).hasSize(8);
	}

	@Test
	public void testFindByAll_OnAddingNewRecord() {

		Iterable<Orders> orders = orderRepository.findAll();
		Orders order = new Orders();
		order.setOrderName("Order 1");
		order.setInventoryId(9l);
		order.setQuantity(12);
		entityManager.persist(order);
		entityManager.flush();
		orders = orderRepository.findAll();
		assertThat(orders).hasSize(9);
	}

	@Test
	public void testFindByAll_OnDeletingRecords() {
		Iterable<Orders> orders = orderRepository.findAll();
		entityManager.remove(orderRepository.findById(2l).get());
		entityManager.remove(orderRepository.findById(3l).get());

		entityManager.flush();

		orders = orderRepository.findAll();
		assertThat(orders).hasSize(6);
	}

	@Test
	public void testFindByAll_OnUpdatingRecords() {

		Orders found = orderRepository.findById(3l).get();
		found.setQuantity(100);
		entityManager.persist(found);
		entityManager.flush();
		Iterable<Orders> orders = orderRepository.findAll();

		assertThat(orders).hasSize(8);
	}

	@Test
	public void testFindById_Found() {
		Orders order = new Orders();
		order.setOrderName("Order 1");
		order.setInventoryId(1l);
		order.setQuantity(12);
		
		entityManager.persist(order);
		entityManager.flush();

		Orders found = orderRepository.findById(order.getOrderId()).get();

		assertThat(order.getOrderName()).isEqualTo(found.getOrderName());
		assertThat(found.getCreateTime()).isNotNull();
		assertThat(found.getUpdateTime()).isNull();
	}

	@Test
	public void testFindById_NotFound() {

		Optional<Orders> found = orderRepository.findById(100l);

		assertFalse(found.isPresent());
	}
}