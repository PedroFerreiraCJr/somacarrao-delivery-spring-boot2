package br.com.dotofcodex.somacarrao_delivery_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import br.com.dotofcodex.somacarrao_delivery_api.api.handler.SimpleWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	private static final String ENDPOINT = "/simple";
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(getWebSocketHandler(), ENDPOINT).setAllowedOrigins("*");
	}

	@Bean
	public WebSocketHandler getWebSocketHandler() {
		return new SimpleWebSocketHandler();
	}
}
