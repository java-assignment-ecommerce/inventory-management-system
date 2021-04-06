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
public class OrderDTO implements Serializable {
	private Integer orderId;
	private String orderName;
	private Integer inventoryId;
	private Integer orderQuantity;	
//	@SerializedName("localDateTime")
	private String createTime;
	@SerializedName("localDateTime")
	private String updateTime;
	
	
}
