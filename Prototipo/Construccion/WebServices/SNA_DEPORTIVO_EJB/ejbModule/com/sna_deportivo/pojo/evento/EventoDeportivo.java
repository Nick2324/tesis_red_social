package com.sna_deportivo.pojo.evento;

import java.util.ArrayList;

/**
 * 
 * Composite para eventos. Puede alojar diferentes tipos de eventos deportivos
 * 
 * @author Nicolas Mauricio Garcia Garzon
 * @version 1.0
 * @see Evento
 * @see PracticaDeportiva
 *
 */

public class EventoDeportivo extends Evento{

	private ArrayList<Evento> eventosInternos;
	
	/**
	 * 
	 * Constructor por defecto
	 * 
	 */
	
	public EventoDeportivo(){
		super();
		eventosInternos = new ArrayList<Evento>();
	}
	
	/**
	 * 
	 * Retorna un evento interno segun el estado pasado sobre el parametro
	 * eventoBuscado
	 * 
	 * @param eventoBuscado Evento con estado a buscar
	 * @return Evento buscado
	 */
	
	public Evento getEvento(Evento eventoBuscado){
		
		for(Evento e:eventosInternos){
			if(e.equals(eventoBuscado))
				return e;
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * Adiciona un evento interno
	 * 
	 * @param eventoAdicion Evento a adicionar
	 */
	
	public void setEvento(Evento eventoAdicion){
		eventosInternos.add(eventoAdicion);
	}
	
}