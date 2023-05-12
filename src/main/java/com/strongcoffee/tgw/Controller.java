package com.strongcoffee.tgw;

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
 * event for which a handler is registered occurs, the Registrar will call the handler.
 */
public class Controller {

	public Controller( Registrar registrar ) {

		String[] events = { "INSERT_GARDEN", "UPDATE_GARDEN", "DELETE_GARDEN", "GET_GARDEN", "GET_GARDENS" };

		// Register event handlers to be called back when events occur.
		registrar.add( insertGarden, "1", events[0] );
	}

	/**
	 *
	 */
	EventHandler insertGarden = (input) -> {

		System.out.println( input.get("ui_event") + " | " + input.get("garden_name") + " | " + input.get("description") );

		// Insert a Garden into the database.
		// ...

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

		System.out.println( input + " Carrol." );

		// Delete a Garden from the database.
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
	 *
	 */
	EventHandler getGardens = (input) -> {

		System.out.println( input + " Mad Hatter." );

		// Get a set of Gardens from the database.
		// ...

		return true;
	};
}
