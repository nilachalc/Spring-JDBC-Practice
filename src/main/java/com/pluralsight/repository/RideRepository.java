package com.pluralsight.repository;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideRepository {

	Ride createRide(Ride ride);
	
	List<Ride> getRides();

	Ride getRideById(Integer id);

	Ride updateRide(Ride ride);

	void batchUpdateRide(List<Object[]> params);

	void deleteRide(Integer id);
}