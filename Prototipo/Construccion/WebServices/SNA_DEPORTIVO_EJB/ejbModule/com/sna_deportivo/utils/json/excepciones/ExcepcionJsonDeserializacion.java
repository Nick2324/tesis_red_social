package com.sna_deportivo.utils.json.excepciones;

public class ExcepcionJsonDeserializacion extends Exception{

	public ExcepcionJsonDeserializacion() {
		super();
	}
	
	public ExcepcionJsonDeserializacion(String message){
		super(message);
	}
	
	public ExcepcionJsonDeserializacion(String message, Throwable throwable){
		super(message, throwable);
	}
	
}
