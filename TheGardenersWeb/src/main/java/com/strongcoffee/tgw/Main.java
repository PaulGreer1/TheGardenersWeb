package com.strongcoffee.tgw;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;          // For HTTP input.
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;       // For HTTP output.
import java.util.HashMap;

public class Main {

	public static void main( String[] args ) {
		System.out.println( "Hello world!\n" );

		HashMap<String, String> qsParams = new HashMap<String, String>();
		APIGatewayV2HTTPEvent event = new APIGatewayV2HTTPEvent();
		event.setQueryStringParameters( qsParams );
		Context context = null;

		Handler handler = new Handler();
		handler.handleRequest( event, context );

	}
}

/*
https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.1/index.html
https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html
https://docs.aws.amazon.com/lambda/latest/dg/services-apigateway.html
https://docs.aws.amazon.com/apigateway/latest/developerguide/http-api.html
*/