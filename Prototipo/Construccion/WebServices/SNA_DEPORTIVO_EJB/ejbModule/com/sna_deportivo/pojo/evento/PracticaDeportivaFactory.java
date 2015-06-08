package com.sna_deportivo.pojo.evento;

public class PracticaDeportivaFactory extends EventosFactory {

	@Override
	public DAOEvento crearDAOEvento() {
		return new DEPracticaDeportiva();
	}

	@Override
	public Evento crearEvento() {
		return new PracticaDeportiva();
	}

}
