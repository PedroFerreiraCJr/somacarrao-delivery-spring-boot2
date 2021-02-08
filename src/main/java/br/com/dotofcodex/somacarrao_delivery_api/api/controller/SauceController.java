package br.com.dotofcodex.somacarrao_delivery_api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Sauce;
import br.com.dotofcodex.somacarrao_delivery_api.domain.service.SauceService;

@RestController
@RequestMapping("/sauces")
public class SauceController {

	@Autowired
	private SauceService sauceService;

	@GetMapping
	public List<Sauce> listar() {
		return sauceService.findAll();
	}
}
