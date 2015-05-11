package com.sna_deportivo.services.eventos.entity;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.EventoDeportivo;
import com.sna_deportivo.pojo.evento.FactoryEventos;

public class GestionEvento {

	private EventoDeportivo evento;
	
	public GestionEvento(){
		this.evento = new EventoDeportivo();
	}
	
	public Evento getEventoNombre(String nombre,String tipo){
		Evento e = new FactoryEventos().creaEvento(tipo);
		e.setNombre(nombre);
		/*busqueda*/
		//return this.evento.getEvento(e);
		return e;
	}
	
}
