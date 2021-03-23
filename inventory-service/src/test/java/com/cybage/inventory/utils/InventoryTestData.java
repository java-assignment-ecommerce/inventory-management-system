package com.cybage.inventory.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cybage.inventory.dto.InventoryDTO;
import com.cybage.inventory.models.Inventory;

public class InventoryTestData {

	public InventoryTestData() {
	}

	public static Inventory createInventory_1() {
		Inventory inv1 = new Inventory();
		inv1.setCreateTime(LocalDateTime.now());
		inv1.setInventoryId(1l);
		inv1.setInventoryName("Inventory 1");
		inv1.setQuantity(12);
		return inv1;
	}

	public static Inventory createInventory_2() {
		Inventory inv2 = new Inventory();
		inv2.setCreateTime(LocalDateTime.now());
		inv2.setInventoryId(2l);
		inv2.setInventoryName("Inventory 2");
		inv2.setQuantity(22);
		return inv2;
	}

//	public static Optional<Inventory> createInventoryModel_1() {
//		Optional<Inventory> inv1 = new Inventory();
//		inv1.setCreateTime(LocalDateTime.now());
//		inv1.setInventoryId(1l);
//		inv1.setInventoryName("Inventory 1");
//		inv1.setQuantity(12);
//		return inv1;
//	}
	
	public static List<Inventory> createInventory2Records() {
		List<Inventory> inventories = new ArrayList<>();
		inventories.add(createInventory_1());
		inventories.add(createInventory_2());
		return inventories;
	}

	public static InventoryDTO createInventoryDTO_1() {
		InventoryDTO inv1 = new InventoryDTO();
		inv1.setCreateTime(LocalDateTime.now());
		inv1.setInventoryId(1l);
		inv1.setInventoryName("Inventory 1");
		inv1.setQuantity(12);
		return inv1;
	}

	public static InventoryDTO createInventoryDTO_2() {
		InventoryDTO inv2 = new InventoryDTO();
		inv2.setCreateTime(LocalDateTime.now());
		inv2.setInventoryId(2l);
		inv2.setInventoryName("Inventory 2");
		inv2.setQuantity(22);
		return inv2;
	}

	public static List<InventoryDTO> createInventoryDTOSingleEntry() {
		List<InventoryDTO> stores = new ArrayList<>();
		stores.add(createInventoryDTO_1());
		return stores;
	}

	public static List<InventoryDTO> createInventoryDTO2Records() {
		List<InventoryDTO> stores = new ArrayList<>();
		stores.add(createInventoryDTO_1());
		stores.add(createInventoryDTO_2());
		return stores;
	}

}