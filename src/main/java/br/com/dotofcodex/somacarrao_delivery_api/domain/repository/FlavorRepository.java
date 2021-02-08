package br.com.dotofcodex.somacarrao_delivery_api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Flavor;

@Repository
public interface FlavorRepository extends JpaRepository<Flavor, Long> {

}
