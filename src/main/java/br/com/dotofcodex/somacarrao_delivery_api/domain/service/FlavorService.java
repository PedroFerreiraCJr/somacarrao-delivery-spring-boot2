package br.com.dotofcodex.somacarrao_delivery_api.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Flavor;
import br.com.dotofcodex.somacarrao_delivery_api.domain.repository.FlavorRepository;

@Service
public class FlavorService {

	@Autowired
	private FlavorRepository repository;

	@Cacheable("flavors")
	public List<Flavor> findAll() {
		return repository.findAll();
	}

	@Cacheable("flavorById")
	public Optional<Flavor> findById(Long id) {
		return repository.findById(id);
	}
}
