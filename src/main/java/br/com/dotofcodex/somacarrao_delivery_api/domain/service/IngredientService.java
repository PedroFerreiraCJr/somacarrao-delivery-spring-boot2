package br.com.dotofcodex.somacarrao_delivery_api.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Ingredient;
import br.com.dotofcodex.somacarrao_delivery_api.domain.repository.IngredientRepository;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository repository;

	@Cacheable("ingredients")
	public List<Ingredient> findAll() {
		return repository.findAll();
	}

	@Cacheable("ingredientById")
	public Optional<Ingredient> findById(Long id) {
		return repository.findById(id);
	}

	@Cacheable("allIngredientsById")
	public List<Ingredient> findAllById(List<Long> ids) {
		return repository.findAllById(ids);
	}
}
