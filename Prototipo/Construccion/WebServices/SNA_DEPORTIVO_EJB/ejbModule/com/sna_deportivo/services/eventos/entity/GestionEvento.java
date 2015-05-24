package com.sna_deportivo.services.eventos.entity;

import com.sna_deportivo.pojo.evento.DAOEvento;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryDAO;

public class GestionEvento {
	
	public GestionEvento(){}
	
	public Evento consultarEvento(String tipo, Evento evento){
		DAOEvento accesoEvento = new ProductorFactoryDAO().
				 				 getDAOEventosFactory(tipo, evento).
				 				 factoryMethod();
		return accesoEvento.getEventoDB();
	}
	
	public boolean crearEvento(String tipo,
							   Evento evento){
		DAOEvento accesoEvento = new ProductorFactoryDAO().
									 getDAOEventosFactory(tipo, evento).
									 factoryMethod();
		return accesoEvento.crearEventoDB();
	}
	public boolean actualizarEvento(String tipo,
									Evento evento){
		DAOEvento accesoEvento = new ProductorFactoryDAO().
									 getDAOEventosFactory(tipo, evento).
									 factoryMethod();
		return accesoEvento.updateEventoDB();
	}
	
	public boolean desactivarEvento(String tipo,
									Evento evento){
		DAOEvento accesoEvento = new ProductorFactoryDAO().
				 					 getDAOEventosFactory(tipo, evento).
				 					 factoryMethod();
		return true;
	}
	
}
