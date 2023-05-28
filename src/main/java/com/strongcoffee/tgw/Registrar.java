package com.strongcoffee.tgw;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * The relationship between handlers and events is many-to-many. That is, each handler may register for zero
 * or more events, and each event may invoke zero or more handlers.
 */
public class Registrar {

	/**
	 * Each handler is registered for a set of events.
	 */
	HashMap< EventHandler, HashSet< String > > eventHandlers = new HashMap<>();

	/**
	 * Set from within the Handler's handleRequest() method before the notify() method is called. This string
	 * is passed to the registered event handlers when the Registrar notifies them that an event has occurred.
	 */
	Map<String, String> queryStringParameters = new HashMap<>();

	/**
	 *
	 */
	HashMap<String, String> dataStore = new HashMap<>();

	/**
	 * Constructor.
	 */
	public Registrar() {}

	/**
	 * @param eventHandler A reference to a lambda function. Attached to a UI option. Event handler is called by the notify() method when
	 *                     one of the events for the handler is registered occurs.
	 * @param eventHandlerId ...
	 * @param event An occurrence of this event will cause eventHandler to be invoked.
	 */
	public void add( EventHandler eventHandler, String eventHandlerId, String event ) {

		if( ! eventHandlers.containsKey( eventHandler ) ) {
			HashSet< String > events = new HashSet<>();
			events.add( event );
			eventHandlers.put( eventHandler, events );
		} else {
			eventHandlers.get( eventHandler ).add( event );
		}
	}

	/**
	 * @param event .
	 */
	public void notify( String event ) {

		for( HashMap.Entry< EventHandler, HashSet<String> > entry : eventHandlers.entrySet() ) {
			for( int i = 0; i < eventHandlers.get( entry.getKey() ).size(); i++ ) {                 // size() is that of the set of events for this eventHandler.
				if( eventHandlers.get( entry.getKey() ).contains( event ) ) {
					entry.getKey().execute( this.queryStringParameters );
				}
			}
		}
	}

	/**
	 * @param inQueryStringParameters .
	 */
	public void setQueryStringParameters( Map< String, String > inQueryStringParameters ) {
		this.queryStringParameters = inQueryStringParameters;
	}
}
