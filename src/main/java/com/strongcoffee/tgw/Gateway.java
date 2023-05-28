package com.strongcoffee.tgw;

import java.util.HashMap;
import java.util.Map;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

/**
 * Simulate an HTTP API Gateway. Send the APIGatewayV2HTTPEvent HashMap<String, String> queryStringParameters with some
 * UI data that will cause registered event handlers to be notified and invoked.
 */
public class Gateway {

	/**
	 *
	 */
	static Map<String, String> queryStringParameters;
	static APIGatewayV2HTTPEvent event = new APIGatewayV2HTTPEvent();
	static APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
	static Context context = null;
	static Handler handler = new Handler();

	public static void main( String[] args ) {

		// Some sample requests.

		// Get all the gardens in the database. A string
		// is returned. The client must split this string on the hash ('#') and
		// pipe ('|') characters to get at the data.

		queryStringParameters = new HashMap<String, String>();
		queryStringParameters.put( "ui_event", "GET_GARDENS" );
		event.setQueryStringParameters( queryStringParameters );
		response = handler.handleRequest( event, context );
		response = handler.handleRequest( event, context );

		/******************************************************
		// Insert some gardens into the database. Run these individually or
		// or one at a time.

		queryStringParameters = new HashMap<String, String>();
		queryStringParameters.put( "ui_event", "INSERT_GARDEN" );
		queryStringParameters.put( "garden_name", "Alice's garden" );
		queryStringParameters.put( "description", "The perfect place for a very happy unbirthday." );
		event.setQueryStringParameters( queryStringParameters );
		response = handler.handleRequest( event, context );

		//queryStringParameters = new HashMap<String, String>();
		queryStringParameters.put( "ui_event", "INSERT_GARDEN" );
		queryStringParameters.put( "garden_name", "Bill's garden" );
		queryStringParameters.put( "description", "A garden for lazy lizards." );
		event.setQueryStringParameters( queryStringParameters );
		response = handler.handleRequest( event, context );

		//queryStringParameters = new HashMap<String, String>();
		queryStringParameters.put( "ui_event", "INSERT_GARDEN" );
		queryStringParameters.put( "garden_name", "Carroll's garden" );
		queryStringParameters.put( "description", "As many interpretations as there are people." );
		event.setQueryStringParameters( queryStringParameters );
		response = handler.handleRequest( event, context );

		//queryStringParameters = new HashMap<String, String>();
		queryStringParameters.put( "ui_event", "INSERT_GARDEN" );
		queryStringParameters.put( "garden_name", "David's garden" );
		queryStringParameters.put( "description", "A small space with big diversity." );
		event.setQueryStringParameters( queryStringParameters );
		response = handler.handleRequest( event, context );

		******************************************************/

		/******************************************************
		// Delete a single garden from the database.
		
		queryStringParameters = new HashMap<String, String>();
		queryStringParameters.put( "ui_event", "DELETE_GARDEN" );
		queryStringParameters.put( "garden_id", "5" );
		event.setQueryStringParameters( queryStringParameters );
		response = handler.handleRequest( event, context );
		******************************************************/

		/******************************************************
		// Delete a set of gardens from the database. The
	        // garden_ids value passed to queryStringParameters.put() method is a set
	        // of comma-separated integers. The event handler for this request will
	        // accept only commas and integers.

		// ""  "asfd"  "a,3,4"  "1,4"  "5"
		queryStringParameters = new HashMap<String, String>();
		queryStringParameters.put( "ui_event", "DELETE_GARDENS" );
		queryStringParameters.put( "garden_ids", "1,2,8" );
		event.setQueryStringParameters( queryStringParameters );
		response = handler.handleRequest( event, context );
		******************************************************/

		System.out.println( response.getBody() );
	}
}
