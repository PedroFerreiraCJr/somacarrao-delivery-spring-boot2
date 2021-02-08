package br.com.dotofcodex.somacarrao_delivery_api.domain.exception;

import org.springframework.validation.BindingResult;

public class OrderStatusException extends BaseBindingResultException {

	private static final long serialVersionUID = 1L;

	public OrderStatusException(BindingResult errors, String message) {
		super(errors, message);
	}
}
