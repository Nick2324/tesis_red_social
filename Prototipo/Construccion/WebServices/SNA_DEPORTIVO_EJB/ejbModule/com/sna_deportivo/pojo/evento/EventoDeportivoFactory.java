package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class EventoDeportivoFactory extends EventosFactory{

	@Override
	public DAOEvento crearDAOEvento() {
		return new DEventoDeportivo();
	}

	@Override
	public Evento crearEvento() {
		return new EventoDeportivo();
	}

	@Override
	public ObjectSNSDeportivo getObjetoSNS() {
		return this.crearEvento();
	}

	@Override
	public ObjectSNSDeportivoDAO getObjetoSNSDAO() {
		return this.crearDAOEvento();
	}

}
