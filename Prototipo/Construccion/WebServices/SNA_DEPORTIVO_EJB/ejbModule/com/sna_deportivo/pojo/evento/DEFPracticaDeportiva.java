package com.sna_deportivo.pojo.evento;

public class DEFPracticaDeportiva extends DAOEventosFactory{

	@Override
	public DAOEvento factoryMethod() {
		return new DEPracticaDeportiva();
	}

	@Override
	public DAOEvento factoryMethod(Evento e) {
		return new DEPracticaDeportiva((PracticaDeportiva)e);
	}
	
	

}
