package br.com.dotofcodex.somacarrao_delivery_api.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Seasoning;
import br.com.dotofcodex.somacarrao_delivery_api.domain.repository.SeasoningRepository;

@Service
public class SeasoningService {

	@Autowired
	private SeasoningRepository repository;

	@Cacheable("seasonings")
	public List<Seasoning> findAll() {
		return repository.findAll();
	}

	@Cacheable("SeasoningById")
	public Optional<Seasoning> findById(Long id) {
		return repository.findById(id);
	}

	@Cacheable("allSeasoningsById")
	public List<Seasoning> findAllById(List<Long> ids) {
		return repository.findAllById(ids);
	}
}
