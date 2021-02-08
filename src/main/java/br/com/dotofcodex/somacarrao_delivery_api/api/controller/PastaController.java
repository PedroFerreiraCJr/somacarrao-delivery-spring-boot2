package br.com.dotofcodex.somacarrao_delivery_api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Pasta;
import br.com.dotofcodex.somacarrao_delivery_api.domain.service.PastaService;

@RestController
@RequestMapping("/pastas")
public class PastaController {

	@Autowired
	private PastaService pastaService;

	@GetMapping
	public List<Pasta> listar() {
		return pastaService.findAll();
	}
}
