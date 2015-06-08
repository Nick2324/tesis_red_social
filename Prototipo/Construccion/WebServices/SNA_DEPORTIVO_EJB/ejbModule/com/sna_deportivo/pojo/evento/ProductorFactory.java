package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.pojo.evento.excepciones.ProductorFactoryExcepcion;

public class ProductorFactory {
	
	public ProductorFactory(){}
	
	public EventosFactory getEventosFactory(String tipo) throws ProductorFactoryExcepcion{
		if(tipo == ConstantesEventos.PRACTICADEPORTIVALIBRE)
			return new PracticaDeportivaFactory();
		if(tipo == ConstantesEventos.EVENTODEPORTIVO)
			return new EventoDeportivoFactory();
		throw new ProductorFactoryExcepcion();
	}
		
	
}
