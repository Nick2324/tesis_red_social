package com.sna_deportivo.pojo.evento;

public class DEventoDeportivo extends DAOEvento {

	public DEventoDeportivo(){}
	
	public DEventoDeportivo(EventoDeportivo e){
		super(e);
	}
	
	@Override
	public Evento getEventoDB(){
		Evento evento = null;
		return evento;
	}
	
	@Override
	public boolean updateEventoDB(){
		return true;
	}
	
	@Override
	public boolean deleteEventoDB(){
		return true;
	}
	
	@Override
	public boolean crearEventoDB(){
		return true;
	}
	
}
