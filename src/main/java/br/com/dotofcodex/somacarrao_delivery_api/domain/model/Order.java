package br.com.dotofcodex.somacarrao_delivery_api.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Pasta pasta;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Flavor flavor;

	@ManyToMany
	@JoinTable(name = "order_sauces", joinColumns = {
			@JoinColumn(name = "order_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "sauce_id", referencedColumnName = "id") })
	private List<Sauce> sauces;

	@ManyToMany
	@JoinTable(name = "order_ingredients", joinColumns = {
			@JoinColumn(name = "order_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "ingredient_id", referencedColumnName = "id") })
	private List<Ingredient> ingredients;

	@ManyToMany
	@JoinTable(name = "order_seasonings", joinColumns = {
			@JoinColumn(name = "order_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "seasoning_id", referencedColumnName = "id") })
	private List<Seasoning> seasonings;

	private Double total;

	@Column(name = "created_at")
	private OffsetDateTime createdAt;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private User client;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<OrderStatusHistory> statusHistory;
}
