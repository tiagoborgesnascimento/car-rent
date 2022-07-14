package br.com.techborges.carrental.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.gson.Gson;

import br.com.techborges.carrental.dto.CarDTO;
import br.com.techborges.carrental.exception.ApiException;
import br.com.techborges.carrental.service.CarService;

@WebMvcTest(CarController.class)
public class CarControllerTest {

	@Autowired
	private MockMvc mvc;
	
	private Gson gson = new Gson();
	
	@MockBean
	private CarService service;
	
	@Test
	void shouldFindCarById() throws Exception {
		
		CarDTO carResponse = new CarDTO();
		carResponse.setAvailable(true);
		carResponse.setBrand("test brand");
		carResponse.setId(1L);
		carResponse.setModel("test model");
		
		when(service.findCarById(1L)).thenReturn(carResponse);
		
		mvc.perform(get("/cars/1"))
					.andExpect(content().json(gson.toJson(carResponse)))
					.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	void shouldNotFindCarById() throws Exception {
		
		when(service.findCarById(1L)).thenThrow(new ApiException("Car not found", HttpStatus.NOT_FOUND));
		
		mvc.perform(get("/cars/1"))
					.andExpect(status().is4xxClientError());
	}
	
	@Test
	void shouldFindCarByModelAndAvailability() throws Exception {
		
		when(service.findCarByModelAndAvailability("gol", Boolean.TRUE)).thenReturn(List.of());
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("model", "gol");
		params.add("availability", "true");
		
		mvc.perform(get("/cars")
				.params(params))
							.andExpect(content().json(gson.toJson(List.of())))
							.andExpect(status().is2xxSuccessful());	
	}
	
	@Test
	void shouldCreateCar() throws Exception {
		
		CarDTO carRequest = new CarDTO();
		carRequest.setAvailable(true);
		carRequest.setBrand("test brand");
		carRequest.setId(1L);
		carRequest.setModel("test model");
		
		when(service.saveNewCar(carRequest)).thenReturn(carRequest);
		
		mvc.perform(post("/cars")
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(carRequest)))
					.andExpect(content().json(gson.toJson(carRequest)))
					.andExpect(status().is(201));
	}
	
	@Test
	void shouldUpdateCar() throws Exception {
		
		CarDTO carRequest = new CarDTO();
		carRequest.setAvailable(true);
		carRequest.setBrand("test brand");
		carRequest.setId(1L);
		carRequest.setModel("test model");

		mvc.perform(put("/cars")
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(carRequest)))
					.andExpect(status().is2xxSuccessful());
	}
}
