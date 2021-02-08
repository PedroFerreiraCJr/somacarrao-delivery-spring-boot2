package br.com.dotofcodex.somacarrao_delivery_api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Flavor;
import br.com.dotofcodex.somacarrao_delivery_api.domain.service.FlavorService;

@RestController
@RequestMapping("/flavors")
public class FlavorController {

	@Autowired
	private FlavorService flavorService;

	@GetMapping
	public List<Flavor> listar() {
		return flavorService.findAll();
	}
}
