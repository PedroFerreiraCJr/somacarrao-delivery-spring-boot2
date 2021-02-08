package br.com.dotofcodex.somacarrao_delivery_api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Pasta;

@Repository
public interface PastaRepository extends JpaRepository<Pasta, Long> {

}
