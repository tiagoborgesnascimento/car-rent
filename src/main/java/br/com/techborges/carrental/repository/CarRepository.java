package br.com.techborges.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.techborges.carrental.domain.CarDomain;

public interface CarRepository extends JpaRepository<CarDomain, Long>{

	public List<CarDomain> findByModelAndAvailable(String model, Boolean Available);
}
