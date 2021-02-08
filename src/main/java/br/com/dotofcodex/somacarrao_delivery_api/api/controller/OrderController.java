package br.com.dotofcodex.somacarrao_delivery_api.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.somacarrao_delivery_api.api.dto.OrderDTO;
import br.com.dotofcodex.somacarrao_delivery_api.api.infra.JwtTokenUtil;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.OrderException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.ValidationException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Flavor;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Ingredient;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Order;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Pasta;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Sauce;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Seasoning;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.User;
import br.com.dotofcodex.somacarrao_delivery_api.domain.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Order> listar() {
		return orderService.findAll();
	}

	@PostMapping
	public ResponseEntity<Order> order(@Valid @RequestBody OrderDTO order,
			@RequestHeader(value = "Authorization", defaultValue = "") final String header, BindingResult result) {
		if (result.hasErrors()) {
			throw new OrderException(result, "O pedido contém informações inválidas");
		}

		String jwt = null;
		try {
			jwt = jwtTokenUtil.getTokenFromBearer(header);
		} catch (IllegalArgumentException e) {
			throw new ValidationException("O pedido contém informações inválidas");
		}

		return ResponseEntity.ok(orderService.create(toModel(order, jwt)));
	}

	private Order toModel(OrderDTO dto, String jwt) {
		return Order.builder().pasta(new Pasta(dto.getPasta().getId())).flavor(new Flavor(dto.getFlavor().getId()))
				.sauces(dto.getSauces().stream().map((s) -> new Sauce(s.getId())).collect(Collectors.toList()))
				.ingredients(dto.getIngredients().stream().map((i) -> new Ingredient(i.getId()))
						.collect(Collectors.toList()))
				.seasonings(
						dto.getSeasonings().stream().map((s) -> new Seasoning(s.getId())).collect(Collectors.toList()))
				.client(new User(jwt)).build();
	}
}
