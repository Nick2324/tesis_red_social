package com.sna_deportivo.utils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class Utils {
	
	private static String SERVER_ROOT_URI = "http://sna-deportivo.ddns.net:7474/db/data";
	
	public static boolean servidorActivo(){
		
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(SERVER_ROOT_URI).path("/");
		String result = target.request().accept(MediaType.TEXT_PLAIN).get(Response.class).toString();

		System.out.println( result );
		return true;
		
	}
}
