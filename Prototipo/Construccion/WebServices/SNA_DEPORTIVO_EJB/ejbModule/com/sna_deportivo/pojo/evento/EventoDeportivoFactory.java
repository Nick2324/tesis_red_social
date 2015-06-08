package com.sna_deportivo.pojo.evento;

public class EventoDeportivoFactory extends EventosFactory{

	@Override
	public DAOEvento crearDAOEvento() {
		return new DEventoDeportivo();
	}

	@Override
	public Evento crearEvento() {
		return new EventoDeportivo();
	}

}
