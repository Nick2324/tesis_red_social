package com.sna_deportivo.pojo.evento;

/**
 * 
 * Crea los eventos transparentemente a los usuarios basado en un tipo
 * de evento
 * 
 * @author Nicolas Mauricio Garcia Garzon
 * @version 1.0
 *
 */

public class FactoryEventos {

	/**
	 * 
	 * Constructor por defecto
	 * 
	 */
	
	public FactoryEventos(){}
	
	/**
	 * 
	 * Crea eventos deportivos basados en el tipo de evento pasado
	 * como parametro
	 * 
	 * @param tipo Tipo del evento. Debe ser uno de los tipos de evento encontrados en {@link Constantes}
	 * @return
	 */
	
	public Evento creaEvento(String tipo){
		
		if(tipo == Constantes.EVENTODEPORTIVO)
			return new EventoDeportivo();
		if(tipo == Constantes.PRACTICADEPORTIVALIBRE)
			return new PracticaDeportivaLibre();
		return null;
		
	}
	
}
