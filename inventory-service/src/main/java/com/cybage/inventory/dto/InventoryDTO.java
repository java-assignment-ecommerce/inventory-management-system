package com.cybage.inventory.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.cybage.inventory.models.Inventory;

/*
 * Inventory 
 */
public class InventoryDTO implements Serializable {
	private Long inventoryId;
	private String inventoryName;
	private Integer quantity;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

	public InventoryDTO() {
		super();
	}

	public InventoryDTO(Inventory inv) {
		super();
		this.inventoryId = inv.getInventoryId();
		this.inventoryName = inv.getInventoryName();
		this.quantity = inv.getQuantity();
		this.createTime = inv.getCreateTime();
		this.updateTime = inv.getUpdateTime();
	}

	public InventoryDTO(Long inventoryId, String inventoryName, Integer quantity, LocalDateTime createTime,
			LocalDateTime updateTime) {
		super();
		this.inventoryId = inventoryId;
		this.inventoryName = inventoryName;
		this.quantity = quantity;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getInventoryName() {
		return inventoryName;
	}

	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
		return "Inventory [inventoryId=" + inventoryId + ", inventoryName=" + inventoryName + ", quantity=" + quantity
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}