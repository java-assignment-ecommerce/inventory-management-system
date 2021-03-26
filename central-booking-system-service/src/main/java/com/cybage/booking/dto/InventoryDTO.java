package com.cybage.booking.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
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
@Data
public class InventoryDTO implements Serializable {
	private Long inventoryId;
	private String inventoryName;
	private Integer quantity;
//	@SerializedName("localDateTime")
	private String createTime;
	@SerializedName("localDateTime")
	private LocalDateTime updateTime;

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