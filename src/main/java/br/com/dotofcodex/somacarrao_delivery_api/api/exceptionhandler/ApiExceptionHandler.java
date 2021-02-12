package br.com.dotofcodex.somacarrao_delivery_api.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.dotofcodex.somacarrao_delivery_api.api.exceptionhandler.ErrorResponse.Field;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.BaseBindingResultException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.OrderIllegalStateException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.OrderNotFoundException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.UserAlreadyExistsException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.UserNotFoundException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.ValidationException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messages;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(ex, createErrorResponse(ex.getBindingResult(), ex, "Campos Inválidos"),
				headers, status, request);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Object> handleException(UserAlreadyExistsException ex, WebRequest request) {
		return handleExceptionInternal(ex,
				new ErrorResponse(HttpStatus.BAD_REQUEST.value(), OffsetDateTime.now(), ex.getMessage()),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(OrderIllegalStateException.class)
	public ResponseEntity<Object> handleException(OrderIllegalStateException ex, WebRequest request) {
		return handleExceptionInternal(ex,
				new ErrorResponse(HttpStatus.BAD_REQUEST.value(), OffsetDateTime.now(), ex.getMessage()),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Object> handleException(OrderNotFoundException ex, WebRequest request) {
		return handleExceptionInternal(ex,
				new ErrorResponse(HttpStatus.NOT_FOUND.value(), OffsetDateTime.now(), ex.getMessage()),
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleException(UserNotFoundException ex, WebRequest request) {
		return handleExceptionInternal(ex,
				new ErrorResponse(HttpStatus.NOT_FOUND.value(), OffsetDateTime.now(), ex.getMessage()),
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleException(ValidationException ex, WebRequest request) {
		return handleExceptionInternal(ex,
				new ErrorResponse(HttpStatus.BAD_REQUEST.value(), OffsetDateTime.now(), ex.getMessage()),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(BaseBindingResultException.class)
	public ResponseEntity<Object> handleException(BaseBindingResultException ex, WebRequest request) {
		return super.handleExceptionInternal(ex, createErrorResponse(ex.getBindingResult(), ex, ex.getMessage()),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Set<String>> handleConstraintViolation(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

		Set<String> messages = new HashSet<>(constraintViolations.size());
		messages.addAll(constraintViolations.stream()
				.map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
						constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
				.collect(Collectors.toList()));

		return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
	}

	private ErrorResponse createErrorResponse(BindingResult errors, Exception ex, String title) {
		final List<Field> fields = new ArrayList<>();
		if (errors != null) {
			for (final ObjectError error : errors.getAllErrors()) {
				fields.add(new ErrorResponse.Field(((FieldError) error).getField(),
						messages.getMessage(error, LocaleContextHolder.getLocale())));
			}
		}

		ErrorResponse response = new ErrorResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setTitle(title);
		response.setHour(OffsetDateTime.now());
		response.setFields(fields);

		return response;
	}
}
