package com.cybage.inventory.exception;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorMessage {
	private String errorCode;
	private Date timestamp;
	private String message;
	private String description;

	public ErrorMessage(String message) {
		this.message = message;
	}

	public ErrorMessage(Date timestamp, String message, String description) {
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}

	public ErrorMessage(String errorCode, Date timestamp, String message, String description) {
		this.errorCode = errorCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}
}