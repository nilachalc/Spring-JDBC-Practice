package com.pluralsight.repository.util.preparedstatementcreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.pluralsight.model.Ride;

public class RidePreparedStatementCreator implements PreparedStatementCreator {

	private Ride ride;
	
	public RidePreparedStatementCreator(Ride ride) {
		super();
		this.ride = ride;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO ride (name, duration) VALUES (?, ?)"
				, new String[] {"id"});
		
		preparedStatement.setString(1, ride.getName());
		preparedStatement.setInt(2, ride.getDuration());
		
		return preparedStatement;
	}

}
