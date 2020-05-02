package com.pluralsight.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.preparedstatementcreator.RidePreparedStatementCreator;
import com.pluralsight.repository.util.rowmapper.RideRowMaper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private Map<String, Object> params;
	
	/*
	 * @Autowired private SimpleJdbcInsert insert;
	 */
	
	@Override
	public Ride createRide(Ride ride) {
		/*
		 * insert.setTableName("ride");
		 * 
		 * List<String> columns = new ArrayList<String>(); columns.add("name");
		 * columns.add("duration"); insert.setColumnNames(columns);
		 * 
		 * Map<String, Object> data = new HashMap<String, Object>(); data.put("name",
		 * ride.getName()); data.put("duration", ride.getDuration());
		 * insert.setGeneratedKeyName("id");
		 * 
		 * System.out.println(insert.executeAndReturnKey(data));
		 */
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new RidePreparedStatementCreator(ride), keyHolder);
		
		return getRideById(keyHolder.getKey().intValue());
	}
	
	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("SELECT * FROM ride", new RideRowMaper());
		return rides;
	}

	@Override
	public Ride getRideById(Integer newId ) {
		params = new HashMap<String, Object>();
		params.put("id", newId);
		
		return namedParameterJdbcTemplate.queryForObject("SELECT * FROM ride WHERE id = :id", params, new RideRowMaper());
	}
	
	@Override
	public Ride updateRide(Ride ride) {
		params = new HashMap<String, Object>();
		params.put("name", ride.getName());
		params.put("duration", ride.getDuration());
		params.put("id", ride.getId());
		
		namedParameterJdbcTemplate.update("UPDATE ride SET name = :name, duration = :duration WHERE id = :id", params);
		return ride;
	}
	
	@Override
	public void batchUpdateRide(List<Object[]> params) {
		jdbcTemplate.batchUpdate("UPDATE ride SET ridedate = ? WHERE id = ?", params);
	}

	@Override
	public void deleteRide(Integer id) {
		params = new HashMap<String, Object>();
		params.put("id", id);

		namedParameterJdbcTemplate.update("DELETE FROM ride WHERE id = :id", params);
	}
}
