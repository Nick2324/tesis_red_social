package com.sna_deportivo.pojo.evento;

/**
 * 
 * Clase que contiene constantes necesitadas en el paquete {@link com.sna_deportivo.pojo.evento}
 * 
 * @author Nicolas Mauricio Garcia Garzon
 * @version 1.0
 *
 */

public enum TiposEventos {

	//************************Tipos de evento*****************************
	PRACTICADEPORTIVALIBRE("practicas_libres","Practica libre",
						"Practicas informales realizadas con cualquier "
						+ "deportista para obtener un momento de diversion"
						+ " y probar habilidades antiguas y nuevas",
						PracticaDeportiva.class.getSimpleName()),
	EVENTODEPORTIVO("eventos_deportivos","Evento compuesto",
					"Compuesto de otros eventos, haciendo la experiencia más completa",
					EventoDeportivo.class.getSimpleName()),
	EVENTOGENERICO("eventos","Evento generico","Evento generico",
					Evento.class.getSimpleName());
	//********************************************************************	
	
	private final String servicio;
	private final String valor;
	private final String descripcion;
	private final String nombreClase;
	
	TiposEventos(String servicio,
					  String valor,
					  String descripcion,
					  String nombreClase){
		this.servicio = servicio;
		this.valor = valor;
		this.descripcion = descripcion;
		this.nombreClase = nombreClase;
	}

	public String getServicio(){
		return this.servicio;
	}
	
	public String getValor(){
		return this.valor;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public String getNombreClase(){
		return this.nombreClase;
	}
	
	@Override
	public String toString(){
		return this.valor;
	}
	
}
