package br.com.dotofcodex.somacarrao_delivery_api.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.somacarrao_delivery_api.api.dto.OrderStatusDTO;
import br.com.dotofcodex.somacarrao_delivery_api.api.infra.JwtTokenUtil;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.OrderStatusException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.ValidationException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.service.OrderService;

@RestController
@RequestMapping("/orders/{id}/status")
public class OrderStatusController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public String updateOrderStatus(@PathVariable("id") Long id, @Valid @RequestBody OrderStatusDTO orderStatus,
			@RequestHeader(value = "Authorization", defaultValue = "") final String header, BindingResult result) {
		if (result.hasErrors()) {
			throw new OrderStatusException(result, "O status do pedido contém informações inválidas");
		}

		String jwt = null;
		try {
			jwt = jwtTokenUtil.getTokenFromBearer(header);
		} catch (IllegalArgumentException e) {
			throw new ValidationException("Houve uma falha no parse do Token");
		}

		return orderService.updateOrderStatus(jwt, id, orderStatus);
	}
}
