package com.cybage.inventory.service.impl;

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

import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.exception.RecordNotFoundException;
import com.cybage.inventory.models.Inventory;
import com.cybage.inventory.repository.InventoryRepository;
import com.cybage.inventory.utils.InventoryTestData;

@RunWith(SpringRunner.class)
public class InventoryServiceImplTest {

	@Mock
	private InventoryRepository inventoryRepository;
	@Mock
	InventoryDTO inventoryDTO;
	@InjectMocks
	private InventoryServiceImpl inventoryService;

	ModelMapper modelMapper;

	@Before
	public void setUp() {
		modelMapper = spy(new ModelMapper());
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllInventory() {

		Mockito.when(inventoryRepository.findAll()).thenReturn(InventoryTestData.createInventory2Records());

		List<InventoryDTO> inventoryFromService = inventoryService.listAll();

		assertNotNull(inventoryFromService);
		assertEquals(2, inventoryFromService.size());
		InventoryDTO s = inventoryFromService.get(0);
		assertEquals(1l, s.getInventoryId());
		assertEquals("Inventory 1", s.getInventoryName());
		assertEquals(12, s.getQuantity());
		verify(inventoryRepository, times(1)).findAll();

	}

	@Test
	public void testGetAllInventory_NoRecords() {

		Mockito.when(inventoryRepository.findAll()).thenReturn(new ArrayList<>());

		List<InventoryDTO> inventoryFromService = inventoryService.listAll();

		assertNotNull(inventoryFromService);
		assertTrue(inventoryFromService.isEmpty());
		verify(inventoryRepository, times(1)).findAll();
	}

	@Test
	public void testGetInventoryById() {

		when(inventoryRepository.findById(1l)).thenReturn(Optional.of(InventoryTestData.createInventory_1()));

		InventoryDTO inv = inventoryService.get(1l);

		assertEquals("Inventory 1", inv.getInventoryName());
		assertEquals(12, inv.getQuantity());
		verify(inventoryRepository, times(1)).findById(1l);
	}

	@Test(expected = RecordNotFoundException.class)
	public void testGetInventoryById_NotFound() {

		when(inventoryRepository.findById(1l)).thenReturn(Optional.empty());

		inventoryService.get(1l);

		verify(inventoryRepository, times(1)).findById(1l);
	}

	@Test
	public void testCreateInventory() {

		InventoryDTO invDto = InventoryTestData.createInventoryDTO_1();
		Inventory inv = InventoryTestData.createInventory_1();

		when(inventoryRepository.save(Mockito.any())).thenReturn(inv);

		inventoryService.save(invDto);

		verify(inventoryRepository, times(1)).save(inv);
	}

	@Test
	public void testUpdateInventory() {
		InventoryDTO invDto = InventoryTestData.createInventoryDTO_1();
		Inventory inv = InventoryTestData.createInventory_1();
		when(inventoryRepository.save(inv)).thenReturn(inv);

		// need to mock this as well
		when(inventoryRepository.findById(invDto.getInventoryId()))
				.thenReturn(Optional.of(InventoryTestData.createInventory_1()));
		InventoryDTO invDTO = inventoryService.update(invDto);
		assertEquals("Inventory 1", invDTO.getInventoryName());
		verify(inventoryRepository, times(1)).save(inv);
	}

	@Test
	public void testDeleteInventory() {
		Optional<Inventory> i = Optional.of(InventoryTestData.createInventory_1());
		when(inventoryRepository.findById(Mockito.anyLong())).thenReturn(i);
		Mockito.doNothing().when(inventoryRepository).delete(Mockito.any());
		inventoryService.delete(1L);
		verify(inventoryRepository, times(1)).delete(i.get());
	}

}