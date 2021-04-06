package com.cybage.booking.service;
import com.cybage.booking.dto.OrderDTO;
import java.util.List;

import com.cybage.booking.dto.InventoryDTO;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderInterface {

	@GET("/orders")
	public Call<List<OrderDTO>> getOrders();

	/*
	 * @GET("/orders/{orderId}") public Call<InventoryDTO>
	 * getInventory(@Path("inventoryId") Integer inventoryId);
	 */
	
	@POST("/orders")
	public Call<OrderDTO> addOrder(@Body OrderDTO orderDTO);
	
	/*
	 * @PUT("/orders") public Call<InventoryDTO> updateInventory(@Body InventoryDTO
	 * inventoryDTO);
	 */

	@DELETE("/orders/{orderId}")
	public Call<Void> deleteOrder(@Path("orderId") Integer orderId);
}
