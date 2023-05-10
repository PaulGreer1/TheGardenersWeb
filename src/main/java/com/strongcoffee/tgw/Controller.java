package com.strongcoffee.tgw;

/**
 *
 */
interface EventHandler {
	/**
	 * @param query .
	 * @return ...
	 */
	boolean execute( String query );
}

/**
 *
 */
public class Controller {

	public Controller( Registrar registrar ) {

		String[] events = { "INSERT_GARDEN", "UPDATE_GARDEN", "DELETE_GARDEN", "GET_GARDEN", "GET_GARDENS" };

		registrar.add( insertGarden, "1", events[0] );
		registrar.add( insertGarden, "1", events[1] );
		//registrar.add( deleteGarden, "1", events[2] );
		//registrar.add( deleteGarden, "1", events[2] );
		//registrar.add( deleteGarden, "1", events[2] );
	}

	EventHandler insertGarden = (query) -> {
		System.out.println( query + " Alice." );
		return true;
	};

	EventHandler updateGarden = (query) -> {
		System.out.println( query + " Bill." );
		return true;
	};

	EventHandler deleteGarden = (query) -> {
		System.out.println( query + " Carrol." );
		return true;
	};

	EventHandler getGarden = (query) -> {
		System.out.println( query + " Dodo." );
		return true;
	};

	EventHandler getGardens = (query) -> {
		System.out.println( query + " Mad Hatter." );
		return true;
	};
}
