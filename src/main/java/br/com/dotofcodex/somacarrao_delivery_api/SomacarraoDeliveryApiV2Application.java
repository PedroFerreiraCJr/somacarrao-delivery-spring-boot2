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
	
	/*
	@Bean
	public CommandLineRunner run() {
		return (args) -> {
			User user = new User();
			user.setEmail("pedroferreiracjr@gmail.com");
			user.setPassword(encoder.encode("p3dr0"));
			user.setEnabled(true);
			repository.save(user);
		};
	}
	*/
}
