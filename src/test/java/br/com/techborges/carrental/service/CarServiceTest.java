package br.com.techborges.carrental.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.techborges.carrental.domain.CarDomain;
import br.com.techborges.carrental.dto.CarDTO;
import br.com.techborges.carrental.repository.CarRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
public class CarServiceTest {

	@Mock
	private CarRepository repository;
	
	@Mock
	private ModelMapper mapper;
	
	@InjectMocks
	private CarService carService;
	
	@Test
	void findCarById() throws Exception {
		
		CarDomain domain = new CarDomain();
		domain.setId(1L);
		domain.setAvailable(Boolean.TRUE);
		domain.setCreatedDate(new Date());
		domain.setModel("model");
		domain.setBrand("brand");

		ModelMapper mapperCar = new ModelMapper();
		CarDTO carConverted = mapperCar.map(domain, CarDTO.class);

		when(repository.findById(1L)).thenReturn(Optional.of(domain));
		when(mapper.map(domain, CarDTO.class)).thenReturn(carConverted);
				
		CarDTO car = carService.findCarById(1L);
		
		assertNotNull(car);
		assertEquals(carConverted, car);

	}
	
	@Test
	void saveNewCar() throws Exception {
		
		CarDTO carDTO = new CarDTO();
		carDTO.setAvailable(Boolean.TRUE);
		carDTO.setCreatedDate(new Date());
		carDTO.setModel("model");
		carDTO.setBrand("brand");
		
		ModelMapper mapperCar = new ModelMapper();
		
		CarDomain carDomain = mapperCar.map(carDTO, CarDomain.class);
		when(mapper.map(carDTO, CarDomain.class)).thenReturn(carDomain);
		
		when(repository.save(carDomain)).thenReturn(carDomain);
		CarDTO carDtoConverted = mapperCar.map(carDomain, CarDTO.class);
		carDtoConverted.setId(new Random().nextLong());
		when(mapper.map(carDomain, CarDTO.class)).thenReturn(carDtoConverted);				
		
		CarDTO carSaved = carService.saveNewCar(carDTO);
		assertNotNull(carSaved);
	}
	
	@Test
	void updateCar() throws Exception {
		
		CarDTO carDTO = new CarDTO();
		carDTO.setAvailable(Boolean.TRUE);
		carDTO.setCreatedDate(new Date());
		carDTO.setModel("model");
		carDTO.setBrand("brand");
		carDTO.setId(new Random().nextLong());
		
		ModelMapper mapperCar = new ModelMapper();
		
		CarDomain carDomain = mapperCar.map(carDTO, CarDomain.class);
		when(mapper.map(carDTO, CarDomain.class)).thenReturn(carDomain);
		
		when(repository.save(carDomain)).thenReturn(carDomain);
		CarDTO carDtoConverted = mapperCar.map(carDomain, CarDTO.class);
		when(mapper.map(carDomain, CarDTO.class)).thenReturn(carDtoConverted);				
		
		carService.updateCar(carDTO);		
	}
	
	
	@Test
	void deleteCarById()  {
		
		doNothing().when(repository).deleteById(1L);
		repository.deleteById(1L);
	}
	
}
