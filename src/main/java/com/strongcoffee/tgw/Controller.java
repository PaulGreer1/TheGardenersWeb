package com.strongcoffee.tgw;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
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
	//StringBuilder stringBuilder;
	ArrayList<String> gardens = new ArrayList<String>();

	/**
	 * @param registrar .
	 */
	public Controller( Registrar registrar ) {

		//stringBuilder = new StringBuilder();

		String[] events = { "INSERT_GARDEN", "UPDATE_GARDEN", "DELETE_GARDEN", "GET_GARDEN", "GET_GARDENS" };

		// Register event handlers to be called back when events occur.
		registrar.add( insertGarden, "1", events[0] );
		registrar.add( deleteGarden, "1", events[2] );
	}

	/**
	 *
	 */
	EventHandler getGardens = (input) -> {

		System.out.println( input );

		gardens.clear();

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
			System.out.println( e.getMessage() );
		}

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
			System.out.println( e.getMessage() );
		}

		return true;
	};

	/**
	 *
	 */
	EventHandler insertGarden = (input) -> {

		System.out.println( input );

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
	EventHandler getGarden = (input) -> {

		System.out.println( input + " Dodo." );

		// Get a Garden from the database.
		// ...

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
			System.out.println( e.getMessage() );
		}

		newTableId = TgwUtils.createId( tableIds );

		return newTableId;
	}
}
