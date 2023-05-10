package com.strongcoffee.tgw;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;          // For HTTP input.
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;       // For HTTP output.
import java.util.HashMap;

public class Main {

	public static void main( String[] args ) {

		HashMap<String, String> queryStringParameters = new HashMap<String, String>();
		APIGatewayV2HTTPEvent event = new APIGatewayV2HTTPEvent();
		event.setQueryStringParameters( queryStringParameters );
		Context context = null;

		Handler handler = new Handler();
		handler.handleRequest( event, context );
	}
}

















































/*
-----------------------------------------------------------------
From: ( https://stackoverflow.com/questions/18279302/how-do-i-perform-a-java-callback-between-classes ).

Use the observer pattern. It works like this:

-----------------------------------------------------------------
//
interface Handler {
    void somethingHappened();
}

public class Registrar {

    private List<Handler> handlers = new ArrayList<Handler>();

    public void add( Handler handler ) {
        handlers.add( handler );
    }

    void notify(){
        for( Handler handler : handlers ){
            handler.somethingHappened();
        }
    }
}

public class Controller implements Handler {

    Registrar registrar;

    public Controller() {
        this.registrar = new Registrar();
        registrar.add( this );
    }

    public void somethingHappened() {
       System.out.println( "Called me!" );
    }
}

You create an interface which has one or more methods to be called when some event happens. Then, any class which needs to be notified when events occur implements this interface.

This allows more flexibility, as the producer is only aware of the listener interface, not a particular implementation of the listener interface.

In my example:

Registrar is the producer here as it is notifying a list of listeners.

Handler is the interface.

MyForm is interested in when somethingHappened, so it is implementing Handler and registering itself with Registrar. Now Registrar can inform MyForm about events without directly referencing MyForm. This is the strength of the observer pattern, it reduces dependency and increases reusability.

*/

/*
From ( https://www.w3schools.com/java/java_lambda.asp ).

To use a lambda expression in a method, the method should have a parameter with a single-method interface as its type. Calling the interface's method will run the lambda expression.

-----------------------------------------------------------------

// Body defined elsewhere. Can have different definitions of its code body as long as its compatible with the signature declared here.
interface StringFunction {
	String run( String str );
}

public class Main {

	//
	public static void main( String[] args ) {
		//
		StringFunction exclaim = (s) -> s + "!";
		printFormatted( exclaim, "Hello" );
		//
		StringFunction ask = (s) -> s + "?";
		printFormatted( ask, "Hello" );
	}

	public static void printFormatted( StringFunction format, String str ) {
		//
		String result = format.run( str );
		System.out.println( result );
	}
}

-----------------------------------------------------------------
*/

/*
https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.1/index.html
https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html
https://docs.aws.amazon.com/lambda/latest/dg/services-apigateway.html
https://docs.aws.amazon.com/apigateway/latest/developerguide/http-api.html
*/
