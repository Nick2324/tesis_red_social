package com.sna_deportivo.pojo.evento;

public class ProductorFactoryDAO {
	
	public ProductorFactoryDAO(){}
	
	public DAOEventosFactory getDAOEventosFactory(String tipo,
												  Evento evento){
		if(tipo == Constantes.PRACTICADEPORTIVALIBRE)
			return new DEFPracticaDeportiva();
		if(tipo == Constantes.EVENTODEPORTIVO)
			return new DEFEventoDeportivo();
		return null;
	}
	
}
