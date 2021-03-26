package com.cybage.booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class PropertiesConfig {

	@Value("${order_url}")
	private String orderUrl;
	@Value("${inventory_url}")
	private String inventoryUrl;

	@Value("${read_time_out}")
	private String readTimeout;
	@Value("${connection_time_out}")
	private String connectionTimeout;
}
