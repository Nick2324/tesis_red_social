package com.sna_deportivo.pojo.evento;

public abstract class EventosFactory {

	public abstract DAOEvento crearDAOEvento();
	
	public abstract Evento crearEvento();
	
}
