package com.sna_deportivo.utils.bd.excepciones;

import javax.ws.rs.core.Response;

public class BDException extends javax.ws.rs.WebApplicationException {
	
	public BDException() {
		super();
	}
	
	public BDException(Response.Status status){
		super(status);
	}
	
	public BDException(String message){
		super(message);
	}
	
	public BDException(String message, Throwable throwable){
		super(message, throwable);
	}
	
}
