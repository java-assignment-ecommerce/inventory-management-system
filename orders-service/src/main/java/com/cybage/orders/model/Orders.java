package com.cybage.orders.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	private String orderName;

	private Integer inventoryId;
	private Integer orderQuantity;
//	@Column(nullable = false)
	private LocalDateTime createTime;
//	@Column(nullable = false)
	private LocalDateTime updateTime;

	public Orders() {
		super();
	}

	public Orders(Integer orderId, String orderName, Integer inventoryId, Integer orderQuantity,
			LocalDateTime createTime, LocalDateTime updateTime) {
		super();
		this.orderId = orderId;
		this.orderName = orderName;
		this.inventoryId = inventoryId;
		this.orderQuantity = orderQuantity;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Integer getorderId() {
		return orderId;
	}

	public void setorderId(Integer orderId) {
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
		return "Order1 [orderId=" + orderId + ", orderName=" + orderName + ", inventoryId=" + inventoryId
				+ ", orderQuantity=" + orderQuantity + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}

}