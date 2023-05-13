package com.strongcoffee.tgw;

import java.sql.*;
import java.util.Map;

/**
 *
 */
interface EventHandler {
	/**
	 * @param input .
	 * @return ...
	 */
	boolean execute( Map<String, String> input );
}

/**
 * References to these lambda functions are stored with the Registrar object. Each handler is registered for a set of events. When an
 * event for which a handler is registered for occurs, the Registrar will call the handler.
 */
public class Controller {

	String url = "jdbc:mysql://localhost:3306/TheGardenersWeb";
	String username = "root";
	String password = "";
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	Connection con;
	StringBuilder stringBuilder;

	/**
	 * @param registrar .
	 */
	public Controller( Registrar registrar ) {

		stringBuilder = new StringBuilder();

		String[] events = { "INSERT_GARDEN", "UPDATE_GARDEN", "DELETE_GARDEN", "GET_GARDEN", "GET_GARDENS" };

		// Register event handlers to be called back when events occur.
		registrar.add( insertGarden, "1", events[0] );
		registrar.add( deleteGarden, "1", events[2] );
	}

	/**
	 *
	 */
	EventHandler insertGarden = (input) -> {

		System.out.println( input );

		// Insert a Garden into the database.
		try {
			con = DriverManager.getConnection( url, username, password );
			String sql = "INSERT INTO garden ( garden_id, garden_name, description ) VALUES ( ?, ?, ? )";
			preparedStatement = con.prepareStatement( sql );
			preparedStatement.setInt( 1, 2 );
			preparedStatement.setString( 2, "Janie's Garden" );
			preparedStatement.setString( 3, "A garden for little birds." );
			int row = preparedStatement.executeUpdate();
			con.close();
		}
		catch ( Exception e ) {
			System.out.println( e.getMessage() );
		}

		return true;
	};

	/**
	 *
	 */
	EventHandler updateGarden = (input) -> {

		System.out.println( input + " Bill." );

		// Update a Garden in the database.
		// ...

		return true;
	};

	/**
	 *
	 */
	EventHandler deleteGarden = (input) -> {

		System.out.println( input );

		// Delete a Garden from the database.
		try {
			con = DriverManager.getConnection(url, username, password);
			String sql = "DELETE FROM garden WHERE garden_id = ?";
			preparedStatement = con.prepareStatement( sql );
			preparedStatement.setInt( 1, 2 );
			int row = preparedStatement.executeUpdate();
			con.close();
		}
		catch ( Exception e ) {
			System.out.println( e.getMessage() );
		}

		return true;
	};

	/**
	 *
	 */
	EventHandler getGarden = (input) -> {

		System.out.println( input + " Dodo." );

		// Get a Garden from the database.
		// ...

		return true;
	};

	/**
	 *
	 */
	EventHandler getGardens = (input) -> {

		System.out.println( input + " Mad Hatter." );

		// Get a set of Gardens from the database.
		// ...

		return true;
	};
}
