package br.com.techborges.carrental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.techborges.carrental.dto.CarDTO;
import br.com.techborges.carrental.exception.ApiException;
import br.com.techborges.carrental.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
@Api(value = "Car controller", description = "Car operations", tags = "Car Controller")
public class CarController {

	private final CarService service;

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Find car by id", response = CarDTO.class)
	public ResponseEntity<CarDTO> findCarById(@PathVariable final Long id) throws Exception {

		CarDTO carDTO = service.findCarById(id);
		return new ResponseEntity<CarDTO>(carDTO, HttpStatus.OK);
	}

	@GetMapping
	@ApiOperation(value = "Find car by model and availability", response = CarDTO.class)
	public ResponseEntity<List<CarDTO>> findCarByModelAndAvailability(@RequestParam(required = true) String model, 
																	  @RequestParam(required = true, defaultValue = "false") Boolean availability) throws Exception {

		List<CarDTO> carDTOs = service.findCarByModelAndAvailability(model, availability);
		return new ResponseEntity<List<CarDTO>>(carDTOs, HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Create a new Car", response = CarDTO.class)
	public ResponseEntity<CarDTO> createNewCar(@RequestBody final CarDTO carDTO) throws Exception {

		CarDTO savedCarDTO = service.saveNewCar(carDTO);
		return new ResponseEntity<CarDTO>(savedCarDTO, HttpStatus.CREATED);
	}

	@PutMapping
	@ApiOperation(value = "Update a car", response = CarDTO.class)	
	public ResponseEntity<Void> updateCarById(@RequestBody final CarDTO carDTO) throws ApiException {

		service.updateCar(carDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Delete a car", response = CarDTO.class)
	public ResponseEntity<Void> deleteCarById(@PathVariable final Long id) throws ApiException {

		service.deleteCarById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
