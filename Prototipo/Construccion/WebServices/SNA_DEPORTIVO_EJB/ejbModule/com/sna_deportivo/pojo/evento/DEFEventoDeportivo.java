package com.sna_deportivo.pojo.evento;

public class DEFEventoDeportivo extends DAOEventosFactory {

	@Override
	public DAOEvento factoryMethod() {
		return new DEventoDeportivo();
	}

	@Override
	public DAOEvento factoryMethod(Evento e) {
		// TODO Auto-generated method stub
		return null;
	}

}
