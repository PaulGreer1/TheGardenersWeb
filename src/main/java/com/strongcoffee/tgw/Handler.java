package com.strongcoffee.tgw;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

/**
 *
 */
public class Handler implements RequestHandler< APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse > {

	/*
	The following can all be initialised/instantiated before handlRequest() is invoked because they don't rely on
	input from the gateway. (? They will be kept in memory between invocations of handleRequest(). ?)
	*/
	String url = "jdbc:mysql://localhost:3306/TheGardenersWeb";
	String username = "root";
	String password = "";
	Statement statement;
	ResultSet resultSet;
	Connection con;
	StringBuilder stringBuilder = new StringBuilder();
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
		Map<String, String> queryStringParameters = event.getQueryStringParameters();
		String uiEvent = queryStringParameters.get( "ui_event" );
		System.out.println( uiEvent );
		APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
		registrar.setQueryStringParameters( queryStringParameters );          // Set the Registrar's queryStringParameters member variable.
		registrar.notify( uiEvent );

		try {
			con = DriverManager.getConnection( url, username, password );
			statement = con.createStatement();
			resultSet = statement.executeQuery( "SELECT * FROM garden" );

			while( resultSet.next() ) {
				stringBuilder.append(resultSet.getObject("garden_id").toString());
				stringBuilder.append(" | ");
				stringBuilder.append(resultSet.getObject("garden_name").toString());
				stringBuilder.append(" | ");
				stringBuilder.append(resultSet.getObject("description").toString());
				stringBuilder.append("\n");
			}
			con.close();
			response.setBody( stringBuilder.toString() );
		}
		catch ( Exception e ) {
			response.setBody( "Exception: " + e.getMessage() + "\nCause: " + e.getCause() );
		}

		System.out.println( response.getBody() );

		return response;
	}
}
