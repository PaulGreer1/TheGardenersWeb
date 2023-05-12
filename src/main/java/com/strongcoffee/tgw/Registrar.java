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
	 *
	 */
	Map<String, String> queryStringParameters = new HashMap<String, String>();

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
	 * @param event ...
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

/*
Plain English can be enough. But mathematical expressions are less ambiguous and move us further towards
code - i.e. something that a machine can understand. But if you are convinced that you understand the system, then
you should be able to write effective tests from plain English post-conditions.

Post-conditions don't need to be restricted to conditions which need to hold after individual methods or functions.
It is perfectly fine to specify post-conditions which should hold after a sequence of actions has been executed. Use
sequence diagrams.

H = { The set of registered handlers }
V = { The set of events for a particular handler }

A h in H [ A v in V [ v not in { V DIFF { v } } ] ]                             // [1]
/\
A h in H [ h not in { H DIFF { h } } ]                                          // [2]

[1] Each element in a particular handler's set of events is unique in that set.

[2] Each handler in the set of registered handlers is unique.

*/




















































/*
A e in E [ ( OCCURRED(e) /\ E h in H [ h is registered for e ] ) =>             // [3]

[3] If an event, e, occurs, and there exists a handler h registered for e, then h is invoked exactly once. We wouldn't want, for example, a database write to occur more than once. Ensuring sets without duplicates is enough, but no harm in further testing.

*/

	/*
	ArrayList< EventHandler > eventHandlers = new ArrayList<>();
	ArrayList< String > events = new ArrayList<>();
	*/

//public void add( EventHandler eventHandler, String event ) {

		/*
		for( int i = 0; i < eventHandlers.size(); i++ ) {
			if( Objects.equals( events.get(i), event ) ) {
				eventHandlers.get(i).execute( "Hello" );
			}
		}
		*/

		/*
		this.eventHandlers.add( eventHandler );                                                 // Implicitly marries the EventHandler method signature to a lambda method body.
		this.events.add( event );
		*/


//String[] events = { "INSERT_GARDEN", "UPDATE_GARDEN", "DELETE_GARDEN", "GET_GARDEN", "GET_GARDENS" };
//HashMap< EventHandler, String[] > eventHandlers = new HashMap<>();
//HashMap< EventHandler, ArrayList< String > > eventHandlers = new HashMap<>();
//ArrayList< String > events;

		/*
		if( ! eventHandlers.containsKey( eventHandler ) ) {
			ArrayList< String > events = new ArrayList<>();
			events.add( event );
			eventHandlers.put( eventHandler, events );
		} else {
			eventHandlers.get( eventHandler ).add( event );
		}
		*/


		/*
		String[] idEventPair = { eventHandlerId, event };
		this.eventHandlers.put( eventHandler, idEventPair );
		*/

		/*
		for( HashMap.Entry< EventHandler, ArrayList<String> > entry : eventHandlers.entrySet() ) {
			for( int i = 0; i < eventHandlers.get( entry.getKey() ).size(); i++ ) {
				if( event == eventHandlers.get( entry.getKey() ).get(i) ) {
					entry.getKey().execute( "Hello" );
					break;
				}
			}
		}
		*/

		/*
		String myEvent;
		for( HashMap.Entry< EventHandler, String[] > entry : eventHandlers.entrySet() ) {
			myEvent = entry.getValue()[1];
			if( Objects.equals( myEvent, event ) ) {
				//entry.getKey().execute( "Hello" );
				for each event e {
					if the handler (entry.getKey()) is registered for e {
						...
						break
					}
				}
			}
		}
		*/