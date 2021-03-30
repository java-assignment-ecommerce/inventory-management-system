package com.cybage.booking.service;

import java.util.List;

import com.cybage.booking.dto.InventoryDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InventoryInterface {

	@GET("/inventory")
	public Call<List<InventoryDTO>> getInventories();

	@GET("/inventory/{inventoryId}")
	public Call<InventoryDTO> getInventory(@Path("inventoryId") Long inventoryId);

	@POST("/inventory")
	public Call<InventoryDTO> addInventory(@Body InventoryDTO inventoryDTO);

	@PUT("/inventory")
	public Call<InventoryDTO> updateInventory(@Body InventoryDTO inventoryDTO);

	@DELETE("/inventory/{inventoryId}")
	public Call<Void> deleteInventory(@Path("inventoryId") Long inventoryId);
}
