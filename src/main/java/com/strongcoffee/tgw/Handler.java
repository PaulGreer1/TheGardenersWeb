package com.strongcoffee.tgw;

import java.util.HashMap;
import java.util.Map;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

/**
 *
 */
public class Handler implements RequestHandler< APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse > {

	/*
	The following can be initialised/instantiated before handleRequest() is invoked because they don't rely on
	input from the gateway.
	*/
	Registrar registrar = new Registrar();
	Controller controller = new Controller( registrar );

	/**
	 * @param event   The Lambda Function input. Includes input about the UI event which occurred. This will determine which
	 *                event handlers will be invoked.
	 * @param context The Lambda execution environment context object.
	 * @return .
	 */
	@Override
	public APIGatewayV2HTTPResponse handleRequest( APIGatewayV2HTTPEvent event, Context context ) {

		System.out.println( registrar.queryStringParameters );

		Map<String, String> queryStringParameters = event.getQueryStringParameters();
		APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();

		// Set the Registrar's queryStringParameters member variable.
		registrar.setQueryStringParameters( queryStringParameters );

		// Notify registered event handlers of the event.
		registrar.notify( queryStringParameters.get( "ui_event" ) );

		// Build a string to send back to the Gateway.
		StringBuilder output = new StringBuilder();
		for( HashMap.Entry< String, String > entry : this.registrar.dataStore.entrySet() ) {
			output.append( entry.getKey() );
			output.append( "#" );
			output.append( this.registrar.dataStore.get( entry.getKey() ) );
		}
		response.setBody( output.toString() );

		return response;
	}

	/**
	 *
	 */
	//EventHandlerOutputBucket eventHandlerOutputBucket = () -> controller.dbOutput;

}
