package com.cybage.orders.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Orders implements Serializable {
	private static final long serialVersionUID = 2298375083377697514L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@NotNull
	private Long orderId;
	
	@NotNull
	private Long inventoryId;
	
	@Column(nullable = false)
	@Size(min = 1)
	@NotNull
	private String orderName;
	@NotNull
	@PositiveOrZero
	private Integer quantity;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createTime;
	@Column(insertable = false)
	private LocalDateTime updateTime;

	@PrePersist
	protected void onCreate() {
		createTime = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updateTime = LocalDateTime.now();
	}
}
