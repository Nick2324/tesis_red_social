package com.sna_deportivo.utils;

public class BDException extends Exception {
	
	public BDException() {
		super();
	}
	
	public BDException(String message){
		super(message);
	}
	
	public BDException(String message, Throwable throwable){
		super(message, throwable);
	}

}
