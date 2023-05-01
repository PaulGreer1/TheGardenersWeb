package com.strongcoffee.tgw;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;          // For HTTP input.
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;       // For HTTP output.
import java.util.HashMap;

public class Main {

	public static void main( String[] args ) {
		System.out.println( "Hello world!\n" );

		HashMap<String, String> queryStringParameters = new HashMap<String, String>();
		APIGatewayV2HTTPEvent event = new APIGatewayV2HTTPEvent();
		event.setQueryStringParameters( queryStringParameters );
		Context context = null;

		Handler handler = new Handler();
		handler.handleRequest( event, context );

	}
}
