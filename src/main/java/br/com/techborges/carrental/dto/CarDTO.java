package br.com.techborges.carrental.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarDTO {

	private Long id;
	
	private String brand;
	
	private String model;
	
	private Boolean available;
	
	private Date createdDate;
}
