package br.com.dotofcodex.somacarrao_delivery_api.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Pasta;
import br.com.dotofcodex.somacarrao_delivery_api.domain.repository.PastaRepository;

@Service
public class PastaService {

	@Autowired
	private PastaRepository repository;

	@Cacheable("pastas")
	public List<Pasta> findAll() {
		return repository.findAll();
	}

	@Cacheable("pastaById")
	public Optional<Pasta> findById(Long id) {
		return repository.findById(id);
	}
}
