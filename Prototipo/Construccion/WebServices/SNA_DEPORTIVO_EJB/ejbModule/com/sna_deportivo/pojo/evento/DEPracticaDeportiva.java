package com.sna_deportivo.pojo.evento;

public class DEPracticaDeportiva extends DAOEvento{

	public DEPracticaDeportiva(){
		super();
	}
	
	public DEPracticaDeportiva(PracticaDeportiva pd){
		super(pd);
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
