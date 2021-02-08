package br.com.dotofcodex.somacarrao_delivery_api.api.infra;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ClientKeyRequestFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("Client Key RequestFilter");

		final String apiKey = request.getParameter("API_KEY");
		log.info(apiKey);
		if (apiKey == null || !apiKey.equalsIgnoreCase("pedro")) {
			log.warn("Client Unauthorized");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API Key Not Found");
			return;
		}

		filterChain.doFilter(request, response);
	}
}
