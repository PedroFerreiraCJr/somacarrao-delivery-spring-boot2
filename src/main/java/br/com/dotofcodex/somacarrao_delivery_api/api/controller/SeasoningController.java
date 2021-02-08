package br.com.dotofcodex.somacarrao_delivery_api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Seasoning;
import br.com.dotofcodex.somacarrao_delivery_api.domain.service.SeasoningService;

@RestController
@RequestMapping("/seasonings")
public class SeasoningController {

	@Autowired
	private SeasoningService seasoningService;

	@GetMapping
	public List<Seasoning> listar() {
		return seasoningService.findAll();
	}
}
