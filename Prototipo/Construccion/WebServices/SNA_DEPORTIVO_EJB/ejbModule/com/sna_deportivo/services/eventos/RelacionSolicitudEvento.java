package com.sna_deportivo.services.eventos;


import com.sna_deportivo.pojo.evento.DeporteEvento;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;

public class RelacionSolicitudEvento extends HandlerRelacionUsuarioEvento{
	
	public RelacionSolicitudEvento(){
		super("solicitud");
	}

	@Override
	protected DeporteEvento prepararDeporteEvento(String tipoEvento) {
		Evento evento = null;
		evento = new ProductorFactoryEvento().
		   getEventosFactory(tipoEvento).crearEvento();
		evento = (Evento)arregloEventos.get(0);
		DeporteEvento de = new DeporteEvento();
		de.setEvento(evento);
		ded.setObjetcSNSDeportivo(de);
		ded.encontrarObjetoManejado();
		de = (DeporteEvento)ded.getObjetcSNSDeportivo();
		de.setSolicitudes(arregloUsuarios);
		return de;
	}

	@Override
	protected String manejarCreacion() {
		return ded.crearSolicitudes();
	}
	
}
