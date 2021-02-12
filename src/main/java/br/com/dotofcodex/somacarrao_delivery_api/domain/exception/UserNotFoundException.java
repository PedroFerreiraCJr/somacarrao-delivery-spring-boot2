package br.com.dotofcodex.somacarrao_delivery_api.domain.exception;

public class UserNotFoundException extends BaseBindingResultException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(null, message);
	}
}
