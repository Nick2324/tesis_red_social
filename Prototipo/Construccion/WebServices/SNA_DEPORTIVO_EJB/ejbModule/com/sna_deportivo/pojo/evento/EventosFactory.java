package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

public abstract class EventosFactory implements FactoryObjectSNSDeportivo {

	public abstract DAOEvento crearDAOEvento();
	
	public abstract Evento crearEvento();
	
	public ObjectSNSDeportivo getObjetoSNS(){
		return this.crearEvento();
	}
	
}
