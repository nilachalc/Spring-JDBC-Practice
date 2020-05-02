package com.pluralsight.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;

@Service("rideService")
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;
	
	@Override
	public Ride createRide(Ride ride) {
		return rideRepository.createRide(ride);
	}
	
	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}

	@Override
	public Ride getRide(Integer id) {
		return rideRepository.getRideById(id);
	}
	
	@Override
	public Ride updateRide(Ride ride) {
		return rideRepository.updateRide(ride);
	}

	@Override
	public void batchUpdateRide() {
		List<Ride> rides = rideRepository.getRides();
		
		List<Object[]> params = new ArrayList<Object[]>();
		
		for (Ride ride : rides) {
			
			Object[] param = {new Date(), ride.getId()};
			
			params.add(param);
		}
		
		rideRepository.batchUpdateRide(params);
	}

	@Override
	@Transactional
	public void deleteRide(Integer id) {
		rideRepository.deleteRide(id);
		throw new DataAccessResourceFailureException("Jhar.");
	}
}
