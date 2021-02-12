package br.com.dotofcodex.somacarrao_delivery_api.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.dotofcodex.somacarrao_delivery_api.util.ValidationGroups;
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
	@Size(max = 40)
	private String name;

	@Email
	private String email;

	@NotBlank
	private String telefone;

	@NotBlank(groups = ValidationGroups.UserPassword.class)
	@Size(min = 5, max = 30)
	private String password;

	@NotBlank(groups = ValidationGroups.UserPassword.class)
	@Size(min = 5, max = 30)
	private String passwordConfirmation;
}
