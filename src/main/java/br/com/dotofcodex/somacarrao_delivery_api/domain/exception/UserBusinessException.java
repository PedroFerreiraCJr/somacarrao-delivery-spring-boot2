package br.com.dotofcodex.somacarrao_delivery_api.domain.exception;

public class UserBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserBusinessException(String message) {
		super(message);
	}
}
