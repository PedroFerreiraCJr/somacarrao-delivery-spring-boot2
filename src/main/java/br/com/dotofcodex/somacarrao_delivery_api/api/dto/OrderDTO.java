package br.com.dotofcodex.somacarrao_delivery_api.api.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
	@NotNull
	@Valid
	private PastaDTO pasta;

	@NotNull
	@Valid
	private FlavorDTO flavor;

	@NotEmpty
	@Valid
	private List<SauceDTO> sauces;

	@Valid
	private List<IngredientDTO> ingredients;

	@Valid
	private List<SeasoningDTO> seasonings;
}
