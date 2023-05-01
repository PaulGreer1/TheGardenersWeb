package com.strongcoffee.tgw;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Handler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse > {

	String url = "jdbc:mysql://localhost:3306/TheGardenersWeb";
	String username = "root";
	String password = "";
	Statement statement;
	ResultSet resultSet;
	String currentTime = "";
	Connection con;
	StringBuilder stringBuilder;

	@Override
	public APIGatewayV2HTTPResponse handleRequest( APIGatewayV2HTTPEvent event, Context context ) {
		System.out.println( "handleRequest() called.\n" );
		APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
		stringBuilder = new StringBuilder();

		try {
			con = DriverManager.getConnection( url, username, password );
			statement = con.createStatement();
			resultSet = statement.executeQuery( "SELECT * FROM garden" );

			while( resultSet.next() ) {
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
