package com.sna_deportivo.services.eventos;

import com.sna_deportivo.pojo.evento.DeporteEvento;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class RelacionParticipante extends HandlerRelacionUsuarioEvento{
	
	public RelacionParticipante(){
		super("participante");
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
		de.setParticipantes(arregloUsuarios);
		return de;
	}

	@Override
	protected String manejarCreacion() throws BDException{
		return ded.crearParticipantes();
	}
	
	@Override
	protected DeporteEvento prepararDeporteEventoObtencion(String tipoEvento) {
		Evento evento = null;
		evento = new ProductorFactoryEvento().
		   getEventosFactory(tipoEvento).crearEvento();
		evento = (Evento)arregloEventos.get(0);
		DeporteEvento de = new DeporteEvento();
		de.setEvento(evento);
		ded.setObjetcSNSDeportivo(de);
		ded.encontrarObjetoManejado();
		de = (DeporteEvento)ded.getObjetcSNSDeportivo();
		return de;
	}

	@Override
	protected String manejarObtencion() 
			throws BDException, ExcepcionJsonDeserializacion {
		return ded.getParticipantesEvento();
	}

	@Override
	protected DeporteEvento prepararDeporteEventoEliminacion(String tipoEvento) {
		Evento evento = null;
		evento = new ProductorFactoryEvento().
		   getEventosFactory(tipoEvento).crearEvento();
		evento = (Evento)arregloEventos.get(0);
		DeporteEvento de = new DeporteEvento();
		de.setEvento(evento);
		ded.setObjetcSNSDeportivo(de);
		ded.encontrarObjetoManejado();
		de = (DeporteEvento)ded.getObjetcSNSDeportivo();
		de.setParticipantes(arregloUsuarios);
		return de;
	}

	@Override
	protected String manejarEliminacion() 
			throws BDException, ExcepcionJsonDeserializacion {
		return ded.eliminarParticipantes();
	}
	
}
