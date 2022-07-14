package br.com.techborges.carrental.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "car")
public class CarDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String brand;
	
	@Column
	private String model;
	
	@Column
	private Boolean available;
	
	@CreatedDate
	@Column(updatable = false)
	private Date createdDate;
	
	@PrePersist
	private void beforeCreate() {
		this.createdDate = new Date();
	}
}
