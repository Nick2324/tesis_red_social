package com.sna_deportivo.pojo.entidadesEstaticas;

public enum ConstantesEntidadesGenerales {

	GENERO("genero","GENERO");
	
	//*********************************************
	
	private final String servicio;
	private final String valor;
	
	ConstantesEntidadesGenerales(String servicio, 
							     String valor){
		this.servicio = servicio;
		this.valor = valor;
	}
	
	public String getServicio(){
		return this.servicio;
	}
	
	public String getValor(){
		return this.valor;
	}
	
	@Override
	public String toString(){
		return this.valor;
	}
	
}
