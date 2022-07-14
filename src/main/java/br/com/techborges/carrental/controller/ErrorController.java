package br.com.techborges.carrental.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.techborges.carrental.dto.ErrorMessageDTO;
import br.com.techborges.carrental.exception.ApiException;

@RestControllerAdvice
public class ErrorController {

	@ExceptionHandler(value = {ApiException.class})
	public ResponseEntity<ErrorMessageDTO> onApiException(ApiException apiException) {
		
		return new ResponseEntity<ErrorMessageDTO>(new ErrorMessageDTO(apiException.getMessage(), new Date()), apiException.getStatus());
	}
}
