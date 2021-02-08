package br.com.dotofcodex.somacarrao_delivery_api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Ingredient;
import br.com.dotofcodex.somacarrao_delivery_api.domain.service.IngredientService;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;

	@GetMapping
	public List<Ingredient> listar() {
		return ingredientService.findAll();
	}
}
