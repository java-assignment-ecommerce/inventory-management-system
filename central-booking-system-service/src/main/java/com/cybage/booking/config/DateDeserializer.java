package com.cybage.booking.config;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DateDeserializer implements JsonDeserializer<LocalDateTime> {

	@Override
	public LocalDateTime deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		String date = element.getAsString();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		try {
			LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
			return dateTime;
		} catch (DateTimeParseException e) {
			log.error("Failed to parse Date due to:", e);
			return null;
		}

	}
}