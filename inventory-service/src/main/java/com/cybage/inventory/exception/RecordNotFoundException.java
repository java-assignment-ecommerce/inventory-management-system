package com.cybage.inventory.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

	public RecordNotFoundException() {

		super();
	}

	public RecordNotFoundException(Long id) {

		super(String.format("Record with Id %d not found", id));
	}
}