package br.com.dotofcodex.somacarrao_delivery_api.domain.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.createdAt >= :date")
	public List<Order> findAllByCreatedAtAfter(@Param("date") OffsetDateTime date);
}
