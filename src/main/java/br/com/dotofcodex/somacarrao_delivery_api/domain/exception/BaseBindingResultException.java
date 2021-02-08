package br.com.dotofcodex.somacarrao_delivery_api.domain.exception;

import org.springframework.validation.BindingResult;

public class BaseBindingResultException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final BindingResult errors;

	public BaseBindingResultException(BindingResult errors, String message) {
		super(message);
		this.errors = errors;
	}

	public BindingResult getBindingResult() {
		return this.errors;
	}
}
