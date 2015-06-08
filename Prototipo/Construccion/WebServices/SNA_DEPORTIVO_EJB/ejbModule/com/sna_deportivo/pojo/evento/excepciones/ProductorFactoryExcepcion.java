package com.sna_deportivo.pojo.evento.excepciones;

public class ProductorFactoryExcepcion extends Exception{

	public ProductorFactoryExcepcion() {
		super();
	}
	
	public ProductorFactoryExcepcion(String message){
		super(message);
	}
	
	public ProductorFactoryExcepcion(String message, Throwable throwable){
		super(message, throwable);
	}
	
}
