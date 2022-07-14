package br.com.techborges.carrental.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiException extends Exception {

	private static final long serialVersionUID = -4648431001188419134L;
	
	private HttpStatus status;
	
	public ApiException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
}
