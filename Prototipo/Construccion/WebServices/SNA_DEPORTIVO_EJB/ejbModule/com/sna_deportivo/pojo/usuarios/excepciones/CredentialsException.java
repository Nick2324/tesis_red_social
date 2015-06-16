package com.sna_deportivo.pojo.usuarios.excepciones;

public class CredentialsException extends Exception {
	
	public CredentialsException() {
		super();
	}
	
	public CredentialsException(String message){
		super(message);
	}
	
	public CredentialsException(String message, Throwable throwable){
		super(message, throwable);
	}
}
