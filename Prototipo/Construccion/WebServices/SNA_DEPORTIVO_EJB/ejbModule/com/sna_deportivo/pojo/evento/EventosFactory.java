package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;

public abstract class EventosFactory implements FactoryObjectSNSDeportivo {

	public abstract DAOEvento crearDAOEvento();
	
	public abstract Evento crearEvento();
	
}
