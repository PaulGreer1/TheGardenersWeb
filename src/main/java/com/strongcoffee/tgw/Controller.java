package com.strongcoffee.tgw;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.Integer.valueOf;

/**
 *
 */
interface EventHandler {
	/**
	 * @param input .
	 * @return A boolean which can be used to indicate the success or failure of the method.
	 */
	boolean execute( Map<String, String> input );
}

/**
 * References to these lambda functions are stored with the Registrar object. Each handler is registered for a set of events. When an
 * event for which a handler is registered occurs, the Registrar will call the handler.
 */
public class Controller {

	String url = "jdbc:mysql://localhost:3306/TheGardenersWeb";
	String username = "root";
	String password = "";
	Statement statement;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	Connection con;
	HashMap<String, String> dataStore = new HashMap<>();
	ArrayList<String> gardens = new ArrayList<String>();
	String dbOutput = "";
	//EventHandlerOutputBucket eventHandlerOutputBucket;
	Registrar registrar;

	/**
	 * @param eventHandlerOutputBucket .
	 */
	//public void setEventHandlerOutputBucket( EventHandlerOutputBucket eventHandlerOutputBucket ) {
	//	this.eventHandlerOutputBucket = eventHandlerOutputBucket;
	//}

	/**
	 * @param registrar .
	 */
	public Controller( Registrar registrar ) {

		String[] events = { "INSERT_GARDEN", "UPDATE_GARDEN", "DELETE_GARDEN", "GET_GARDEN", "GET_GARDENS", "DELETE_GARDENS" };
		this.registrar = registrar;

		// Register event handlers to be called back when events occur.
		registrar.add( insertGarden, "1", events[0] );
		registrar.add( deleteGarden, "1", events[2] );
		registrar.add( getGardens, "1", events[4] );
		registrar.add( deleteGardens, "1", events[5] );
	}

	/**
	 *
	 */
	EventHandler getGardens = (input) -> {

		//gardens.clear();

		//
		try {
			con = DriverManager.getConnection(url, username, password);

			// Start transaction.

			//
			String sql = "SELECT * FROM garden";
			statement = con.createStatement();
			resultSet = statement.executeQuery( sql );
			while( resultSet.next() ) {
				gardens.add( resultSet.getObject( "garden_id" ).toString() + "|" +
							 resultSet.getObject( "garden_name" ).toString() + "|" +
							 resultSet.getObject( "description" ).toString() );
			}

			// End transaction.

			con.close();
		}
		catch ( Exception e ) {
			this.registrar.dataStore.put( "dbOutput", e.getMessage() );
		}

		this.registrar.dataStore.put( "dbOutput", gardens.toString() );
		//this.dbOutput += gardens.toString();

		return true;
	};

	/**
	 *
	 */
	EventHandler deleteGarden = (input) -> {

		// Delete a Garden from the database.
		try {
			con = DriverManager.getConnection(url, username, password);

			// Start transaction.

			//
			String tableName = "garden";
			String colName = "garden_id";
			int gardenId = Integer.parseInt( input.get( "garden_id" ) );

			// Delete the row with the given garden_id from the garden table.
			String sql = "DELETE FROM garden WHERE garden_id = ?";
			preparedStatement = con.prepareStatement( sql );
			preparedStatement.setInt( 1, gardenId );
			int row = preparedStatement.executeUpdate();

			// End transaction.

			con.close();
		}
		catch ( Exception e ) {
			this.dbOutput += e.getMessage();
		}

		return true;
	};

	/**
	 *
	 */
	EventHandler deleteGardens = (input) -> {

		// Specify a pattern for approved input.
		Pattern pattern = Pattern.compile( "^[\\d,]+$" );
		Matcher matcher;

		// Delete a set of Gardens from the database.
		try {
			con = DriverManager.getConnection(url, username, password);

			// Start transaction.

			//
			String garden_ids = input.get( "garden_ids" );
			System.out.println( garden_ids );

			//
			matcher = pattern.matcher( garden_ids );
			if( matcher.matches() ) {
			// Delete the rows in the given set of gardens.
				String sql = "DELETE FROM garden WHERE garden_id IN (" + garden_ids + ")";
				this.statement = this.con.prepareStatement( sql );
				this.statement.executeUpdate( sql );
			} else {
				System.out.println( "Not matched" );
			}

			// No need to do it the following way because it's easy to specify the input we want.
			/*
			String sql = "DELETE FROM garden WHERE garden_id IN (?)";
			preparedStatement = con.prepareStatement( sql );
			preparedStatement.setString( 1, garden_ids );
			int row = preparedStatement.executeUpdate();
			*/

			// End transaction.

			con.close();
		}
		catch ( Exception e ) {
			this.registrar.dataStore.put( "dbOutput", e.getMessage() );
		}

		return true;
	};

	/**
	 * @param tableName .
	 * @param colName .
	 * @return .
	 */
	private int getTableId( String tableName, String colName ) {

		int newTableId = 0;

		// Create a unique ID for the new row. First get the existing set of table IDs.
		ArrayList<Integer> tableIds = new ArrayList<Integer>();
		String sql = "SELECT " + colName + " FROM " + tableName;

		//
		try {
			this.statement = this.con.createStatement();
			this.resultSet = this.statement.executeQuery( sql );
			while( resultSet.next() ) {
				tableIds.add( valueOf( resultSet.getObject( colName ).toString() ) );
			}
		}
		catch ( Exception e ) {
			this.dbOutput += e.getMessage();
		}

		newTableId = TgwUtils.createId( tableIds );

		return newTableId;
	}

	/**
	 *
	 */
	EventHandler insertGarden = (input) -> {

		// Insert a Garden into the database.
		try {
			con = DriverManager.getConnection( url, username, password );

			// Start transaction.

			//
			String tableName = "garden";
			String colName = "garden_id";
			int newGardenId = this.getTableId( tableName, colName );
			String gardenName = input.get( "garden_name" );
			String description = input.get( "description" );

			// Insert the new garden into the garden table.
			String sql = "INSERT INTO garden ( garden_id, garden_name, description ) VALUES ( ?, ?, ? )";
			preparedStatement = con.prepareStatement( sql );
			preparedStatement.setInt( 1, newGardenId );
			preparedStatement.setString( 2, gardenName );
			preparedStatement.setString( 3, description );
			int row = preparedStatement.executeUpdate();

			// End transaction.

			con.close();
		}
		catch ( Exception e ) {
			this.dbOutput += e.getMessage();
		}

		return true;
	};

	/**
	 *
	 */
	EventHandler updateGarden = (input) -> {

		// Update a Garden in the database.
		// ...

		return true;
	};

	/**
	 *
	 */
	EventHandler getGarden = (input) -> {

		// Get a Garden from the database.
		// ...

		return true;
	};
}
