package com.cybage.inventory.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.cybage.inventory.models.Inventory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class InventoryDTO implements Serializable {
	private Long inventoryId;
	private String inventoryName;
	private Integer quantity;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

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

}