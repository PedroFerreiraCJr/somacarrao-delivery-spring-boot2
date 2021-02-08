package br.com.dotofcodex.somacarrao_delivery_api.domain.exception;

public class OrderNotFoundException extends BaseBindingResultException {

	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String message) {
		super(null, message);
	}
}
