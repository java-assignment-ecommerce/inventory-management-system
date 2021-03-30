package com.cybage.orders.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class Orders implements Serializable {

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

}