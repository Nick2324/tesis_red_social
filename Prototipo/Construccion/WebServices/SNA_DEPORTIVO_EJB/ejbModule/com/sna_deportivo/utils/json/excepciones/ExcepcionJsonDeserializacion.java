package com.sna_deportivo.utils.json.excepciones;

public class ExcepcionJsonDeserializacion extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
