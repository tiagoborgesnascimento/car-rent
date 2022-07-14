package br.com.techborges.carrental.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.techborges.carrental.domain.CarDomain;
import br.com.techborges.carrental.dto.CarDTO;
import br.com.techborges.carrental.exception.ApiException;
import br.com.techborges.carrental.repository.CarRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarService {

	private final CarRepository repository;
	
	private final ModelMapper mapper;
	
	public synchronized CarDTO findCarById(final Long id) throws Exception {
		
		CarDomain carDomain = repository.findById(id).orElseThrow(() -> new ApiException("Car not found", HttpStatus.NOT_FOUND));
		return toDTO(carDomain);
	}

	public synchronized List<CarDTO> findCarByModelAndAvailability(final String model, final Boolean available){
		
		List<CarDomain> carDomains = repository.findByModelAndAvailable(model, available);
		return carDomains.stream()
						 .map(domain -> toDTO(domain))
						 .collect(Collectors.toList());
	}
	
	
	public synchronized CarDTO saveNewCar(final CarDTO carDTO) throws Exception {
		
		CarDomain carDomain = repository.save(toDomain(carDTO));
		CarDTO dto = toDTO(carDomain);
		return dto;
	}
	
	public synchronized void updateCar(CarDTO carDTO) throws ApiException {
		try {
			repository.save(toDomain(carDTO));			
		}
		catch (DataAccessException e) {
			throw new ApiException("Car entity with id " + carDTO.getId().toString() + " not exists!", HttpStatus.BAD_REQUEST);
		}
	}
	
	public synchronized void deleteCarById(final Long id) throws ApiException {
		try {
			repository.deleteById(id);			
		}
		catch (DataAccessException e) {
			throw new ApiException("Car entity with id " + id.toString() + " not exists!", HttpStatus.BAD_REQUEST);
		}
	}
	
	public CarDomain toDomain(CarDTO carDTO) {
		return mapper.map(carDTO, CarDomain.class);
	}
	
	public CarDTO toDTO(CarDomain carDomain) {
		return mapper.map(carDomain, CarDTO.class);
	}
	
}
