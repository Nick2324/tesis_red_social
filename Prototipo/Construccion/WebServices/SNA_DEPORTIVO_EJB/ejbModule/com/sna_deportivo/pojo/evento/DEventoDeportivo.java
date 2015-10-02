package com.sna_deportivo.pojo.evento;

public class DEventoDeportivo extends DAOEvento {

	public DEventoDeportivo(){}
	
	public DEventoDeportivo(EventoDeportivo e){
		super(e);
		this.eventoManejado = TiposEventos.EVENTODEPORTIVO.getServicio();
	}

	@Override
	protected void setUpDAOGeneral() {}
	
}
