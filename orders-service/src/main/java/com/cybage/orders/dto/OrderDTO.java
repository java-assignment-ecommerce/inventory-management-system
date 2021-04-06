package com.cybage.orders.dto;

import java.time.LocalDateTime;

public class OrderDTO {
	private Integer orderId;
	private String orderName;
	private Integer inventoryId;
	private Integer orderQuantity;	
//	@Column(nullable = false)
	private LocalDateTime createTime;
//	@Column(nullable = false)
	private LocalDateTime updateTime;
	public OrderDTO() {
		super();
	}
	public OrderDTO(Integer orderId, String orderName, Integer inventoryId, Integer orderQuantity,
			LocalDateTime createTime, LocalDateTime updateTime) {
		super();
		this.orderId = orderId;
		this.orderName = orderName;
		this.inventoryId = inventoryId;
		this.orderQuantity = orderQuantity;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public Integer getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}
	public Integer getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "OrderDto [orderId=" + orderId + ", orderName=" + orderName + ", inventoryId=" + inventoryId
				+ ", orderQuantity=" + orderQuantity + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}

	
}
