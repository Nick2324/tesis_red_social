package com.sna_deportivo.pojo.entidadesEstaticas;

public enum EntidadesGenerales {

	GENERO("genero","GENERO",Genero.class.getSimpleName());
	
	//*********************************************
	
	private final String servicio;
	private final String valor;
	private final String nombreClase;
	
	EntidadesGenerales(String servicio, 
							     String valor,
							     String nombreClase){
		this.servicio = servicio;
		this.valor = valor;
		this.nombreClase = nombreClase;
	}
	
	public String getServicio(){
		return this.servicio;
	}
	
	public String getValor(){
		return this.valor;
	}
	
	public String getNombreClase(){
		return this.nombreClase;
	}
	
	@Override
	public String toString(){
		return this.valor;
	}
	
}
