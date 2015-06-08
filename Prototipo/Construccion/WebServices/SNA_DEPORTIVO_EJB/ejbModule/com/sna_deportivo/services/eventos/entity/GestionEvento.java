package com.sna_deportivo.services.eventos.entity;

import com.sna_deportivo.pojo.evento.DAOEvento;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactory;
import com.sna_deportivo.pojo.evento.excepciones.ProductorFactoryExcepcion;

public class GestionEvento {
	
	public GestionEvento(){}
	
	public Evento consultarEvento(String tipo, Evento evento){
		DAOEvento accesoEvento;
		try {
			accesoEvento = new ProductorFactory().
										 getEventosFactory(tipo).
										 crearDAOEvento();
			return accesoEvento.getEventoDB();
		} catch (ProductorFactoryExcepcion e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public Evento crearEvento(String tipo,
							  Evento evento){
		DAOEvento accesoEvento = null;
		try {
			accesoEvento = new ProductorFactory().
										 getEventosFactory(tipo).
										 crearDAOEvento();
			accesoEvento.setEvento(evento);
			return accesoEvento.crearEventoDB();
		} catch (ProductorFactoryExcepcion e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public boolean actualizarEvento(String tipo,
									Evento evento){
		DAOEvento accesoEvento = null;
		try {
			accesoEvento = new ProductorFactory().
										 getEventosFactory(tipo).
										 crearDAOEvento();
			accesoEvento.setEvento(evento);
			return accesoEvento.updateEventoDB();
		} catch (ProductorFactoryExcepcion e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public boolean desactivarEvento(String tipo,
									Evento evento){
		DAOEvento accesoEvento = null;
		try {
			accesoEvento = new ProductorFactory().
										 getEventosFactory(tipo).
					 					 crearDAOEvento();
			accesoEvento.setEvento(evento);
			return accesoEvento.deleteEventoDB();
		} catch (ProductorFactoryExcepcion e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
}
