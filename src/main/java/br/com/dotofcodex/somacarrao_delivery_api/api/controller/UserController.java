package br.com.dotofcodex.somacarrao_delivery_api.api.controller;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.somacarrao_delivery_api.api.dto.UserDTO;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.User;
import br.com.dotofcodex.somacarrao_delivery_api.domain.service.UserService;
import br.com.dotofcodex.somacarrao_delivery_api.util.ValidationGroups;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private Validator validator;

	@PostMapping
	public ResponseEntity<User> create(@RequestBody UserDTO dto) {
		final Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto, Default.class,
				ValidationGroups.UserPassword.class);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		return ResponseEntity.ok(service.create(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
		final Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto, Default.class);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		return ResponseEntity.ok(service.update(id, dto));
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> list() {
		return service.findAll();
	}
}
