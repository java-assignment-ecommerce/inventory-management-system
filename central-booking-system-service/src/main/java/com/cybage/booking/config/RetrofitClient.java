package com.cybage.booking.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cybage.booking.service.InventoryInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Log4j2
@Configuration
//@Component

public class RetrofitClient {

	@Autowired
	private PropertiesConfig props;

	@Bean(name = "inventoryClient")
	public InventoryInterface createInventoryClient() {
		log.info(props.getInventoryUrl());
		Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd").create();
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
				.connectTimeout(Integer.parseInt(props.getConnectionTimeout()), TimeUnit.MILLISECONDS)
				.readTimeout(Integer.parseInt(props.getReadTimeout()), TimeUnit.MILLISECONDS).build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(props.getInventoryUrl())
				.addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient).build();

		InventoryInterface inv = retrofit.create(InventoryInterface.class);

		return inv;
	}

}
