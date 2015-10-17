package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class EventosFactory implements FactoryObjectSNSDeportivo {

	public DAOEvento crearDAOEvento(){
		return new DAOEvento();
	}
	
	public Evento crearEvento(){
		return new Evento();
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
