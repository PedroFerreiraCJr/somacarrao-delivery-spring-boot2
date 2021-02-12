package br.com.dotofcodex.somacarrao_delivery_api.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.ValidationException;

@Component
public class PasswordValidator {
	private static final Pattern NUMBERS = Pattern.compile("\\d+");
	private static final Pattern LETTERS = Pattern.compile("\\D+");

	public boolean validate(String password) throws ValidationException {
		if (!NUMBERS.matcher(password).matches()) {
			throw new ValidationException("A senha deve conter dígitos");
		}

		if (!LETTERS.matcher(password).matches()) {
			throw new ValidationException("A senha deve conter letras");
		}

		return true;
	}
}
