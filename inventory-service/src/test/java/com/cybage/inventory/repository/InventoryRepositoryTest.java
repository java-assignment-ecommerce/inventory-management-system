package com.cybage.inventory.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.cybage.inventory.models.Inventory;

@RunWith(SpringRunner.class)
@DataJpaTest
//@ActiveProfiles("test")
public class InventoryRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	InventoryRepository inventoryRepository;

	@Test
	public void contextLoads() {
		assertThat(entityManager).isNotNull();
		assertThat(inventoryRepository).isNotNull();
	}

	@Test
	public void testFindByAll() {

		Iterable<Inventory> inventories = inventoryRepository.findAll();

		assertThat(inventories).hasSize(8);
	}

	@Test
	public void testFindByAll_OnAddingNewRecord() {

		Iterable<Inventory> inventories = inventoryRepository.findAll();
		Inventory inventory = new Inventory();
		inventory.setInventoryName("Inventory 1");
		inventory.setQuantity(12);
		entityManager.persist(inventory);
		entityManager.flush();
		inventories = inventoryRepository.findAll();
		assertThat(inventories).hasSize(9);
	}

	@Test
	public void testFindByAll_OnDeletingRecords() {
		Iterable<Inventory> inventories = inventoryRepository.findAll();
		entityManager.remove(inventoryRepository.findById(2l).get());
		entityManager.remove(inventoryRepository.findById(3l).get());

		entityManager.flush();

		inventories = inventoryRepository.findAll();
		assertThat(inventories).hasSize(6);
	}

	@Test
	public void testFindByAll_OnUpdatingRecords() {

		Inventory found = inventoryRepository.findById(3l).get();
		found.setQuantity(100);
		entityManager.persist(found);
		entityManager.flush();
		Iterable<Inventory> inventories = inventoryRepository.findAll();

		assertThat(inventories).hasSize(8);
	}

	@Test
	public void testFindById_Found() {
		Inventory inventory = new Inventory();
		inventory.setInventoryName("Inventory 1");
		inventory.setQuantity(12);
		entityManager.persist(inventory);
		entityManager.flush();

		Inventory found = inventoryRepository.findById(inventory.getInventoryId()).get();

		assertThat(inventory.getInventoryName()).isEqualTo(found.getInventoryName());
		assertThat(found.getCreateTime()).isNotNull();
		assertThat(found.getUpdateTime()).isNull();
	}

	@Test
	public void testFindById_NotFound() {

		Optional<Inventory> found = inventoryRepository.findById(100l);

		assertFalse(found.isPresent());
	}
}