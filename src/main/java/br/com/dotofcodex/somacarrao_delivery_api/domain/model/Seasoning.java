package br.com.dotofcodex.somacarrao_delivery_api.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AccessLevel;
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
@Table(name = "seasonings")
public class Seasoning extends BaseEntity {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private Double price;

	public Seasoning(Long id) {
		super(id);
	}
}
