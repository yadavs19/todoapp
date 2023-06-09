package com.ibm.todoapp.dto;

import java.time.LocalDateTime;

public class ErrorResponse {

	private Integer status;
	private String errorMessage;
	private LocalDateTime timestamp;

	public ErrorResponse(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
