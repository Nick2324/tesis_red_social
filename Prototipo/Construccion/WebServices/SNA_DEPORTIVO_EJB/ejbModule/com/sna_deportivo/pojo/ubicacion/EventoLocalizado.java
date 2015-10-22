package com.sna_deportivo.pojo.ubicacion;

import com.sna_deportivo.pojo.evento.Evento;

public class EventoLocalizado {
	
	private Evento evento;
	private LugarPractica ubicacion;
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public LugarPractica getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(LugarPractica ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	

}
