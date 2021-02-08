package br.com.dotofcodex.somacarrao_delivery_api.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.somacarrao_delivery_api.api.dto.UserDTO;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.User;
import br.com.dotofcodex.somacarrao_delivery_api.domain.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<User> create(@Valid @RequestBody UserDTO user) {
		return ResponseEntity.ok(service.create(user));
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> list() {
		return service.findAll();
	}
}
