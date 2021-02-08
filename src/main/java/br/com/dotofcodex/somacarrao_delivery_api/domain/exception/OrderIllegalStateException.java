package br.com.dotofcodex.somacarrao_delivery_api.domain.exception;

public class OrderIllegalStateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OrderIllegalStateException(String message) {
		super(message);
	}
}
