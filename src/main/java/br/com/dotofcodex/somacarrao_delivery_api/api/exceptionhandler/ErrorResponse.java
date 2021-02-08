package br.com.dotofcodex.somacarrao_delivery_api.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

	private Integer status;
	private OffsetDateTime hour;
	private String title;
	private List<Field> fields;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(Integer status, OffsetDateTime hour, String title) {
		super();
		this.status = status;
		this.hour = hour;
		this.title = title;
	}

	public ErrorResponse addFields(final List<Field> fields) {
		this.fields = fields;
		return this;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OffsetDateTime getHour() {
		return hour;
	}

	public void setHour(OffsetDateTime hour) {
		this.hour = hour;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public static class Field {
		private String name;
		private String message;

		public Field(String name, String message) {
			super();
			this.name = name;
			this.message = message;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
