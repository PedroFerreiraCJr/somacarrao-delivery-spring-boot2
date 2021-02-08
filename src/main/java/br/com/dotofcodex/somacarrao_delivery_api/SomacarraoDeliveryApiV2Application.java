package br.com.dotofcodex.somacarrao_delivery_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SomacarraoDeliveryApiV2Application {

	public static void main(String[] args) {
		SpringApplication.run(SomacarraoDeliveryApiV2Application.class, args);
	}
}
