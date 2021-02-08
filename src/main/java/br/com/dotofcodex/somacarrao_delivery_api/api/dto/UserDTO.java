package br.com.dotofcodex.somacarrao_delivery_api.api.dto;

import javax.validation.constraints.NotBlank;

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
public class UserDTO {

	@NotBlank
	private String name;

	@NotBlank
	private String email;

	@NotBlank
	private String telefone;

	@NotBlank
	private String password;

	@NotBlank
	private String passwordConfirmation;
}
