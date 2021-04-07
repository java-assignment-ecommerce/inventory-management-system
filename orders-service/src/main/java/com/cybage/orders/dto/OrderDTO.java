package com.cybage.orders.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.cybage.orders.models.Orders;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDTO implements Serializable {
	private Long orderId;
	private Long inventoryId;
	private String orderName;
	private Integer quantity;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createTime;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime updateTime;

	public OrderDTO(Orders ord) {
		super();
		this.orderId = ord.getOrderId();
		this.inventoryId = ord.getInventoryId();
		this.orderName =ord.getOrderName();
		this.quantity = ord.getQuantity();
		this.createTime = ord.getCreateTime();
		this.updateTime = ord.getUpdateTime();
	}
//
//	public InventoryDTO(Long inventoryId, String inventoryName, Integer quantity, LocalDateTime createTime,
//			LocalDateTime updateTime) {
//		super();
//		this.inventoryId = inventoryId;
//		this.inventoryName = inventoryName;
//		this.quantity = quantity;
//		this.createTime = createTime;
//		this.updateTime = updateTime;
//	}

}