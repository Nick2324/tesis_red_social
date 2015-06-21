package com.sna_deportivo.pojo.evento;

/**
 * 
 * Clase que contiene constantes necesitadas en el paquete {@link com.sna_deportivo.pojo.evento}
 * 
 * @author Nicolas Mauricio Garcia Garzon
 * @version 1.0
 *
 */

public enum ConstantesEventos {

	//************************Tipos de evento*****************************
	PRACTICADEPORTIVALIBRE("practicas_libres","Practica libre"),
	EVENTODEPORTIVO("eventos_deportivos","Evento compuesto"),
	EVENTOGENERICO("eventos","Evento generico");
	//********************************************************************	
	
	private final String servicio;
	private final String valor;
	
	ConstantesEventos(String servicio,String valor){
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
