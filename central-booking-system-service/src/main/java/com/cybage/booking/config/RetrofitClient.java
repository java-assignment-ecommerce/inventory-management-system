package com.cybage.booking.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Log4j2
@Configuration
@Component
public class RetrofitClient {

	@Autowired
	private PropertiesConfig props;

	public Retrofit createInventoryClient() {
		log.info(props.getInventoryUrl());
		Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd").create();
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
				.connectTimeout(Integer.parseInt(props.getConnectionTimeout()), TimeUnit.MILLISECONDS)
				.readTimeout(Integer.parseInt(props.getReadTimeout()), TimeUnit.MILLISECONDS).build();
		
		Retrofit retrofit = new Retrofit.Builder().baseUrl(props.getInventoryUrl())
				.addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient).build();

		return retrofit;
	}
	public Retrofit createOrderClient() {
		log.info(props.getOrderUrl());
		Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd").create();
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
				.connectTimeout(Integer.parseInt(props.getConnectionTimeout()), TimeUnit.MILLISECONDS)
				.readTimeout(Integer.parseInt(props.getReadTimeout()), TimeUnit.MILLISECONDS).build();
		
		Retrofit orderRetrofit = new Retrofit.Builder().baseUrl(props.getOrderUrl())
				.addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient).build();

		return orderRetrofit;
	}

}
