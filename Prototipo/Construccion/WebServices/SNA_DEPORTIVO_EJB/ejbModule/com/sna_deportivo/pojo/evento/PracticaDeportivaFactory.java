package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class PracticaDeportivaFactory extends EventosFactory {

	@Override
	public DAOEvento crearDAOEvento() {
		return new DEPracticaDeportiva();
	}

	@Override
	public Evento crearEvento() {
		return new PracticaDeportiva();
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
