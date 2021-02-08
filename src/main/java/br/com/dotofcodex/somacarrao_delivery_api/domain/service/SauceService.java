package br.com.dotofcodex.somacarrao_delivery_api.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Sauce;
import br.com.dotofcodex.somacarrao_delivery_api.domain.repository.SauceRepository;

@Service
public class SauceService {

	@Autowired
	private SauceRepository repository;

	@Cacheable("sauces")
	public List<Sauce> findAll() {
		return repository.findAll();
	}

	@Cacheable("sauceById")
	public Optional<Sauce> findById(Long id) {
		return repository.findById(id);
	}

	@Cacheable("allSaucesById")
	public List<Sauce> findAllById(List<Long> ids) {
		return repository.findAllById(ids);
	}
}
