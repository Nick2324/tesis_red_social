package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.pojo.evento.excepciones.ProductorFactoryExcepcion;

public class ProductorFactory {
	
	public ProductorFactory(){}
	
	public EventosFactory getEventosFactory(String tipo) throws ProductorFactoryExcepcion{
		if(tipo.equals(ConstantesEventos.PRACTICADEPORTIVALIBRE.getServicio()))
			return new PracticaDeportivaFactory();
		if(tipo.equals(ConstantesEventos.EVENTODEPORTIVO.getServicio()))
			return new EventoDeportivoFactory();
		throw new ProductorFactoryExcepcion();
	}
	
	public static void main(String[] args){
		Integer i = null;
	}
	
}
