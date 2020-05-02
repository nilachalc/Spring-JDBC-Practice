package com.pluralsight.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {

	@Test(timeout=300000)
	public void testCreateRides() {
		RestTemplate restTemplate = new RestTemplate();
		
		Ride ride = new Ride();
		
		ride.setName("Bombay ride");
		ride.setDuration(190);
		
		Ride newRide = restTemplate.postForObject("http://localhost:8082/ride_tracker/ride", ride, Ride.class);
		
		System.out.println("New Ride Created::");
		System.out.println("Ride name: " + newRide.getName());
		System.out.println("Ride Duration: " + newRide.getDuration());
	}
	
	@Test(timeout=300000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8082/ride_tracker/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
			System.out.println("Ride Duration: " + ride.getDuration());
		}
	}
	
	@Test(timeout=300000)
	public void testGetRide() {
		RestTemplate restTemplate = new RestTemplate();
		
		Ride ride = restTemplate.getForObject("http://localhost:8082/ride_tracker/ride/2", Ride.class);
		
		System.out.println("Ride Details::");
		System.out.println("Ride name: " + ride.getName());
		System.out.println("Ride Duration: " + ride.getDuration());
	}
	
	@Test(timeout=300000)
	public void testUpdateRide() {
		RestTemplate restTemplate = new RestTemplate();
		
		Ride ride = restTemplate.getForObject("http://localhost:8082/ride_tracker/ride/41", Ride.class);
		
		ride.setName("Bombay Ride");
		ride.setDuration(ride.getDuration() + 10);
		
		restTemplate.put("http://localhost:8082/ride_tracker/ride", ride);
	}
	
	@Test(timeout=300000)
	public void testBatchUpdateRide() {
		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.getForObject("http://localhost:8082/ride_tracker/ride/batch", Object.class);
	}
	
	@Test(timeout=300000)
	public void deleteRide() {
		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.delete("http://localhost:8082/ride_tracker/ride/delete/83");
	}
	
	@Test(timeout=300000)
	public void testException() {
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.getForObject("http://localhost:8082/ride_tracker/ride/test", Object.class);
			System.out.println("Status is OK.");
		} catch (Exception exception) {
			System.out.println("Exception Occured:: " + exception.getMessage());
		}
	}
}
